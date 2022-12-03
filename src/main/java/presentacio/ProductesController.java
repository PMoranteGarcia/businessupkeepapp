package presentacio;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

/**
 * Controlador de la vista 'productes.fxml'. Permet a l'usuari gestionar el CRUD
 * dels productes des d'una UI.
 *
 * @author Pablo Morante - Creació
 * @author Victor García - Creació
 * @author Izan Jimenez - Creació vista FXML/Implementació
 */
public class ProductesController {

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
    private TextArea textArea;
    @FXML
    private Button btnSearchCustomer;
    @FXML
    private TableView<?> ordersList;
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
    private TableColumn<?, ?> columnCustomerEmail11;

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
    private void goToAbout(ActionEvent event) {
    }

    @FXML
    private void searchCustomer(ActionEvent event) {
    }

    @FXML
    private void goToNewProduct(ActionEvent event) throws IOException {
        App.setRoot("productesForm");
    }

}
