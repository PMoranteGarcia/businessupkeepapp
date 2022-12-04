package presentacio;

import dades.ClientDAO;
import entitats.Client;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

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
            columnBirthDate, columnPhone, columnCreditLimit, columnActions;
    @FXML
    private TextField inputSearchCustomer;
    

    // Definir una llista observable d'objectes de tipus Client
    ObservableList<Client> llistaObservableClient = FXCollections.observableArrayList();
    
            
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // Obtenir el texte dels botons
        String text1 = btnOrders.getText();
        String text2 = btnCustomers.getText();
        String text3 = btnProducts.getText();
        String text4 = btnAbout.getText();
        String text5 = btnNewCustomer.getText();
        String text6 = btnClearContent.getText();
        
        // Passar el texte a MAJÚSCULES
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
     * @author Txell Llanas - Creació
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
     * @author Txell Llanas - Creació
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
     * @author Txell Llanas - Creació
     */
    @FXML
    private void goToAbout() {
    }

    @FXML
    private void goToNewClient() throws IOException {
        App.setRoot("clientsForm");
    }

    /**
     * Mètode que neteja el camp de texte del cercador (clear).
     * @param event Acció que afecti al 'btnCleanContent' (ex: clicar)
     */
    @FXML
    private void clearContent(ActionEvent event) {
        inputSearchCustomer.clear();
    }
    
    /**
     * Mètode que recupera tots els registres de la taula 'customers'.
     * @author Txell Llanas
     */
    private void fillCustomersTable(){
        
        try {
            
            // Instància del ClientDAO per carregar els registres de la taula 'customers'
            ClientDAO data = new ClientDAO();

            // Afegir els registres existents a la taula dins la llista
            llistaObservableClient.addAll(data.getAll());
        
            // Establir vincle entre els atributs de l'objecte Client i cada columna
            // de la taula per mostrar les dades recuperades dins el tableview:
            columnCustomerEmail.setCellValueFactory(new PropertyValueFactory<>("customerEmail"));
            columnCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            columnIdCard.setCellValueFactory(new PropertyValueFactory<>("idCard"));
            columnBirthDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
            columnPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
            columnCreditLimit.setCellValueFactory(new PropertyValueFactory<>("creditLimit"));

            // Afegir els elements a la taula
            customersTableView.setItems(llistaObservableClient);
            
        } catch (SQLException ex) {
            Logger.getLogger(ClientsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    /**
     * Mètode que filtra els registres segons el texte introduït al cercador.
     * @author Txell Llanas
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
                    return false; // Contingut no trobat

            });

        });

        // Ordenar els resultats coincidents (descarta els registres que no coincideixen amb les paraules cercades)
        SortedList<Client> sortedData = new SortedList<>(filteredData);

        // Establir vincle de la SortedList amb la TableView
        sortedData.comparatorProperty().bind(customersTableView.comparatorProperty());

        // Aplicar filtratge a la taula
        customersTableView.setItems(sortedData);
    }

}
