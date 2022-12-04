/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package presentacio;

import dades.ClientDAO;
import entitats.Client;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.sql.Date; 
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

/**
 * FXML Controller class
 *
 * @author Izan
 */
public class ClientsFormController implements Initializable {

    @FXML
    private Button btnBack, btnSave, btnCancel;   
    @FXML
    private Label customerId;
    @FXML
    private TextField field_customerName, field_customerEmail, field_idCard,
            field_phone, field_creditLimit;
    @FXML
    private DatePicker field_birthDate;

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
    private void saveClient() throws IOException, SQLException {
        
        // Creo nou client amb les dades introduïdes al formulari
        Client c = new Client(field_customerEmail.getText(), 
                              field_idCard.getText(), 
                              field_customerName.getText(), 
                              field_phone.getText(), 
                              Float.parseFloat(field_creditLimit.getText()), 
                              Date.valueOf(field_birthDate.getValue()));
        
        // Crido funció per desar el nou client a la BD
        ClientDAO dadesClient = new ClientDAO();
        dadesClient.save(c);
        
        // Redirigeixo a l'usuari al llistat de clients
        App.setRoot("clients");

    }

    /**
     * Mètode que mostra una alerta per confirmar que es volen descartar els 
     * canvis realitzats al formulari actual.
     * 
     * @throws IOException
     * @author Txell Llanas - Creació / Implementació
     */
    @FXML
    private void cancelClient() throws IOException {
        
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("CONFIRMI UNA OPCIÓ");
            alert.setHeaderText("Desitja descartar els canvis realitzats?");

            ButtonType yesButton = new ButtonType("Descartar");
            ButtonType cancelButton = new ButtonType("Seguir editant");

            alert.getButtonTypes().setAll(yesButton, cancelButton);
            
            if( alert.showAndWait().get() == yesButton )
                App.setRoot("clients");
            else
                alert.close();

    }

}
