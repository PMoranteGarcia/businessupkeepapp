/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package presentacio;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Izan
 */
public class ClientsFormController implements Initializable {

    @FXML
    private Button btnBack;
    @FXML
    private Label orderNumber;
    @FXML
    private TextField textFieldEmail;
    @FXML
    private TextField textFieldCard;
    @FXML
    private TextField textFieldCardLimit;
    @FXML
    private TextField textFieldNom;
    @FXML
    private TextField textFieldTelefon;
    @FXML
    private TextField textFieldBirth;
    @FXML
    private Button btnDesarClient;
    @FXML
    private Button btnCancelClient;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void goToCustomers() throws IOException {
        App.setRoot("clients");
    }

    @FXML
    private void saveClient() throws IOException {
        App.setRoot("clients");

    }

    @FXML
    private void cancelClient() throws IOException {
        App.setRoot("clients");

    }

}
