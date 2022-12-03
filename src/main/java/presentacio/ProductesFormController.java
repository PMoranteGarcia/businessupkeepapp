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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Izan
 */
public class ProductesFormController implements Initializable {

    @FXML
    private Button btnBack;
    @FXML
    private Label orderNumber;
    @FXML
    private TextField textFieldNom;
    @FXML
    private TextField textFieldCodi;
    @FXML
    private TextField textFieldPreu;
    @FXML
    private TextField textFieldStock;
    @FXML
    private TextField textFieldDescripcio;
    @FXML
    private Button btnDesarProducte;
    @FXML
    private Button btnCancelProducte;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void goToProducts(ActionEvent event) throws IOException {
        App.setRoot("productes");
    }

    @FXML
    private void saveProducte(ActionEvent event) throws IOException {
        App.setRoot("productes");

    }

    @FXML
    private void cancelProducte(ActionEvent event) throws IOException {
        App.setRoot("productes");

    }

}
