package presentacio;

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
import java.sql.Date;
import java.sql.SQLException;
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
import java.util.List;
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
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    private ObservableList<ProductesComanda> llistaObservableProductes = FXCollections.observableArrayList();

    private final Tooltip tooltipDesar = new Tooltip("Desar Canvis");
    private final Tooltip tooltipEliminar = new Tooltip("Eliminar Producte");

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

        this.idComanda = ComandesController.getIdComanda(); // obtenir id comanda actual

        if (idComanda == 0) { // si és 0 és que la comanda és nova
            orderNumber.setText("");
            TitolComanda.setText("Crear Comanda");
        } else { // si no, estableix el número de comanda
            orderNumber.setText(Integer.toString(ComandesController.getIdComanda()));
            TitolComanda.setText("Detall Comanda ");
        }

        fillDropDownList();
        fillProductsTable();
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
     * Mètode que afegeix botons dins la cel·la d'accions de la TableView
     *
     * @author Víctor García - Creació/Implementació
     */
//    private void addCellButtons() {
//
//        columnActions.setCellFactory(param -> new TableCell<ProductesComanda, ProductesComanda>() {
//
//            private final Button btnEdit = new Button("");
//            private final Button btnDelete = new Button("");
//            private final HBox container = new HBox(btnEdit, btnDelete);
//
//            @Override
//            protected void updateItem(ProductesComanda p, boolean empty) {
//                super.updateItem(p, empty);
//
//                if (p == null) {
//                    setGraphic(null);
//                    return;
//                }
//
//                // Inserir container amb botons a dins
//                setGraphic(container);
//                container.setId("container");
//                container.setAlignment(Pos.CENTER);
//
//                // Desar canvis registre actual
//                btnEdit.setId("btnEdit");
//                btnEdit.setTooltip(tooltipDesar);
//                btnEdit.setOnAction(event -> {
//
//                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//                    alert.setTitle("CONFIRMAR CANVIS");
//                    alert.setHeaderText("Desitja actualitzar el Producte \"" + p.getNom().toUpperCase() + "\"?");
//
//                    ButtonType yesButton = new ButtonType("Sí");
//                    ButtonType cancelButton = new ButtonType("No");
//
//                    alert.getButtonTypes().setAll(yesButton, cancelButton);
//
//                    if (alert.showAndWait().get() == yesButton) {
//
//                        DAOComanda.update(p);                                   // Actualitzar el registre actual dins la BD, taula 'products'
//
//                    } else {
//                        DAOComanda.getOne(p);                                   // Recuperar dades originals de la BD per revertir els canvis realitzats
//                        System.out.println("producte no modificat: "
//                                + DAOComanda.getOne(p));
//                        alert.close();
//                    }
//
//                    columnProductName.getStyleClass().add("netejar");           // Netejar estils aplicats als camps modificats
//                    orderLinesList.refresh();                                       // Refrescar llistat (NECESSARI)
//
//                });
//
//                btnDelete.setId("btnDelete");                                   // Botó per eliminar registre actual
//                btnDelete.setTooltip(tooltipEliminar);
//                btnDelete.setOnAction(event -> {
////
////                    if (validate.productIsInOrders(p) > 0) {                     // Mostrar avís si el client té comandes actives
////
////                        Alert alert = new Alert(Alert.AlertType.ERROR);
////                        alert.setTitle("NO ES POT ELIMINAR EL Producte");
////                        alert.setHeaderText("El producte \"" + p.getProductName().toUpperCase() + "\" existeix encara en comandes.\nNo es pot eliminar de la base de dades.");
////                        alert.show();
////
////                    } else {                                                    // Demanar confirmació per eliminar el client
////
////                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
////                        alert.setTitle("CONFIRMAR BAIXA");
////                        alert.setHeaderText("Desitja eliminar el producte \"" + p.getProductName().toUpperCase() + "\"?");
////
////                        ButtonType yesButton = new ButtonType("Sí");
////                        ButtonType cancelButton = new ButtonType("No");
////
////                        alert.getButtonTypes().setAll(yesButton, cancelButton);
////
////                        if (alert.showAndWait().get() == yesButton) {
//                    dataProducte.delete(p);                               // Crido funció per eliminar el registre actual de la BD
//                    llistaObservableProducte.remove(p);                   // Elimino també del llistat al moment
////                        } else {
////                            alert.close();
////                        }
////                    }
//                });
//
//            }
//        }
//        );
//    }
    /**
     * Mostra l'apartat 'Comandes' i un llistat que conté tots els registres de
     * la BD.
     *
     * @throws IOException Excepció a mostrar en cas que no es trobi el Layout
     * @author Txell Llanas - Creació
     */
    @FXML
    private void goToOrdersList(ActionEvent event) throws IOException {

        // Carregar la vista del llistat "COMANDES (Llistat)" in-situ
        App.setRoot("comandes");
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
     */
    @FXML
    private void addProduct() {
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
            ProductesComanda newProduct = new ProductesComanda();
            newProduct.setIdProducte(temp.getProductCode());
            newProduct.setNumberLine(llistaObservableProductes.size() + 1);
            newProduct.setOrderNummber(this.idComanda);
            newProduct.setQuantitat(1);
            newProduct.setUnitaryPrice(temp.getBuyPrice());
            newProduct.setNom(temp.getProductName());
            newProduct.setTotal(temp.getBuyPrice() * 1);
            llistaObservableProductes.add(newProduct);
        }
        calculateTotalAmount();
    }

    private void calculateTotalAmount() {
        float total = 0;

        for (int i = 0; i < llistaObservableProductes.size(); i++) {
            total = total + llistaObservableProductes.get(i).getTotal();
        }

        totalAmount.setText("" + total);
    }

    /**
     * Mètode per crear una nova comanda
     *
     * @author Pablo Morante - Creació/Implementació
     * @author Víctor García - Creació/Implementació
     */
    private void createNewCommand() throws SQLException, ParseException {
        LocalDate today = java.time.LocalDate.now();
        LocalDate requiredDay = datePicker.getValue();
        String temp = requiredDay.toString() + " " + fieldHour.getText() + ":" + fieldMinutes.getText() + ":" + "00";
        System.out.println("hora temp " + temp);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("formatter " + formatter.parse(temp));
        Date requiredDayG = new java.sql.Date((formatter.parse(temp)).getTime());
        System.out.println("required day " + requiredDayG);

        String cus = selectorClient.getValue().getCustomerEmail();
        System.out.println(cus);

        Comanda c = new Comanda(java.sql.Date.valueOf(today), requiredDayG, cus);

        DAOComanda = new ComandaDAO();
        DAOComanda.save(c);
        for (int i = 0; i < llistaObservableProductes.size(); i++) {
            ProductesComanda p = llistaObservableProductes.get(i);
            DAOComanda.saveProduct(true, p, idComanda);
        }
    }

    /**
     * Mètode per fer update a una comanda ja existent
     *
     * @author Pablo Morante - Creació/Implementació
     * @author Víctor García - Creació/Implementació
     */
    private void updateCommand() throws SQLException {
        DAOComanda = new ComandaDAO();
        for (int i = 0; i < llistaObservableProductes.size(); i++) {
            ProductesComanda p = llistaObservableProductes.get(i);
            DAOComanda.saveProduct(true, p, idComanda);
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
                        if (ComandesController.getIdComanda() != 0) {
                            DAOComanda.deleteProductFromComanda(t, ComandesController.getIdComanda());
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

}
