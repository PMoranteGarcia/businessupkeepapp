package presentacio;

import dades.AppConfigDAO;
import dades.ClientDAO;
import dades.ComandaDAO;
import dades.ProducteDAO;
import entitats.Client;
import entitats.Comanda;
import entitats.ComandaLogic;
import entitats.Producte;
import entitats.ProductesComanda;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import java.util.Comparator;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * Controlador de la vista 'comandesForm.fxml'. Permet a l'usuari gestionar el
 * CRUD d'una línia de comanda des d'una UI.
 *
 * @author Txell Llanas - Creació vista FXML
 * @author Pablo Morante - Creació/Implementació
 * @author Victor García - Creació/Implementació
 */
public class ComandesFormController extends PresentationLayer implements Initializable {

    int idComanda; // comanda clicada per l'usuari

    @FXML
    private Button btnBack;
    @FXML
    public Label orderNumber;
    @FXML
    private Label TitolComanda;
    @FXML
    private TableColumn<ProductesComanda, ProductesComanda> columnActions;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnCancel;
    @FXML
    private TableView<ProductesComanda> orderLinesList;
    @FXML
    private TableColumn columnProductCode;
    @FXML
    private TableColumn columnProductName;
    @FXML
    private TableColumn columnQuantityOrdered;
    @FXML
    private TableColumn columnPriceEach;
    @FXML
    private TableColumn columnAmount;
    @FXML
    private Label totalAmount;
    @FXML
    private ComboBox<Producte> selectorProduct;
    @FXML
    private Button btnaddProduct;
    @FXML
    private ComboBox<Client> selectorClient = new ComboBox<Client>();
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField fieldHour;
    @FXML
    private TextField fieldMinutes;

    private ComandaDAO DAOComanda;
    private ClientDAO DAOClient;
    private ProducteDAO DAOProducte;
    private AppConfigDAO DAOAppConfig;

    private ObservableList<ProductesComanda> llistaObservableProductes = FXCollections.observableArrayList();

    private final Tooltip tooltipDesar = new Tooltip("Desar Canvis");
    private final Tooltip tooltipEliminar = new Tooltip("Eliminar Producte");

    // Instància del ClientLogic per carregar els mètodes de validacions
    private final ComandaLogic validate = new ComandaLogic();

