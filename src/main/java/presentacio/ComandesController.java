package presentacio;

import dades.ComandaDAO;
import entitats.Client;
import entitats.Comanda;
import entitats.ComandaLogic;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import java.sql.Date;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;

/**
 * Controlador de la vista 'comandes.fxml'. Permet a l'usuari gestionar el CRUD
 * d'una capçalera de comanda des d'una UI.
 *
 * @author Txell Llanas - Creació vista FXML/Implementació
 * @author Pablo Morante - Creació/Implementació
 * @author Victor García - Creació/Implementació
 */
public class ComandesController implements Initializable {

    @FXML
    private Button btnOrders;
    @FXML
    private Button btnCustomers;
    @FXML
    private Button btnProducts;
    @FXML
    private Button btnAbout;
    @FXML
    private Button btnNewOrder;
    @FXML
    private DatePicker datePickerFrom;
    @FXML
    private DatePicker datePickerTo;
    @FXML
    private Button btnSearchOrder;
    @FXML
    private TableView<Comanda> ordersList;
    @FXML
    private TableColumn columnOrderNumber;
    @FXML
    private TableColumn columnRequiredDate;
    @FXML
    private TableColumn columnShippedDate;
    @FXML
    private TableColumn columnCustomerEmail;
    @FXML
    private TableColumn<Comanda, Comanda> columnActions;

    private Tooltip tooltipEliminar = new Tooltip("Eliminar Comanda");

    private Tooltip tooltipDetail = new Tooltip("Veure Comanda");

    // Definir una llista observable d'objectes de tipus Client
    private ObservableList<Comanda> llistaObservableComanda = FXCollections.observableArrayList();

    // Instància de ComandaDAO per carregar els registres de la taula 'orders'
    private ComandaDAO dataComanda;

    // Instància de ComandaLogic per carregar els mètodes de validacions
    private ComandaLogic validate = new ComandaLogic();

    /**
     * Inicialitza els components especificats.
     *
     * @param url
     * @param rb
     * @author Txell Llanas - Creació/Implementació
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Obtenir el texte dels botons
        String text1 = btnOrders.getText();
        String text2 = btnCustomers.getText();
        String text3 = btnProducts.getText();
        String text4 = btnAbout.getText();
        String text5 = btnNewOrder.getText();
        String text6 = btnSearchOrder.getText();

        // Passar el texte a MAJÚSCULES
        btnOrders.setText(text1.toUpperCase());
        btnCustomers.setText(text2.toUpperCase());
        btnProducts.setText(text3.toUpperCase());
        btnAbout.setText(text4.toUpperCase());
        btnNewOrder.setText(text5.toUpperCase());
        btnSearchOrder.setText(text6.toUpperCase());

        // Recuperar registres taula 'orders'
        fillOrdersTable();
    }

    /**
     * Mostra un formulari per 'Crear' o 'Editar' una comanda.
     *
     * @throws IOException Excepció a mostrar en cas que no es trobi el Layout
     * @author Txell Llanas - Creació
     */
    @FXML
    private void goToNewOrder() throws IOException {

        // Carregar la vista del formulari "COMANDES (Detalls)" in-situ
        App.setRoot("comandesForm");
    }

