package presentacio;

import dades.ClientDAO;
import entitats.Client;
import entitats.ClientLogic;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.util.Callback;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.util.StringConverter;

/**
 * Controlador de la vista 'clients.fxml'. Permet a l'usuari gestionar el CRUD
 * dels clients des d'una UI.
 *
 * @author Pablo Morante - Creació
 * @author Victor García - Creació
 * @author Izan Jimenez - Creació vista FXML
 * @author Txell Llanas - Implementació
 */
public class ClientsController implements Initializable {

    @FXML
    private Button btnOrders, btnCustomers, btnProducts, btnAbout, 
            btnNewCustomer, btnClearContent;
    @FXML
    private TableView<Client> customersTableView;
    @FXML
    private TableColumn columnCustomerEmail, columnCustomerName, columnIdCard,
            columnBirthDate, columnPhone;
    @FXML
    private TableColumn<Client, Number> columnCreditLimit;
    @FXML
    private TableColumn<Client, Client> columnActions;
    @FXML
    private TextField inputSearchCustomer;
    
    private final Tooltip tooltipDesar = new Tooltip ("Desar Canvis");
    private final Tooltip tooltipEliminar = new Tooltip ("Eliminar Client");    

    // Definir una llista observable d'objectes de tipus Client
    private final ObservableList<Client> llistaObservableClient = FXCollections.observableArrayList();
    
    // Instància del ClientDAO per carregar els registres de la taula 'customers'
    private ClientDAO dataClient;
    
    // Instància del ClientLogic per carregar els mètodes de validacions
    private final ClientLogic validate = new ClientLogic();
    
   
    /**
     * Inicialitza el controlador.
     * 
     * @param url The location used to resolve relative paths for the root
     * object, or null if the location is not known.
     * @param rb The resources used to localize the root object, or null if the
     * root object was not localized.
     * @author Txell Llanas - Creació / Implementació 
     */        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // Obtenir el texte dels botons de la vista actual
        String text1 = btnOrders.getText();
        String text2 = btnCustomers.getText();
        String text3 = btnProducts.getText();
        String text4 = btnAbout.getText();
        String text5 = btnNewCustomer.getText();
        String text6 = btnClearContent.getText();
        
        // Passar el texte dels botons a MAJÚSCULES
        btnOrders.setText(text1.toUpperCase());
        btnCustomers.setText(text2.toUpperCase());
        btnProducts.setText(text3.toUpperCase());
        btnAbout.setText(text4.toUpperCase());
        btnNewCustomer.setText(text5.toUpperCase());
        btnClearContent.setText(text6.toUpperCase());
        
        // Recuperar registres taula 'customers'
        fillCustomersTable();
        
