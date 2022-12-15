/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package presentacio;

import entitats.ProducteLogic;
import javafx.scene.control.TextArea;
import javafx.stage.Screen;

import dades.ProducteDAO;
import entitats.Producte;
import entitats.ProducteLogic;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.util.ResourceBundle;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

/**
 * FXML Controller class
 *
 * @author Izan
 */
public class ProductesFormController implements Initializable {

    @FXML
    private Button btnBack, btnDesarProducte, btnCancelProducte;
    @FXML
    private Label orderNumber;

    @FXML
    private Label lblPreu, lblStock, lblNom, lblDescripcio;

    @FXML
    private TextField textFieldNom, textFieldPreu, textFieldStock;
    @FXML
    private TextArea textAreaDescripcio;

    private ProducteLogic validate = new ProducteLogic();                       // Instància del ProducteLogic per carregar els mètodes de validacions    
    List<String> errors = new ArrayList<>();                                    // Llistat per recollir errors de validació del formulari    
    String defaultStock = Integer.toString(validate.getDefaultStock());

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        textFieldNom.setPrefWidth(Screen.getPrimary().getBounds().getWidth());
        textAreaDescripcio.setPrefHeight(Screen.getPrimary().getBounds().getHeight());
        textAreaDescripcio.setPrefWidth(Screen.getPrimary().getBounds().getWidth());