    /**
     * Mètode que recupera tots els registres de la taula 'orders'.
     *
     * @author Víctor García - Creació/Implementació
     */
    private void fillOrdersTable() {

        try {

            dataComanda = new ComandaDAO();

            llistaObservableComanda.addAll(dataComanda.getAll());

            // Vincular els atributs de Comanda amb cada columna de la taula per mostrar les dades recuperades dins el tableview:
            columnOrderNumber.setCellValueFactory(new PropertyValueFactory<>("numOrdre"));
            columnRequiredDate.setCellValueFactory(new PropertyValueFactory<Comanda, Date>("dataEntrega"));
            columnShippedDate.setCellValueFactory(new PropertyValueFactory<Comanda, Date>("dataEnviament"));
            columnCustomerEmail.setCellValueFactory(new PropertyValueFactory<>("customers_customerEmail"));

            columnActions.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
            addCellButtons(); // Afegir BOTONS ACCIONS

            // Aplicar estils pels camps NO EDITABLES
            columnOrderNumber.setCellFactory(tc -> new TableCell<Comanda, Integer>() {
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

            //makeColsEditable();
            // Afegir els registres a la taula
            ordersList.setItems(llistaObservableComanda);

        } catch (SQLException ex) {
            Logger.getLogger(ComandesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Mètode que afegeix botons dins la cel·la d'accions de la TableView
     *
     * @author Víctor García - Implementació
     */
    private void addCellButtons() {

        columnActions.setCellFactory(param -> new TableCell<Comanda, Comanda>() {

            private final Button btnDetail = new Button("");
            private final Button btnDelete = new Button("");
            private final HBox container = new HBox(btnDetail, btnDelete);

            @Override
            protected void updateItem(Comanda t, boolean empty) {
                super.updateItem(t, empty);

                if (t == null) {
                    setGraphic(null);
                    return;
                }

                // Inserir container amb botons a dins
                setGraphic(container);
                container.setId("container");
                container.setAlignment(Pos.CENTER);

                // Desar canvis registre actual
                btnDetail.setId("btnDetail");
                btnDetail.setTooltip(tooltipDetail);
//                btnEdit.setOnAction(event -> {
//
//                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//                    alert.setTitle("CONFIRMAR CANVIS");
//                    alert.setHeaderText("Desitja actualitzar l'usuari \""+c.getCustomerName().toUpperCase()+"\"?");
//
//                    ButtonType yesButton = new ButtonType("Sí");
//                    ButtonType cancelButton = new ButtonType("No");
//
//                    alert.getButtonTypes().setAll(yesButton, cancelButton);
//
//                    if( alert.showAndWait().get() == yesButton ) {
//
//                        dataClient.update(c);                                   // Actualitzar el registre actual dins la BD, taula 'customers'
//
//                    } else {
//                        dataClient.getOne(c);                                   // Recuperar dades originals de la BD per revertir els canvis realitzats
//                        System.out.println("client no modificat: "
//                                           + dataClient.getOne(c));
//                        alert.close();
//                    }
//
//                    columnCustomerName.getStyleClass().add("netejar");          // Netejar estils aplicats als camps modificats
//                    customersTableView.refresh();                               // Refrescar llistat (NECESSARI)
//
//                });
                btnDelete.setId("btnDelete");                                   // Botó per eliminar registre actual
                btnDelete.setTooltip(tooltipEliminar);
                btnDelete.setOnAction(event -> {

                    try {
                        dataComanda.delete(t);                               // Crido funció per eliminar el registre actual de la BD
                    } catch (SQLException ex) {
                        Logger.getLogger(ComandesController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    llistaObservableComanda.remove(t);

//                    if( validate.clientHasOrders(t) > 0 ) {                     // Mostrar avís si el client té comandes actives
//                        
//                        Alert alert = new Alert(Alert.AlertType.ERROR);
//                        alert.setTitle("CNO ES POT ELIMINAR EL CLIENT");
//                        alert.setHeaderText("El client \""+c.getCustomerName().toUpperCase()+"\" té comandes pendents.\nNo es pot eliminar de la base de dades.");
//                        alert.show();
//                        
//                    } else {                                                    // Demanar confirmació per eliminar el client
//                        
//                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//                        alert.setTitle("CONFIRMAR BAIXA");
//                        alert.setHeaderText("Desitja eliminar l'usuari \""+c.getCustomerName().toUpperCase()+"\"?");
//
//                        ButtonType yesButton = new ButtonType("Sí");
//                        ButtonType cancelButton = new ButtonType("No");
//
//                        alert.getButtonTypes().setAll(yesButton, cancelButton);
//
//                        if( alert.showAndWait().get() == yesButton ) {                           
//                            dataClient.delete(c);                               // Crido funció per eliminar el registre actual de la BD
//                            llistaObservableClient.remove(c);                   // Elimino també del llistat al moment
//                        } else
//                            alert.close();
//                    }
                });
            }
        });
    }

    /**
     * Mètode que defineix com a editables les dades de tots els camps d'una
     * mateixa columna.
     *
     * @author Txell Llanas - Creació/Implementació
     */
//    private void makeColsEditable() {
//            
//            // 1. Fer editables les cel·les de dins una mateixa columna
//            columnCustomerName.setCellFactory(TextFieldTableCell.forTableColumn());
//            //columnBirthDate.setCellFactory(TextFieldTableCell.forTableColumn());
//            columnPhone.setCellFactory(TextFieldTableCell.forTableColumn());
//            
//            columnCreditLimit.setCellFactory(col -> new NumberCell());          // Crear Classe per editar la cel·la amb valors numèrics
//           // columnBirthDate.setCellFactory(col -> new DatePickerCell());      // Crear Classe per editar la cel·la amb valors de calendari
//            
//           System.out.println("llistaObservableClient: "+llistaObservableClient.size());
//            //LocalDate date = LocalDate.of(2020, 1, 8);
//            Date date = Date.valueOf("2014-02-14");
//            //Date.valueOf(field_birthDate.getValue()));
//            
//            columnBirthDate.setCellFactory(new Callback<TableColumn, TableCell>() {
//                @Override
//                public TableCell call(TableColumn p) {
//                    DatePickerCell datePick = new DatePickerCell();
//                    return datePick;
//                }
//            });
//           
//           
//            // 2. Desar els registres editats
//            columnCustomerName.setOnEditCommit(event -> {
//                
//                // Recuperar l'objecte 'CellEditEvent' que ens dona informació de l'esdeveniment (si ha estat editat...)
//                TableColumn.CellEditEvent e = (TableColumn.CellEditEvent) event;                
//                
//                // Desem els valors d'abans i després de la modificació
//                String valorAntic = (String)e.getOldValue();
//                String valorNou = (String)e.getNewValue();
//                System.out.println("Valor antic:" + valorAntic);
//                System.out.println("Valor nou:" + valorNou);
//                
//                
//                //columnCustomerName.getStyleClass().clear();
//                //columnCustomerName.getStyleClass().add("changed");
//                //applyStyleToEditedCell(columnCustomerName, e);
//                
//                
//                // ** ACCIÓ x DESAR (BOTÓ SAVE)
//                // Recuperem l'objecte 'Client' de la fila afectada
//                Client c = (Client)e.getRowValue();
//                
//                // Detectar canvis
//                //if(valorNou.equalsIgnoreCase(valorAntic) || valorNou.isBlank() || valorNou.isEmpty()) {
//                if( valorNou.isBlank() || valorNou.isEmpty() ) {
//                    //valorNou = valorAntic; // mostra valor original al llistat
//                    //li assignem el nou valor (necessari x actualitzar nou valor editat)
//                    c.setCustomerName(valorAntic);
//                } else                    
//                    c.setCustomerName((String)e.getNewValue());//li assignem el nou valor (necessari x actualitzar nou valor editat)             
//                
//                System.out.println("Cond. 1: "+valorNou.equalsIgnoreCase(valorAntic));
//                System.out.println("Cond. 2: "+valorNou.isBlank());
//                System.out.println("Cond. 3: "+valorNou.isEmpty());
//            });
//            
//            // Modificar telèfon
//            columnPhone.setOnEditCommit(event -> {
//                
//                //recuperem l'objecte CellEditEvent que ens dona informació de l'esdeveniment
//                TableColumn.CellEditEvent e = (TableColumn.CellEditEvent) event;                
//                
//                //valor antic abans de la modificació
//                String valorAntic = (String)e.getOldValue();
//                System.out.println("Valor antic:" + valorAntic);
//
//                //valor nou
//                String valorNou = (String)e.getNewValue();
//                System.out.println("Valor nou:" + valorNou);
//
//                // ** ACCIÓ x DESAR (BOTÓ SAVE)
//                //recuperem l'objecte Client de la fila afectada
//                Client c = (Client)e.getRowValue();
//                
//                //li assignem el nou valor (necessari x actualitzar nou valor editat)
//                c.setPhone(valorNou);
//                
//                //applyStyleToEditedCell(columnPhone, e);
//                
//            });
//    }
    
    /**
     * Mostra l'apartat 'Clients' i al llistat que conté tots els registres de
     * la BD.
     *
     * @throws IOException Excepció a mostrar en cas que no es trobi el Layout
     * @author Txell Llanas - Creació
     */
    @FXML
    private void goToCustomers() throws IOException {
        App.setRoot("clients");
    }

    /**
     * Mostra l'apartat 'Productes' i al llistat que conté tots els registres de
     * la BD.
     *
     * @throws IOException Excepció a mostrar en cas que no es trobi el Layout
     * @author Txell Llanas - Creació
     */
    @FXML
    private void goToProducts() throws IOException {
        App.setRoot("productes");
    }

    /**
     * Mostra l'apartat 'Crèdits'. Indica la versió de l'aplicació i els seus
     * desenvolupadors.
     *
     * @throws IOException Excepció a mostrar en cas que no es trobi el Layout
     * @author Txell Llanas - Creació
     */
    @FXML
    private void goToAbout(ActionEvent event) throws IOException {
        //App.setRoot("about");  <-- descomentar quan ho editeu
    }

    /**
     * Mètode per cercar comandes que es trobin entre l'interval de dates
     * especificat.
     *
     * @author Txell Llanas - Creació
     */
    @FXML
    private void searchOrderBetweenDates() {
    }

}
