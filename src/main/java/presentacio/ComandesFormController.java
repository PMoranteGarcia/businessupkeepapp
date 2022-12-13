package presentacio;

import dades.ClientDAO;
import dades.ComandaDAO;
import entitats.Client;
import entitats.ComandaLogic;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.StringConverter;

/**
 * Controlador de la vista 'comandesForm.fxml'.
 * Permet a l'usuari gestionar el CRUD d'una línia de comanda des d'una UI.
 * 
 * @author Txell Llanas  - Creació vista FXML
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
    private TableColumn columnActions;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnCancel;
    @FXML
    private TableView orderLinesList;
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
    private ComboBox<?> selectorProduct;
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

    /**
     * Initializes the controller class.
     * 
     * @author Txell Llanas - Creació/Implementació
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Obtenir el texte dels botons
        String text1 = btnSave.getText();
        String text2 = btnCancel.getText();
        String text3 = btnaddProduct.getText();
        // get all clients
        try {
            ClientDAO DAO = new ClientDAO();
            ObservableList<Client> ol = (FXCollections.observableList(DAO.getAll()));
            selectorClient.setItems(ol);
            selectorClient.setConverter(new StringConverter<Client>() {
              @Override
              public String toString(Client client) {
                if (client== null){
                  return null;
                } else {
                  return client.getCustomerName();
                }
              }

            @Override
            public Client fromString(String id) {
                return null;
            }
        });
        } catch (SQLException ex) {
            this.alertInfo(ex.toString());
        }
        
        // Passar el texte a MAJÚSCULES
        btnSave.setText(text1.toUpperCase());
        btnCancel.setText(text2.toUpperCase());
        btnaddProduct.setText(text3.toUpperCase());
        orderNumber.setText(Integer.toString(ComandesController.getIdComanda()));
        
        this.idComanda = Integer.parseInt(orderNumber.getText()); // obtenir id comanda actual
        
        // get all products (ProducteLogic)
        
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
        
        // Carregar la vista del llistat "COMANDES (Llistat)" in-situ
        App.setRoot("comandes");
    }    

    /**
     * Mètode per desar una comanda.
     * 
     * @throws IOException Excepció a mostrar en cas que no es trobi el Layout
     * @author Txell Llanas - Creació
     */
    @FXML
    private void saveOrder() throws IOException {
         App.setRoot("comandes");
    }

    /**
     * Mètode per descartar una comanda. 
     * - Si és de nova creació, no la desa i redirigeix a l'usuari al llistat de 
     * comandes.
     * - Si s'està editant, no s'actualitzen els canvis i es redirigeix a l'usuari 
     * al llistat de comandes.
     * Abans de redirigir a l'usuari, mostra un avís a l'usuari perquè confirmi 
     * si realment vol descartar la comanda actual.
     * 
     * @throws IOException Excepció a mostrar en cas que no es trobi el Layout
     * @author Txell Llanas - Creació
     */
    @FXML
    private void cancelOrder() throws IOException {
         App.setRoot("comandes");
    }

    /**
     * Mètode per afegir un producte al llistat.
     * 
     * @author Txell Llanas - Creació
     */
    @FXML
    private void addProduct() {
    }
    
}