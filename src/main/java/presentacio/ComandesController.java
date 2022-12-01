package presentacio;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Controlador de la vista 'comandes.fxml'.
 * Permet a l'usuari gestionar el CRUD d'una capçalera de comanda des d'una UI.
 * 
 * @author Txell Llanas  - Creació vista FXML/Implementació
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
    private TableView ordersList;
    @FXML
    private TableColumn columnOrderNumber;
    @FXML
    private TableColumn columnRequiredDate;
    @FXML
    private TableColumn columnShippedDate;
    @FXML
    private TableColumn columnCustomerEmail;
    @FXML
    private TableColumn columnActions;
    @FXML
    private TableColumn columnCustomerEmail1;

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
        
//      VBox content = FXMLLoader.load(this.getClass().getClassLoader().getResource("presentacio/comandesForm.fxml"));
//      rootPane.getChildren().setAll(content);
    }

    /**
     * Mostra l'apartat 'Clients' i al llistat que conté tots els registres de 
     * la BD.
     * 
     * @throws IOException Excepció a mostrar en cas que no es trobi el Layout
     * @author Txell Llanas - Creació
     */
    @FXML
    private void goToCustomers() throws IOException {
        //App.setRoot("customers");  <-- descomentar quan ho editeu
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
         //App.setRoot("products");  <-- descomentar quan ho editeu
    }

    /**
     * Mostra l'apartat 'Crèdits'.
     * Indica la versió de l'aplicació i els seus desenvolupadors.
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