    /**
     * Initializes the controller class.
     *
     * @author Txell Llanas - Creació/Implementació
     * @author Pablo Morante - Implementació
     * @author Víctor García - Implementació
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Obtenir el texte dels botons
        String text1 = btnSave.getText();
        String text2 = btnCancel.getText();
        String text3 = btnaddProduct.getText();

        // Passar el texte a MAJÚSCULES
        btnSave.setText(text1.toUpperCase());
        btnCancel.setText(text2.toUpperCase());
        btnaddProduct.setText(text3.toUpperCase());

        this.idComanda = ComandesController.getComanda().getNumOrdre(); // obtenir id comanda actual

        fillDropDownList();
        fillProductsTable();

        if (idComanda == 0) { // si és 0 és que la comanda és nova
            orderNumber.setText("");
            TitolComanda.setText("Crear Comanda");
            totalAmount.setText("0");
        } else { // si no, estableix el número de comanda
            Comanda comandaActual = DAOComanda.getOne(ComandesController.getComanda());
            orderNumber.setText(Integer.toString(comandaActual.getNumOrdre()));
            Client clientActual = DAOClient.getOne(new Client(comandaActual.getCustomers_customerEmail()));
            selectorClient.setValue(clientActual);
            selectorClient.setDisable(true);
            datePicker.setValue((comandaActual.getDataEntrega()).toLocalDateTime().toLocalDate());
            String hours = String.valueOf(comandaActual.getDataEntrega().getHours());
            String minutes = String.valueOf(comandaActual.getDataEntrega().getMinutes());
            if (hours.equals("0")) {
                hours = hours + "0";
            }
            if (minutes.equals("0")) {
                minutes = minutes + "0";
            }
            fieldHour.setText(hours);
            fieldMinutes.setText(minutes);
            TitolComanda.setText("Detall Comanda ");
            calculateTotalAmount();
        }

        selectorProduct.setOnMouseClicked(event -> {
            btnaddProduct.setDisable(false);
        });
        
        // Definir format:(dia/mes/any) a mostrar quan s'edita a dins els camps dels calendaris
        String datePattern = "dd/MM/yyyy";                                      // Format per aplicar a la Data
        datePicker.setPromptText("dd/mm/aaaa");                                 // Texte que es mostra al camp Data
        
        datePicker.setConverter(new StringConverter<LocalDate>() {
            
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(datePattern);

            @Override 
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);                          // Aplico format a la data
                } else {
                    return "";
                }
            }

            @Override 
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);              // Aplico format a la data
                } else {
                    return null;
                }
            }
        });

    }

    /**
     * Omplir la taula dels productes que té la comanda
     *
     * @author Pablo Morante - Creació/Implementació
     * @author Víctor García - Creació/Implementació
     */
    private void fillProductsTable() {
        try {

            DAOComanda = new ComandaDAO();

            llistaObservableProductes.addAll(DAOComanda.getProductes(this.idComanda));

            // Vincular els atributs de Comanda amb cada columna de la taula per mostrar les dades recuperades dins el tableview:
            columnProductCode.setCellValueFactory(new PropertyValueFactory<ProductesComanda, Integer>("idProducte"));
            columnProductName.setCellValueFactory(new PropertyValueFactory<>("nom"));
            columnQuantityOrdered.setCellValueFactory(new PropertyValueFactory<ProductesComanda, Integer>("quantitat"));
            columnPriceEach.setCellValueFactory(new PropertyValueFactory<ProductesComanda, Float>("unitaryPrice"));
            columnAmount.setCellValueFactory(new PropertyValueFactory<ProductesComanda, Float>("total"));

            columnActions.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
            addCellButtons();

            // Aplicar estils pels camps NO EDITABLES
            columnProductCode.setCellFactory(tc -> new TableCell<Comanda, Integer>() {
                @Override
                protected void updateItem(Integer value, boolean empty) {
                    super.updateItem(value, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        getStyleClass().add("non-editable");
                        setText(Integer.toString(value));
                    }
                }
            });

            Comparator<ProductesComanda> comparator = Comparator.comparingInt(ProductesComanda::getNumberLine);
            llistaObservableProductes.sort(comparator);

            // Afegir els registres a la taula
            orderLinesList.setItems(llistaObservableProductes);

        } catch (SQLException ex) {
            Logger.getLogger(ComandesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Omple amb dades les dues dropdown lists de clients i productes
     *
     * @author Pablo Morante - Creació/Implementació
     * @author Víctor García - Creació/Implementació
     */
    private void fillDropDownList() {
        try {
            DAOClient = new ClientDAO();
            ObservableList<Client> olc = (FXCollections.observableList(DAOClient.getAll()));
            selectorClient.setDisable(false);
            selectorClient.setPromptText("Selecciona un client");
            selectorClient.setItems(olc);
            // conversor per pasar d'objecte a string per la dropdownlist
            selectorClient.setConverter(new StringConverter<Client>() {
                @Override
                public String toString(Client client) {
                    if (client == null) {
                        return null;
                    } else {
                        return client.getCustomerName(); // retorna el nom del client si no és null
                    }
                }

                @Override
                public Client fromString(String id) {
                    return null;
                }
            });
            DAOProducte = new ProducteDAO();
            ObservableList<Producte> olp = (FXCollections.observableList(DAOProducte.getAll()));
            selectorProduct.setPromptText("Selecciona un producte");
            selectorProduct.setItems(olp);
            // conversor per pasar d'objecte a string per la dropdownlist
            selectorProduct.setConverter(new StringConverter<Producte>() {
                @Override
                public String toString(Producte p) {
                    if (p == null) {
                        return null;
                    } else {
                        return p.getProductName(); // retorna el nom del producte si no és null
                    }
                }

                @Override
                public Producte fromString(String id) { // per passar de string a objecte si és necessari
                    return null;
                }
            });

        } catch (SQLException ex) {
            this.alertInfo(ex.toString());
        }
    }

    /**
     * Mostra l'apartat 'Comandes' i un llistat que conté tots els registres de
     * la BD.
     *
     * @throws IOException Excepció a mostrar en cas que no es trobi el Layout
     * @author Txell Llanas - Creació
     */
    @FXML
    private void goToOrdersList(ActionEvent event) throws IOException {
        cancelOrder();
    }

    /**
     * Mètode per desar una comanda.
     *
     * @throws IOException Excepció a mostrar en cas que no es trobi el Layout
     * @author Txell Llanas - Creació
     * @author Pablo Morante - Implementació
     * @author Víctor García - Implementació
     */
    @FXML
    private void saveOrder() throws IOException, SQLException {
        String errorText = validacions();
        if (errorText.equals("")) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);                    // Mostrar alerta per confirmar si cancel·lar el procés d'alta
            alert.setTitle("CONFIRMI UNA OPCIÓ");
            alert.setHeaderText("Vols confirmar la comanda actual?");

            ButtonType cancelButton = new ButtonType("Seguir Editant");
            ButtonType yesButton = new ButtonType("Confirmar");

            alert.getButtonTypes().setAll(yesButton, cancelButton);
            if (alert.showAndWait().get() == yesButton) {
                // si la id de comanda és 0, es que s'ha de crear una nova comanda, si no, s'ha de fer un update
                if (this.idComanda == 0) {
                    try {
                        createNewCommand();
                    } catch (ParseException ex) {
                        Logger.getLogger(ComandesFormController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    updateCommand();
                }
                App.setRoot("comandes");
            } else {
                alert.close();
            }
        } else {
            Alert error = new Alert(Alert.AlertType.WARNING);                    // Mostrar alerta per confirmar si cancel·lar el procés d'alta
            error.setTitle("ALERTA");
            error.setHeaderText(errorText);

            ButtonType acceptButton = new ButtonType("Acceptar");

            error.getButtonTypes().setAll(acceptButton);
            error.show();
        }

    }

    /**
     * Mètode per descartar una comanda. - Si és de nova creació, no la desa i
     * redirigeix a l'usuari al llistat de comandes. - Si s'està editant, no
     * s'actualitzen els canvis i es redirigeix a l'usuari al llistat de
     * comandes. Abans de redirigir a l'usuari, mostra un avís a l'usuari perquè
     * confirmi si realment vol descartar la comanda actual.
     *
     * @throws IOException Excepció a mostrar en cas que no es trobi el Layout
     * @author Txell Llanas - Creació
     * @author Pablo Morante - Implementació
     * @author Víctor García - Implementació
     */
    @FXML
    private void cancelOrder() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);                    // Mostrar alerta per confirmar si cancel·lar el procés d'alta
        alert.setTitle("CONFIRMI UNA OPCIÓ");
        alert.setHeaderText("Desitja descartar l'alta actual?");

        ButtonType cancelButton = new ButtonType("Sortir");
        ButtonType yesButton = new ButtonType("Seguir Editant");

        alert.getButtonTypes().setAll(yesButton, cancelButton);
        if (alert.showAndWait().get() == cancelButton) {
            App.setRoot("comandes");                                         // Redirigir a l'usuari al llistat de comandes
        } else {
            alert.close();
        }
    }

    /**
     * Mètode per afegir un producte al llistat.
     *
     * @author Txell Llanas - Creació
     * @author Pablo Morante - Implementació
     * @author Víctor García - Implementació
     *
     * (RF36) La quantitat d'un producte per defecte ha de ser
     * defaultQuantityOrdered
     * @author Pablo Morante - Creació/Implementació (RF38) El marge de benefici
     * per defecte ha de ser defaultProductBenefit
     * @author Pablo Morante - Creació/Implementació
     */
    @FXML
    private void addProduct() {
        try {
            Boolean producteRepetit = false;
            Producte temp = selectorProduct.getValue();
            for (int i = 0; i < llistaObservableProductes.size(); i++) {
                ProductesComanda llistTemp = llistaObservableProductes.get(i);
                if (llistTemp.getIdProducte() == temp.getProductCode()) {
                    if (llistTemp.getQuantitat() == 20) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);                    // Mostrar alerta per confirmar si cancel·lar el procés d'alta
                        alert.setTitle("ALERTA");
                        alert.setHeaderText("La quantitat d'un producte no pot superar 20.");

                        ButtonType yesButton = new ButtonType("Acceptar");

                        alert.getButtonTypes().setAll(yesButton);
                        if (alert.showAndWait().get() == yesButton) {
                            alert.close();
                        }
                    } else {
                        llistTemp.setQuantitat(llistTemp.getQuantitat() + 1);
                        llistTemp.setTotal(llistTemp.getUnitaryPrice() * llistTemp.getQuantitat());
                        llistaObservableProductes.set(i, llistTemp);
                    }
                    producteRepetit = true;
                }
            }
            if (!producteRepetit) {
                //si el numero de productes supera el maxLinesPerOrder
                System.out.println(llistaObservableProductes.size());
                System.out.println(validate.getmaxLinesPerOrder());
                System.out.println(checkMaxLInesPerOrder(llistaObservableProductes.size()));
                if (checkMaxLInesPerOrder(llistaObservableProductes.size())) {
                    //Mostrar alerta per informar que no es pot afegir mes productes
                    Alert alert = new Alert(Alert.AlertType.WARNING);                    // Mostrar alerta per confirmar si cancel·lar el procés d'alta
                    alert.setTitle("ALERTA");
                    alert.setHeaderText("La quantitat de productes no pot superar " + validate.getmaxLinesPerOrder() + " productes");

                    ButtonType yesButton = new ButtonType("Acceptar");

                    alert.getButtonTypes().setAll(yesButton);
                    if (alert.showAndWait().get() == yesButton) {
                        alert.close();
                    }
                } else {

                    ProductesComanda newProduct = new ProductesComanda();
                    newProduct.setIdProducte(temp.getProductCode());
                    newProduct.setNumberLine(llistaObservableProductes.size() + 1);
                    newProduct.setOrderNummber(this.idComanda);
                    newProduct.setQuantitat(validate.getDefaultQuantityOrdered());
                    newProduct.setUnitaryPrice(temp.getBuyPrice() + (temp.getBuyPrice() * ((float) validate.getDefaultProductBenefit() / 100)));
                    newProduct.setNom(temp.getProductName());
                    newProduct.setTotal(newProduct.getQuantitat() * newProduct.getUnitaryPrice());
                    llistaObservableProductes.add(newProduct);
                }
            }
            calculateTotalAmount();
        } catch (NullPointerException ex) {
            System.out.println("No s'ha seleccionat producte.");
        }
    }

    /**
     * *
     * Comprova que la quantitat de productes a la comanda no superi el maxim
     *
     * @param quantitat
     * @return true si no supera la quantitat, false si ja te el maxim de productes
     * @author Izan Jimenez - Creació /Implementació
     */
    private boolean checkMaxLInesPerOrder(int quantitat) {
        return validate.getmaxLinesPerOrder() <= quantitat;
    }

    /**
     * Mètode per omplir el preu total de la comanda en curs a la vista
     *
     * @author Pablo Morante - Creació/Implementació
     * @author Víctor García - Creació/Implementació
     */
    private void calculateTotalAmount() {
        float total = 0;

        for (int i = 0; i < llistaObservableProductes.size(); i++) {
            total = total + llistaObservableProductes.get(i).getTotal();
        }

        totalAmount.setText(String.format("%.2f", total));
    }

    /**
     * Mètode per obtenir el preu total de la comanda en curs
     *
     * @author Pablo Morante - Creació/Implementació
     * @author Víctor García - Creació/Implementació
     * @return Float amb el preu total de la comanda en curs
     */
    private float calculateTotalAmountCheckMaxOrderAmount() {
        float total = 0;

        for (int i = 0; i < llistaObservableProductes.size(); i++) {
            total = total + llistaObservableProductes.get(i).getTotal();
        }

        return total;
    }

    /**
     * Mètode per crear una nova comanda
     *
     * @author Pablo Morante - Creació/Implementació
     * @author Víctor García - Creació/Implementació
     */
    private void createNewCommand() throws SQLException, ParseException {
        Timestamp today = new Timestamp(System.currentTimeMillis());
        LocalDate requiredDay = datePicker.getValue();
        LocalDateTime requiredDayTemp = requiredDay.atTime(Integer.parseInt(fieldHour.getText()), Integer.parseInt(fieldMinutes.getText()), 0);

        Timestamp requiredDayG = new java.sql.Timestamp((Timestamp.valueOf(requiredDayTemp).getTime()));

        String cus = selectorClient.getValue().getCustomerEmail();

        Comanda c = new Comanda(new java.sql.Timestamp(today.getTime()), requiredDayG, cus);

        DAOComanda = new ComandaDAO();
        this.idComanda = DAOComanda.saveCommand(c);
        if (idComanda != 0) {
            for (int i = 0; i < llistaObservableProductes.size(); i++) {
                ProductesComanda p = llistaObservableProductes.get(i);
                DAOComanda.saveProduct(p, idComanda);
            }
        } else {
            System.out.println("Error al generar nova comanda a base de dades.");
        }
    }

    /**
     * Mètode per fer update a una comanda ja existent
     *
     * @author Pablo Morante - Creació/Implementació
     * @author Víctor García - Creació/Implementació
     */
    private void updateCommand() {
        Timestamp today = new Timestamp(System.currentTimeMillis());
        LocalDate requiredDay = datePicker.getValue();
        LocalDateTime requiredDayTemp = requiredDay.atTime(Integer.parseInt(fieldHour.getText()), Integer.parseInt(fieldMinutes.getText()), 0);

        Timestamp requiredDayG = new java.sql.Timestamp((Timestamp.valueOf(requiredDayTemp).getTime()));

        String cus = selectorClient.getValue().getCustomerEmail();
        Comanda c = new Comanda(this.idComanda, requiredDayG);
        DAOComanda.update(c);
        for (int i = 0; i < llistaObservableProductes.size(); i++) {
            ProductesComanda p = llistaObservableProductes.get(i);
            DAOComanda.saveProduct(p, idComanda);
        }
    }

    /**
     * Mètode que afegeix botons dins la cel·la d'accions de la TableView
     *
     * @author Víctor García - Creació/Implementació
     * @author Pablo Morante - Implementació
     */
    private void addCellButtons() {

        columnActions.setCellFactory(param -> new TableCell<ProductesComanda, ProductesComanda>() {

            private final Button btnDelete = new Button("");
            private final HBox container = new HBox(btnDelete);

            @Override
            protected void updateItem(ProductesComanda t, boolean empty) {
                super.updateItem(t, empty);

                if (t == null) {
                    setGraphic(null);
                    return;
                }

                // Inserir container amb botons a dins
                setGraphic(container);
                container.setId("container");
                container.setAlignment(Pos.CENTER);

                // FALTA IF/ELSE PARA SI LA COMANDA TIENE PRODUCTOS DENTRO NO PODER BORRARLA
                btnDelete.setId("btnDelete");
                btnDelete.setTooltip(new Tooltip("Eliminar comanda"));
                btnDelete.setOnAction(event -> {

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("AVÍS");
                    alert.setHeaderText("Estàs a punt d'eliminar el producte \"" + t.getNom() + "\" de la comanda. Vols continuar?");

                    ButtonType yesButton = new ButtonType("Sí");
                    ButtonType cancelButton = new ButtonType("No");

                    alert.getButtonTypes().setAll(yesButton, cancelButton);

                    if (alert.showAndWait().get() == yesButton) {
                        if (ComandesController.getComanda().getNumOrdre() != 0) {
                            DAOComanda.deleteProductFromComanda(t, ComandesController.getComanda().getNumOrdre());
                        }
                        llistaObservableProductes.remove(t);
                        calculateTotalAmount();
                    } else {
                        alert.close();
                    }
                });
            }
        }
        );
    }

    /**
     * Mètode per fer les comprovacions de què tots els camps estiguin plens i
     * compleixin les regles de negoci
     *
     * @author Pablo Morante - Creació/Implementació
     * @author Víctor García - Creació/Implementació
     * @return String amb el missatge d'error en cas que alguna condició no es
     * compleixi
     *
     * (RF40): No es pot donar d’alta una comanda amb zero línies de comanda.
     * @author Víctor García - Creació/Implementació
     *
     * (RF48) No es pot donar d'alta una comanda amb més import que el valor de
     * maxOrderAmount
     * @author Víctor García - Creació/Implementació
     *
     * (RF42) No es pot donar d'alta una comanda amb una diferència d'hores
     * menors a minShippingHours
     * @author Pablo Morante - Creació/Implementació
     */
    public String validacions() {
        float totalComanda = calculateTotalAmountCheckMaxOrderAmount();

        if (selectorClient.getValue() == null || fieldHour.getText().isEmpty() || fieldMinutes.getText().isEmpty() || datePicker.getValue() == null) {
            return "Per guardar una comanda s'han d'omplir tots els valors";
        }
        if ((Integer.parseInt(fieldHour.getText()) > 23) || (Integer.parseInt(fieldHour.getText()) < 0) || (Integer.parseInt(fieldMinutes.getText()) > 59) || (Integer.parseInt(fieldMinutes.getText()) < 0)) {
            return "El format d'hora ha d'estar entre 0 i 23 i el de minuts entre 0 i 59 ";
        }
        if (llistaObservableProductes.isEmpty()) {
            return "Una comanda ha de tenir entre 1 i 20 productes.";
        }
        Timestamp today = Timestamp.from(Instant.now());
        LocalDate requiredDay = datePicker.getValue();
        LocalDateTime requiredDayTemp = requiredDay.atTime(Integer.parseInt(fieldHour.getText()), Integer.parseInt(fieldMinutes.getText()), 0);

        Timestamp requiredDayG = new java.sql.Timestamp((Timestamp.valueOf(requiredDayTemp).getTime()));
        long milliseconds = requiredDayG.getTime() - today.getTime();
        long hour = TimeUnit.MILLISECONDS.toHours(milliseconds);
        if (hour < validate.getMinShippingHours()) {
            return "El mínim d'hores entre fer la comanda i enviar-la és de " + validate.getMinShippingHours() + "h.";
        }

        if (totalComanda > validate.getMaxOrderAmount()) {
            return "L'import màxim de la comanda no pot superar els " + validate.getMaxOrderAmount() + "€";
        }

        return "";
    }

}
