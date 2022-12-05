package presentacio;

import entitats.Client;
import java.io.IOException;
import java.net.URL;
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

/**
 * Controlador de la vista 'comandesForm.fxml'.
 * Permet a l'usuari gestionar el CRUD d'una línia de comanda des d'una UI.
 * 
 * @author Txell Llanas  - Creació vista FXML
 * @author Pablo Morante - Creació/Implementació
 * @author Victor García - Creació/Implementació
 */
public class ComandesFormController implements Initializable {

    @FXML
    private Button btnBack;
    @FXML
    private Label orderNumber;
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
    private ComboBox<Client> selectorClient;
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
        
        // Passar el texte a MAJÚSCULES
        btnSave.setText(text1.toUpperCase());
        btnCancel.setText(text2.toUpperCase());
        btnaddProduct.setText(text3.toUpperCase());
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