        System.out.println("DefaultStock = " + defaultStock);
        textFieldStock.setText(defaultStock); // Inserir valor per defecte

    }

    /**
     * *Torna a la vista de cercar productes
     *
     * @throws IOException
     */
    @FXML
    private void goToProducts() throws IOException {
        cancel();
    }

    /**
     * *Guarda a la BBDD el producte a la taula productes amb les dades
     * introduides
     *
     * @throws IOException
     */
    @FXML
    private void saveProducte() throws IOException, SQLException {
        validacions();
        System.out.println(errors.isEmpty());

        if (!errors.isEmpty()) {

            System.out.println(">> Hi ha errors al formulari!");

            String errorLines = "";
            for (int i = 0; i < errors.size(); i++) // Llistar els errors provinents de les validacions             
            {
                errorLines += errors.get(i) + "\n";
            }

            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("AVÍS IMPORTANT");
            alert.setHeaderText("Hi ha camps buits o errors. Revisi'ls si us plau");
            alert.setContentText(errorLines);
            alert.show();                                                       // Mostrar errors

        } else {

            System.out.println(">> Producte validat");
            Producte c = new Producte(textFieldNom.getText(),
                    textAreaDescripcio.getText(),
                    Integer.parseInt(textFieldStock.getText()),
                    Float.parseFloat(textFieldPreu.getText()));

            ProducteDAO dadesProducte = new ProducteDAO();                      // Si tot ok, crear nou producte amb les dades introduïdes al formulari
            dadesProducte.save(c);
            System.out.println(">> Producte desat satisfactòriament!");

            App.setRoot("productes");                                           // Redirigir a l'usuari al llistat de productes
        }
    }

    /**
     * *Cancela la creacio del nou producte i torna a la vista de productes
     *
     * @throws IOException
     * @author Izan Jimenez - Implementació
     */
    @FXML
    private void cancelProducte() throws IOException {
        cancel();

    }

    /**
     * Mètode que mostra una alerta per confirmar que es volen descartar els
     * canvis realitzats al formulari actual.
     *
     * @throws IOException Mostra un error si no troba la vista FXML cap a on es
     * dirigeix
     * @author Izan Jimenez - Implementació
     */
    @FXML
    private void cancel() throws IOException {

        Alert alert = new Alert(AlertType.CONFIRMATION);                    // Mostrar alerta per confirmar si cancel·lar el procés d'alta
        alert.setTitle("CONFIRMI UNA OPCIÓ");
        alert.setHeaderText("Desitja descartar l'alta actual?");

        ButtonType yesButton = new ButtonType("Descartar");
        ButtonType cancelButton = new ButtonType("Seguir Editant");

        alert.getButtonTypes().setAll(yesButton, cancelButton);

        if (alert.showAndWait().get() == yesButton) {
            App.setRoot("productes");                                         // Redirigir a l'usuari al llistat de productes
        } else {
            alert.close();
        }

    }

    /**
     * Mètode per validar el formulari d'alta d'un nou producte. Validacions: -
     * Que no hi hagi camps buits - Que no existeixi un producte a la BBDD - 
     * Que el preu sigui un numero positiu
     *
     * @author Izan JImenez - Implementació
     */
    public void validacions() {

        errors.clear();                                                         // Buidar llistat d'errors

        textFieldNom.getStyleClass().remove("filled");                          // Netejar estils (camps requerits en vermell)
        lblNom.getStyleClass().remove("filled");
        textFieldNom.applyCss();
        lblNom.applyCss();
//        
        textAreaDescripcio.getStyleClass().remove("filled");
        lblDescripcio.getStyleClass().remove("filled");
        textAreaDescripcio.applyCss();
        lblDescripcio.applyCss();
//        
        textFieldStock.getStyleClass().remove("filled");
        lblStock.getStyleClass().remove("filled");
        textFieldStock.applyCss();
        lblStock.applyCss();
//        
        textFieldPreu.getStyleClass().remove("filled");
        lblPreu.getStyleClass().remove("filled");
        textFieldPreu.applyCss();
        lblPreu.applyCss();

        // VALIDACIONS: Detectar camps buits, usuari no duplicat, edat mínima i crèdit màxim
        if (textFieldNom.getText().isEmpty() || textFieldNom.getText().isBlank()) {
            errors.add("El camp 'Nom' és obligatori.");
            textFieldNom.getStyleClass().add("required");
            lblNom.getStyleClass().add("required");
        } else {
            if (!validate.checkProductExists(textFieldNom)) {
                textFieldNom.getStyleClass().add("filled");
                lblNom.getStyleClass().add("filled");
            } else {
                errors.add("ERROR: No es pot donar d'alta el producte: ja existeix un producte amb aquest email.");
                textFieldNom.getStyleClass().add("required");
                lblNom.getStyleClass().add("required");
            }
        }

        if (textAreaDescripcio.getText().isEmpty() || textAreaDescripcio.getText().isBlank()) {
            textAreaDescripcio.setText("");
        }
        textAreaDescripcio.getStyleClass().add("filled");
        lblDescripcio.getStyleClass().add("filled");

        if (textFieldPreu.getText().isEmpty() || textFieldPreu.getText().isBlank()) {
            errors.add("El camp 'Preu' és obligatori.");
            textFieldPreu.getStyleClass().add("required");
            lblPreu.getStyleClass().add("required");
        } else {
            System.out.println(textFieldPreu.getText());
            if (validate.checkPreu(textFieldPreu)) {
                textFieldPreu.getStyleClass().add("filled");
                lblPreu.getStyleClass().add("filled");
            } else {
                errors.add("ERROR: No es pot donar d'alta el producte: El preu ha de ser un numero positiu");
                textFieldPreu.getStyleClass().add("required");
                lblPreu.getStyleClass().add("required");
            }
        }

        if (textFieldStock.getText().isEmpty() || textFieldStock.getText().isBlank()) {
            errors.add("El camp 'Stock' és obligatori.");
            textFieldStock.getStyleClass().add("required");
            lblStock.getStyleClass().add("required");
        } else {
            if (validate.checkStock(textFieldStock)) {
                textFieldStock.getStyleClass().add("filled");
                lblStock.getStyleClass().add("filled");
            } else {
                errors.add("ERROR: No es pot donar d'alta el producte: La quantitat minima d'stock ha de ser 1.");
                textFieldStock.getStyleClass().add("required");
                lblStock.getStyleClass().add("required");
            }
        }
        System.out.println("errors:" + errors);
    }

}
