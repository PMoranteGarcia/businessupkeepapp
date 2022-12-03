package presentacio;

import entitats.Client;
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
import javafx.scene.control.TextArea;

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
    private Button btnOrders;
    @FXML
    private Button btnCustomers;
    @FXML
    private Button btnProducts;
    @FXML
    private Button btnAbout;
    @FXML
    private Button btnNewCustomer;
    @FXML
    private TableView<Client> ordersList;
    @FXML
    private TableColumn<?, ?> columnOrderNumber;
    @FXML
    private TableColumn<?, ?> columnRequiredDate;
    @FXML
    private TableColumn<?, ?> columnShippedDate;
    @FXML
    private TableColumn<?, ?> columnCustomerEmail;
    @FXML
    private TableColumn<?, ?> columnCustomerEmail1;
    @FXML
    private TableColumn<?, ?> columnActions;
    @FXML
    private TextArea textArea;
    @FXML
    private Button btnSearchCustomer;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }
    
    @FXML
    private void goToCustomers() {
    }

    @FXML
    private void goToProducts() throws IOException {
        App.setRoot("productes");

    }

    @FXML
    private void goToAbout() {
    }

    @FXML
    private void goToOrders() throws IOException {
        App.setRoot("comandes");
    }

    @FXML
    private void goToNewClient() throws IOException {
        App.setRoot("clientsForm");
    }

    @FXML
    private void searchCustomer() {
    }

}
