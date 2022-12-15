package presentacio;

import dades.DAOInterface;
import dades.ProducteDAO;
import entitats.Producte;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import entitats.ProducteLogic;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

/**
 * Controlador de la vista 'productes.fxml'. Permet a l'usuari gestionar el CRUD
 * dels productes des d'una UI.
 *
 * @author Pablo Morante - Creació
 * @author Victor García - Creació
 * @author Izan Jimenez - Creació vista FXML/Implementació
 */
public class ProductesController implements Initializable {

    @FXML
    private Button btnOrders, btnCustomers, btnProducts, btnAbout,
            btnNewCustomer, btnSearchCustomer;
    @FXML
    private TableView<Producte> productList;
    @FXML
    private TextField textFieldCercarProducte;

    @FXML
    private TableColumn columnProductCode, columnProductName,
            columnProductDescription, columnProductStock, columnProductBuyPice;
    ;
    @FXML
    private TableColumn<Producte, Producte> columnActions;

    private final Tooltip tooltipDesar = new Tooltip("Desar Canvis");
    private final Tooltip tooltipEliminar = new Tooltip("Eliminar Producte");

    // Instància del ProducteDAO per carregar els registres de la taula 'products'
    private ProducteDAO dataProducte;

    // Instància del ProducteLogic per carregar els mètodes de validacions
    private ProducteLogic validate = new ProducteLogic();