        // Filtrar registres segons el texte introduït al cercador
        searchCustomersDataFilter();
        
    }
    
    /**
     * Mostra l'apartat 'Comandes' i al llistat que conté tots els registres de 
     * la BD.
     * 
     * @throws IOException Excepció a mostrar en cas que no es trobi el Layout
     * @author Txell Llanas - Creació/Implementació
     */
    @FXML
    private void goToOrders() throws IOException {
        App.setRoot("comandes");
    }

    /**
     * Mostra l'apartat 'Productes' i al llistat que conté tots els registres de 
     * la BD.
     * 
     * @throws IOException Excepció a mostrar en cas que no es trobi el Layout
     * @author Txell Llanas - Creació/Implementació
     */
    @FXML
    private void goToProducts() throws IOException {
        App.setRoot("productes");

    }

    /**
     * Mostra l'apartat 'Crèdits'.
     * Indica la versió de l'aplicació i els seus desenvolupadors.
     * 
     * @throws IOException Excepció a mostrar en cas que no es trobi el Layout
     * @author Txell Llanas - Creació/Implementació
     */
    @FXML
    private void goToAbout() throws IOException {
        App.setRoot("credits");
    }

    /**
     * Mostra un formulari per donar d'alta un nou 'Client'.
     * 
     * @throws IOException Excepció a mostrar en cas que no es trobi el Layout
     * @author Txell Llanas - Creació/Implementació 
     */
    @FXML
    private void goToNewClient() throws IOException {
        App.setRoot("clientsForm");
    }

    /**
     * Mètode que neteja el camp de texte del cercador (clear).
     * 
     * @param event Acció que afecti al 'btnClearContent' (ex: clicar)
     * @author Txell Llanas - Creació/Implementació
     */
    @FXML
    private void clearContent(ActionEvent event) {
        inputSearchCustomer.clear();
    }
    
    /**
     * Mètode que recupera tots els registres de la taula 'customers'.
     * 
     * @author Txell Llanas - Creació/Implementació
     */
    private void fillCustomersTable(){
        
        try {
            
            // 1. Instància del ClientDAO per carregar els registres de la taula 'customers'
            dataClient = new ClientDAO();

            // 2. Afegir els registres existents a la taula dins la 'ObservableList'
            llistaObservableClient.addAll(dataClient.getAll());
        
            // 3. Vincular els atributs de Client amb cada columna de la taula per mostrar les dades recuperades dins el tableview:
            columnCustomerEmail.setCellValueFactory(new PropertyValueFactory<>("customerEmail"));
            columnCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));            
            columnIdCard.setCellValueFactory(new PropertyValueFactory<>("idCard"));            
            columnBirthDate.setCellValueFactory(new PropertyValueFactory<Client, Date>("birthDate"));
            columnPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));            
            columnCreditLimit.setCellValueFactory(new PropertyValueFactory<>("creditLimit"));
            
            // Afegir BOTONS ACCIONS
            columnActions.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
            addCellButtons();                                                   
            
            // 4. Aplicar estils pels camps NO EDITABLES
            columnCustomerEmail.setCellFactory(tc -> new TableCell<Client, String>() {
                @Override
                protected void updateItem(String value, boolean empty) {
                    super.updateItem(value, empty) ;
                    if (empty) {
                        setText(null);
                    } else {
                        getStyleClass().add("non-editable");
                        setText(value);
                    }                    
                }
            });
            columnIdCard.setCellFactory(tc -> new TableCell<Client, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty) ;
                    if (empty) {
                        setText(null);
                    } else {
                        getStyleClass().add("non-editable");
                        setText(item);
                    }                    
                }
            });
            
            // 5. Fer editable algunes columnes
            makeColsEditable();
            
            // 6. Afegir els registres a la taula
            customersTableView.setItems(llistaObservableClient);
            
        } catch (SQLException ex) {
            Logger.getLogger(ClientsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    /**
     * Mètode que afegeix botons dins la cel·la d'accions de la TableView.
     * 
     * @author Txell Llanas - Creació/Implementació
     */
    private void addCellButtons(){
        
        columnActions.setCellFactory(param -> new TableCell<Client, Client>() {
                
            private final Button btnEdit = new Button("");
            private final Button btnDelete = new Button("");
            private final HBox container = new HBox(btnEdit, btnDelete);

            @Override
            protected void updateItem(Client c, boolean empty) {
                super.updateItem(c, empty);

                if (c == null) {
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
                    alert.setHeaderText("Desitja actualitzar l'usuari \""+c.getCustomerName().toUpperCase()+"\"?");

                    ButtonType yesButton = new ButtonType("Sí");
                    ButtonType cancelButton = new ButtonType("No");

                    alert.getButtonTypes().setAll(yesButton, cancelButton);

                    if( alert.showAndWait().get() == yesButton ) {

                        dataClient.update(c);                                   // Actualitzar el registre actual dins la BD, taula 'customers'

                    } else {
                        
                        dataClient.getOne(c);                                   // Recuperar dades originals de la BD per revertir els canvis realitzats
                        alert.close();
                        
                    }

                    columnCustomerName.getStyleClass().add("netejar");          // Netejar estils aplicats als camps modificats
                    customersTableView.refresh();                               // Refrescar llistat (NECESSARI)

                });
                
                btnDelete.setId("btnDelete");                                   // Botó per eliminar registre actual
                btnDelete.setTooltip(tooltipEliminar);
                btnDelete.setOnAction(event -> {
                    
                    if( validate.clientHasOrders(c) > 0 ) {                     // Mostrar avís si el client té comandes actives
                        
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("NO ES POT ELIMINAR EL CLIENT");
                        alert.setHeaderText("El client \""+c.getCustomerName().toUpperCase()+"\" té comandes pendents.\nNo es pot eliminar de la base de dades.");
                        alert.show();
                        
                    } else {                                                    // Demanar confirmació per eliminar el client
                        
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("CONFIRMAR BAIXA");
                        alert.setHeaderText("Desitja eliminar l'usuari \""+c.getCustomerName().toUpperCase()+"\"?");

                        ButtonType yesButton = new ButtonType("Sí");
                        ButtonType cancelButton = new ButtonType("No");

                        alert.getButtonTypes().setAll(yesButton, cancelButton);

                        if( alert.showAndWait().get() == yesButton ) {                           
                            dataClient.delete(c);                               // Crido funció per eliminar el registre actual de la BD
                            llistaObservableClient.remove(c);                   // Elimino també del llistat al moment
                        } else
                            alert.close();
                    }
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
    private void makeColsEditable() {
            
            // 1. Fer editables les cel·les de dins una mateixa columna
            columnCustomerName.setCellFactory(TextFieldTableCell.forTableColumn());
            columnPhone.setCellFactory(TextFieldTableCell.forTableColumn());            
            columnCreditLimit.setCellFactory(col -> new NumberCell());          // Crear Classe per editar la cel·la amb valors numèrics
            columnBirthDate.setCellFactory(new Callback<TableColumn, TableCell>() { // Crear Classe per editar la cel·la amb valors de calendari
                @Override
                public TableCell call(TableColumn p) {
                    DatePickerCell datePick = new DatePickerCell();
                    return datePick;
                }
            }); 
           
            // 2. Desar els registres editats
            // Nom
            columnCustomerName.setOnEditCommit(event -> {
                
                // Recuperar l'objecte 'CellEditEvent' que ens dona informació de l'esdeveniment (si ha estat editat...)
                TableColumn.CellEditEvent e = (TableColumn.CellEditEvent) event;                
                
                // Desem els valors d'abans i després de la modificació
                String valorAntic = (String)e.getOldValue();
                String valorNou = (String)e.getNewValue();
                
                // Recuperem l'objecte 'Client' de la fila afectada
                Client c = (Client)e.getRowValue();
                
                // Detectar canvis aplicats
                if( valorNou.isBlank() || valorNou.isEmpty() ) {
                    
                    c.setCustomerName(valorAntic);                              // li assignem el nou valor (necessari x actualitzar nou valor editat)
                } else                    
                    c.setCustomerName(valorNou);                                // li assignem el nou valor (necessari x actualitzar nou valor editat)               
                
            });
            
            // Telèfon
            columnPhone.setOnEditCommit(event -> {
                
                // Recuperem l'objecte 'CellEditEvent' que ens dona informació de l'esdeveniment
                TableColumn.CellEditEvent e = (TableColumn.CellEditEvent) event;                
                
                // Desem els valors d'abans i després de la modificació
                String valorAntic = (String)e.getOldValue();
                String valorNou = (String)e.getNewValue();

                // Recuperem l'objecte 'Client' de la fila afectada
                Client c = (Client)e.getRowValue();
                
                // Detectar canvis aplicats
                if( valorNou.isBlank() || valorNou.isEmpty() ) {
                    
                    c.setPhone(valorAntic);                                     // li assignem el nou valor (necessari x actualitzar nou valor editat)
                } else                    
                    c.setPhone(valorNou);                                       // li assignem el nou valor (necessari x actualitzar nou valor editat)
                
            });
            
    }
    
    /***
     * Classe que extén de TableCell i permet a la cel·la actual contenir dades numèriques.
     * Conté una validació per evitar entrades incorrectes de l'usuari.
     * 
     * @author Txell Llanas - Creació/Implementació
     */
    public class NumberCell extends TableCell<Client, Number> {

        private final TextField textField = new TextField();        
        private final Pattern pattern = Pattern.compile("^\\d*\\.?\\d*$");      // Regex per limitar introducció de dades numèriques amb decimals  
        String defaultCredit = Float.toString(validate.getDefaultCreditLimit()); // String amb el valor per defecte pel Crèdit per assignar aun client
        
        /**
         * Constructor que serveix per crear un objecte de tipus NumberCell.
         */
        public NumberCell() {

            textField.setOnAction(event -> processEdit());
        }

        private void processEdit() {
            
            String value = textField.getText();
            
            if( value.isEmpty() )                                               // Evitar valors buits
                textField.setText("0");
            
            if ( pattern.matcher(value).matches() ) {                           // Si són nombres i/o amb decimals
            
                if (validate.checkCreditLimit(textField)) {                     // Validar dades introduïdes (valors mínim-màxim)
                    commitEdit(Float.parseFloat(value));                        // Desar canvis
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("QUANTITAT ERRÒNIA");
                    alert.setHeaderText("ERROR: 'Crèdit Màxim' incorrecte, indicar "
                                      + "un valor entre 0 i "+ defaultCredit);
                    alert.show();                                               // Mostrar error

                    cancelEdit();
                }
                
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("REVISI LES DADES INTRODUÏDES");
                alert.setHeaderText("ERROR: Només valors numèrics [amb o sense decimals (.)]" );
                alert.show();
            }
        }

        /**
         * Mètode que afegeix a l'original el visualitzar la dada numèrica com a un decimal de 2 xifres.
         * 
         * @param value Number amb el valor a processar
         * @param empty boolean per verificar si la cel·la conté informació o no
         */
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
                setText(String.format("%.2f",  Float.parseFloat(value.toString())) + " €");
                setGraphic(null);
            }
        }

        /**
         * Mètode que detecta si s'està editant la cel·la actual.
         */
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

        /**
         * Mètode que cancel·la l'edició de la cel·la actual.
         */
        @Override
        public void cancelEdit() {
            super.cancelEdit();
            setText(getItem().toString() + " €");
            setGraphic(null);
        }

        /**
         * Mètode que desa els canvis aplicats a la cel·la actual
         * @param value Number amb el valor a processar
         */
        @Override
        public void commitEdit(Number value) {
            super.commitEdit(value);
            ((Client)this.getTableRow().getItem()).setCreditLimit(value.floatValue());
        }
    }
    
    /***
     * Classe que extén de TableCell i permet a la cel·la actual seleccionar una data.
     * Conté una validació per evitar entrades incorrectes de l'usuari.
     * 
     * @author Txell Llanas - Creació/Implementació
     */
    public class DatePickerCell extends TableCell<Client, Date> {
        
        private DatePicker datePicker;
        private final TextField textField = new TextField();
        String datePattern = "dd/MM/yyyy";                                      // Format per aplicar al camp de la Data de Naixement                
        private final SimpleDateFormat dateOutput = new SimpleDateFormat(datePattern);   // Especificar format per la data 
        int defaultMinAge = validate.getDefaultMinCustomerAge();                // Valor per defecte d'edat mínima d'un client
        
        /**
         * Constructor que serveix per crear un objecte de tipus DatePicker. 
         */
        public DatePickerCell() {
            
            super();
            textField.setOnAction(event -> startEdit());
        }
        
        /**
         * Mètode que detecta si s'està editant la cel·la actual.
         */
        @Override
        public void startEdit() {
            super.startEdit();
            if (datePicker == null) {
                setText(textField.getText());
                createDatePicker();
            }
            
            setGraphic(datePicker);
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    datePicker.requestFocus();
                }
             });  
        }
        
        /**
         * Mètode que afegeix a l'original el visualitzar la dada numèrica com a un decimal de 2 xifres.
         * 
         * @param item Date amb el valor a processar
         * @param empty boolean per verificar si la cel·la conté informació o no
         */
        @Override
        public void updateItem(Date item, boolean empty) {

            super.updateItem(item, empty);

            if (empty) {
                setText(null);
                setGraphic(null);
            } else {

                if (isEditing()) {
                    setContentDisplay(ContentDisplay.TEXT_ONLY);

                } else {
                    setText(dateOutput.format(item));                           // data aniversari (marca format de la cel·la)
                    setGraphic(this.datePicker);
                    setContentDisplay(ContentDisplay.TEXT_ONLY);
                }
            }
        }

        /**
         * Mètode que cancel·la l'edició de la cel·la actual.
         */
        @Override
        public void cancelEdit() {
            super.cancelEdit();
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        }
    
        /**
         * Mètode que renderitza un calendari a la cel·la actual per poder definir 
         * la data de naixement del client.
         * 
         * @author Txell Llanas - Creació/Implementació
         */
        private void createDatePicker() {
                    
            // Variables inicials
            this.datePicker = new DatePicker();                                 // Crear UI calendari           
            Date aniversari = this.getTableRow().getItem().getBirthDate();      // Mostrar valor actual del client en format 'Date' (DatePicker només accepta aquest format)
            String clientBirthday = aniversari.toString();
            
            // 1. Recuperar data naixement desada a la BD
            datePicker.setValue(java.time.LocalDate.parse(clientBirthday));            
            
            // 2. Definir format:(dia/mes/any) a mostrar quan s'edita a dins el camp del calendari
            datePicker.setConverter(new StringConverter<LocalDate>() {
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(datePattern);

                @Override 
                public String toString(LocalDate date) {
                    if (date != null) {
                        return dateFormatter.format(date);                      // Aplico format a la data
                    } else {
                        return "";
                    }
                }

                @Override 
                public LocalDate fromString(String string) {
                    if (string != null && !string.isEmpty()) {
                        return LocalDate.parse(string, dateFormatter);          // Aplico format a la data
                    } else {
                        return null;
                    }
                }
            });
            
            // 3. Mostrar la data després de ser seleccionada al calendari
            setText(dateOutput.format(aniversari));          
            
            /* Actualitzar data naixement a l'objecte Client si ha canviat.
             * Si no hi ha canvis, la BD no queda afectada i es mostra el valor anterior  la cel·la
             */
            this.datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
                if(isEditing()) {
                    
                    if (validate.checkMinCustomerAge(datePicker)) {             // Validar dades introduïdes
                        // 1. Desar valor a la cel·la de la TableView
                        commitEdit(Date.valueOf(newValue));
                        // 2. Desar el nou valor a la instància Client actual
                        ((Client)this.getTableRow().getItem()).setBirthDate(java.sql.Date.valueOf(newValue));
                    
                    } else {
                        
                        // Mostrar el valor anterior
                        datePicker.setValue(java.time.LocalDate.parse(clientBirthday));
                        
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("EDAT DE L'USUARI ERRÒNIA");
                        alert.setHeaderText("Revisi la data introduïda, l'usuari no"
                                + " pot ser menor d'edat! (Edat mínima: " + defaultMinAge +" anys)");
                        alert.show();
                        
                        cancelEdit();
                    } 
                }
            });
              
            setAlignment(Pos.CENTER);
        }
    }
    
    /**
     * Mètode que filtra els registres segons el texte introduït al cercador.
     * 
     * @author Txell Llanas - Creació/Implementació
     */
    private void searchCustomersDataFilter(){
        
        FilteredList<Client> filteredData = new FilteredList<>(llistaObservableClient, b -> true);

        inputSearchCustomer.textProperty().addListener((observable, oldValue, newValue) -> {

            filteredData.setPredicate(client -> {

                // Si no s'escriu res al cercador, o no hi ha coincidències amb el texte introduït, mostra tots els registres
                if( newValue.isEmpty()|| newValue.isBlank() || newValue == null ) {
                    return true;
                }

                String searchKeyword = newValue.toLowerCase();

                // Definir filtres de cerca: buscar a tots els camps
                if( client.getCustomerEmail().toLowerCase().contains(searchKeyword) ) {
                    return true;
                } else if( client.getCustomerName().toLowerCase().contains(searchKeyword) ) {
                    return true;                       
                } else if( client.getIdCard().toLowerCase().contains(searchKeyword) ) {
                    return true;
                } else if( client.getBirthDate().toString().contains(searchKeyword) ) {
                    return true;
                } else if( client.getPhone().toLowerCase().contains(searchKeyword) ) {
                    return true;                       
                } else if( Float.toString(client.getCreditLimit()).contains(searchKeyword) ) {
                    return true;
                } else 
                    return false;                                               // Contingut no trobat

            });

        });

        // Ordenar els resultats coincidents (descarta els registres que no coincideixen amb les paraules cercades)
        SortedList<Client> sortedData = new SortedList<>(filteredData);

        // Establir vincle de la SortedList amb la TableView
        sortedData.comparatorProperty().bind(customersTableView.comparatorProperty());

        // Aplicar filtratge ordenat a la taula
        customersTableView.setItems(sortedData);
    }

}
