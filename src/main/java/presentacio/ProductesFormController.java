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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

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
    private TextArea textAreaDescripcio;
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
        textFieldNom.setPrefWidth(Screen.getPrimary().getBounds().getWidth());
        textAreaDescripcio.setPrefHeight(Screen.getPrimary().getBounds().getHeight());
        textAreaDescripcio.setPrefWidth(Screen.getPrimary().getBounds().getWidth());

    }

    /**
     * *Torna a la vista de cercar productes
     *
     * @throws IOException
     */
    @FXML
    private void goToProducts() throws IOException {
        App.setRoot("productes");
    }

    /**
     * *Guarda a la BBDD el producte a la taula productes amb les dades
     * introduides
     *
     * @throws IOException
     */
    @FXML
    private void saveProducte() throws IOException {
        App.setRoot("productes");

    }

    /**
     * *Cancela la creacio del nou producte i torna a la vista de productes
     *
     * @throws IOException
     */
    @FXML
    private void cancelProducte() throws IOException {
        App.setRoot("productes");

    }

}