    //INstancia de la llista que carrega el TableView
    private ObservableList<Producte> llistaObservableProducte = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            emplenarTaula();
            searchCustomersDataFilter();
        } catch (SQLException ex) {
            System.out.println(ex);
        }

    }

    @FXML
    private void goToOrders(ActionEvent event) throws IOException {
        App.setRoot("comandes");
    }

    @FXML
    private void goToCustomers(ActionEvent event) throws IOException {
        App.setRoot("clients");

    }

    @FXML
    private void goToProducts(ActionEvent event) {
    }

    @FXML
    private void goToAbout() throws IOException {
        App.setRoot("credits");
    }

    @FXML
    private void searchCustomer(ActionEvent event) {
    }

    @FXML
    private void goToNewProduct(ActionEvent event) throws IOException {
        App.setRoot("productesForm");
    }

    /**
     * *
     * EMplena la taula
     *
     * @throws SQLException
     * @author
     */
    private void emplenarTaula() throws SQLException {

        dataProducte = new ProducteDAO();

        //afegim els elements
        llistaObservableProducte.addAll(dataProducte.getAll());
        System.out.println(dataProducte.getAll());

        //Establim un vincle entre els atributs de l'objecte Producte i cada columna del tableview.
        columnProductCode.setCellValueFactory(new PropertyValueFactory<>("productCode"));
        columnProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        columnProductDescription.setCellValueFactory(new PropertyValueFactory<>("ProductDescription"));
        columnProductStock.setCellValueFactory(new PropertyValueFactory<>("QuantityInStock"));
        columnProductBuyPice.setCellValueFactory(new PropertyValueFactory<>("BuyPrice"));

        //Afegir BOTONS ACCIONS
        columnActions.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        addCellButtons();

        //Aplicar estils pels camps NO EDITABLES
        columnProductCode.setCellFactory(tc -> new TableCell<Producte, Integer>() {
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
        columnProductName.setCellFactory(tc -> new TableCell<Producte, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    getStyleClass().add("non-editable");
                    setText(item);
                }
            }
        });

        makeColsEditable();
        productList.setItems(llistaObservableProducte);

    }

    /**
     * Mètode que defineix com a editables les dades de tots els camps d'una
     * mateixa columna.
     *
     * @author Txell Llanas - Creació/Implementació
     */
    private void makeColsEditable() {

        // 1. Fer editables les cel·les de dins una mateixa columna
       columnProductDescription.setCellFactory(TextFieldTableCell.forTableColumn());
        columnProductBuyPice.setCellFactory(col -> new NumberCellFloat());          // Crear Classe per editar la cel·la amb valors numèrics
        columnProductStock.setCellFactory(col -> new NumberCellInt());          // Crear Classe per editar la cel·la amb valors numèrics
        System.out.println("llistaObservableProducte: " + llistaObservableProducte.size());

        // 2. Desar els registres editats
        columnProductDescription.setOnEditCommit(event -> {

            //recuperem l'objecte CellEditEvent que ens dona informació de l'esdeveniment
            TableColumn.CellEditEvent e = (TableColumn.CellEditEvent) event;

            //valor antic abans de la modificació
            String valorAntic = (String) e.getOldValue();
            System.out.println("Valor antic:" + valorAntic);

            //valor nou
            String valorNou = (String) e.getNewValue();
            System.out.println("Valor nou:" + valorNou);

            // ** ACCIÓ x DESAR (BOTÓ SAVE)
            //recuperem l'objecte Producte de la fila afectada
            Producte p = (Producte) e.getRowValue();

            //li assignem el nou valor (necessari x actualitzar nou valor editat)
            p.setProductDescription(valorNou);

        });

        columnProductStock.setOnEditCommit(event -> {

            //recuperem l'objecte CellEditEvent que ens dona informació de l'esdeveniment
            TableColumn.CellEditEvent e = (TableColumn.CellEditEvent) event;

            //valor antic abans de la modificació
            int valorAntic = (int) e.getOldValue();
            System.out.println("Valor antic:" + valorAntic);

            //valor nou
            int valorNou = (int) e.getNewValue();
            System.out.println("Valor nou:" + valorNou);

            // ** ACCIÓ x DESAR (BOTÓ SAVE)
            //recuperem l'objecte Producte de la fila afectada
            Producte p = (Producte) e.getRowValue();

            //li assignem el nou valor (necessari x actualitzar nou valor editat)
            p.setQuantityInStock(valorNou);

        });

        columnProductBuyPice.setOnEditCommit(event -> {

            //recuperem l'objecte CellEditEvent que ens dona informació de l'esdeveniment
            TableColumn.CellEditEvent e = (TableColumn.CellEditEvent) event;

            //valor antic abans de la modificació
            float valorAntic = (float) e.getOldValue();
            System.out.println("Valor antic:" + valorAntic);

            //valor nou
            float valorNou = (float) e.getNewValue();
            System.out.println("Valor nou:" + valorNou);

            // ** ACCIÓ x DESAR (BOTÓ SAVE)
            //recuperem l'objecte Producte de la fila afectada
            Producte p = (Producte) e.getRowValue();

            //li assignem el nou valor (necessari x actualitzar nou valor editat)
            p.setBuyPrice(valorNou);

        });
    }

    /**
     * Mètode que afegeix botons dins la cel·la d'accions de la TableView
     *
     * @author Izan Jimenez - Creació/Implementació
     */
    private void addCellButtons() {

        columnActions.setCellFactory(param -> new TableCell<Producte, Producte>() {

            private final Button btnEdit = new Button("");
            private final Button btnDelete = new Button("");
            private final HBox container = new HBox(btnEdit, btnDelete);

            @Override
            protected void updateItem(Producte p, boolean empty) {
                super.updateItem(p, empty);

                if (p == null) {
                    setGraphic(null);
                    return;
                }

                // Inserir container amb botons a dins
                setGraphic(container);
                container.setId("container");
                container.setAlignment(Pos.CENTER);

                // Desar canvis registre actual
                btnEdit.setId("btnEdit");
                btnEdit.setTooltip(tooltipDesar);
                btnEdit.setOnAction(event -> {

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("CONFIRMAR CANVIS");
                    alert.setHeaderText("Desitja actualitzar el Producte \"" + p.getProductName().toUpperCase() + "\"?");

                    ButtonType yesButton = new ButtonType("Sí");
                    ButtonType cancelButton = new ButtonType("No");

                    alert.getButtonTypes().setAll(yesButton, cancelButton);

                    if (alert.showAndWait().get() == yesButton) {

                        dataProducte.update(p);                                   // Actualitzar el registre actual dins la BD, taula 'products'

                    } else {
                        dataProducte.getOne(p);                                   // Recuperar dades originals de la BD per revertir els canvis realitzats
                        System.out.println("producte no modificat: "
                                + dataProducte.getOne(p));
                        alert.close();
                    }

                    columnProductName.getStyleClass().add("netejar");           // Netejar estils aplicats als camps modificats
                    productList.refresh();                                       // Refrescar llistat (NECESSARI)

                });

                btnDelete.setId("btnDelete");                                   // Botó per eliminar registre actual
                btnDelete.setTooltip(tooltipEliminar);
                btnDelete.setOnAction(event -> {
//
//                    if (validate.productIsInOrders(p) > 0) {                     // Mostrar avís si el client té comandes actives
//
//                        Alert alert = new Alert(Alert.AlertType.ERROR);
//                        alert.setTitle("NO ES POT ELIMINAR EL Producte");
//                        alert.setHeaderText("El producte \"" + p.getProductName().toUpperCase() + "\" existeix encara en comandes.\nNo es pot eliminar de la base de dades.");
//                        alert.show();
//
//                    } else {                                                    // Demanar confirmació per eliminar el client
//
//                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//                        alert.setTitle("CONFIRMAR BAIXA");
//                        alert.setHeaderText("Desitja eliminar el producte \"" + p.getProductName().toUpperCase() + "\"?");
//
//                        ButtonType yesButton = new ButtonType("Sí");
//                        ButtonType cancelButton = new ButtonType("No");
//
//                        alert.getButtonTypes().setAll(yesButton, cancelButton);
//
//                        if (alert.showAndWait().get() == yesButton) {
                    dataProducte.delete(p);                               // Crido funció per eliminar el registre actual de la BD
                    llistaObservableProducte.remove(p);                   // Elimino també del llistat al moment
//                        } else {
//                            alert.close();
//                        }
//                    }
                });

            }
        });
    }

    /**
     * *
     * Classe que extén de TableCell i permet a la cel·la actual contenir dades
     * numèriques no decimals. Conté una validació per evitar entrades incorrectes de
     * l'usuari.
     *
     * @author Izan Jimenez - Implementació / Creació
     */
    public class NumberCellInt extends TableCell<Producte, Number> {

        private final TextField textField = new TextField();
        private final Pattern pattern = Pattern.compile("[0-9]+");

        public NumberCellInt() {
            textField.setOnAction(event -> processEdit());
        }

        private void processEdit() {

            String value = textField.getText();

            if (value.isEmpty()) // Evitar valors buits
            {
                textField.setText("0");
            }

            if (pattern.matcher(value).matches()) {                           // Si són nombres i/o amb decimals

                if (validate.checkStock(textField)) {                         // Validar dades introduïdes (valors mínim-màxim)
                    commitEdit(Integer.parseInt(value));                             // Desar canvis
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("STOCK ERRÒNI");
                    alert.setHeaderText("ERROR: Stock incorrecte, indicar "
                            + "un valor major a 1");
                    alert.show();                                                   // Mostrar error

                    cancelEdit();
                }

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("REVISI LES DADES INTRODUÏDES");
                alert.setHeaderText("ERROR: Només valors numèrics");
                alert.show();
            }
        }

        @Override
        public void updateItem(Number value, boolean empty) {
            super.updateItem(value, empty);
            if (empty) {
                setText(null);
                setGraphic(null);
            } else if (isEditing()) {
                setText(null);
                textField.setText(value.toString());
                setGraphic(textField);
            } else {
                setText(String.format(String.valueOf(value.intValue())));
                setGraphic(null);
            }
        }

        @Override
        public void startEdit() {
            super.startEdit();
            Number value = getItem();
            if (value != null) {
                textField.setText(value.toString());
                setGraphic(textField);
                setText(null);
            }
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();
            setText(getItem().toString());
            setGraphic(null);
        }

        @Override
        public void commitEdit(Number value) {
            super.commitEdit(value);
            ((Producte) this.getTableRow().getItem()).setQuantityInStock(value.intValue());
        }
    }

    /**
     * *
     * Classe que extén de TableCell i permet a la cel·la actual contenir dades
     * numèriques. Conté una validació per evitar entrades incorrectes de
     * l'usuari.
     *
     * @author Izan Jimenez - Implementació
     */
    public class NumberCellFloat extends TableCell<Producte, Number> {

        private final TextField textField = new TextField();
        private final Pattern pattern = Pattern.compile("^\\d*\\.?\\d*$");

        public NumberCellFloat() {
            textField.setOnAction(event -> processEdit());
        }

        private void processEdit() {

            String value = textField.getText();

            if (value.isEmpty()) // Evitar valors buits
            {
                textField.setText("0");
            }

            if (pattern.matcher(value).matches()) {                           // Si són nombres i/o amb decimals

                if (validate.checkPreu(textField)) {                         // Validar dades introduïdes (valors mínim-màxim)
                    commitEdit(Float.parseFloat(value));                             // Desar canvis
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("PREU ERRÒNIA");
                    alert.setHeaderText("ERROR: Preu  incorrecte, indicar "
                            + "un valor major a 0");
                    alert.show();                                                   // Mostrar error

                    cancelEdit();
                }

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("REVISI LES DADES INTRODUÏDES");
                alert.setHeaderText("ERROR: Només valors numèrics (amb o sense decimals)");
                alert.show();
            }
        }

        @Override
        public void updateItem(Number value, boolean empty) {
            super.updateItem(value, empty);
            if (empty) {
                setText(null);
                setGraphic(null);
            } else if (isEditing()) {
                setText(null);
                textField.setText(value.toString());
                setGraphic(textField);
            } else {
                setText(String.format("%.2f", Float.parseFloat(value.toString())) + " €");
                setGraphic(null);
            }
        }

        @Override
        public void startEdit() {
            super.startEdit();
            Number value = getItem();
            if (value != null) {
                textField.setText(value.toString());
                setGraphic(textField);
                setText(null);
            }
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();
            setText(getItem().toString() + " €");
            setGraphic(null);
        }

        @Override
        public void commitEdit(Number value) {
            super.commitEdit(value);
            ((Producte) this.getTableRow().getItem()).setBuyPrice(value.floatValue());
        }
    }

    /**
     * Mètode que filtra els registres segons el texte introduït al cercador.
     *
     * @author Izan Jimenez - Creació/Implementació
     */
    private void searchCustomersDataFilter() {

        FilteredList<Producte> filteredData = new FilteredList<>(llistaObservableProducte, b -> true);

        textFieldCercarProducte.textProperty().addListener((observable, oldValue, newValue) -> {

            filteredData.setPredicate(producte -> {

                // Si no s'escriu res al cercador, o no hi ha coincidències amb el texte introduït, mostra tots els registres
                if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                    return true;
                }

                String searchKeyword = newValue.toLowerCase();

                // Definir filtres de cerca: buscar a tots els camps
                if (Integer.toString(producte.getProductCode()).toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if (producte.getProductName().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if (producte.getProductDescription().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if (Integer.toString(producte.getQuantityInStock()).toString().contains(searchKeyword)) {
                    return true;
                } else if (Float.toString(producte.getBuyPrice()).toLowerCase().contains(searchKeyword)) {
                    return true;
                } else {
                    return false; // Contingut no trobat
                }
            });

        });

        // Ordenar els resultats coincidents (descarta els registres que no coincideixen amb les paraules cercades)
        SortedList<Producte> sortedData = new SortedList<>(filteredData);

        // Establir vincle de la SortedList amb la TableView
        sortedData.comparatorProperty().bind(productList.comparatorProperty());

        // Aplicar filtratge a la taula
        productList.setItems(sortedData);
    }

}
