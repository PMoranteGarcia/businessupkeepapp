package presentacio;

import dades.ClientDAO;
import entitats.Client;
import entitats.ClientLogic;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.util.ResourceBundle;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.util.StringConverter;

/**
 * Controlador de la vista 'clientsForm.fxml'. 
 * Permet a l'usuari crear un Client des d'una UI.
 *
 * @author Izan
 * @author Txell Llanas - Implementació
 */
public class ClientsFormController implements Initializable {

    @FXML
    private Button btnBack, btnSave, btnCancel;
    @FXML
    private Label label_customerEmail, label_customerName, label_idCard, label_phone, label_birthDate, label_creditLimit, label_euro;
    @FXML
    private TextField field_customerName, field_customerEmail, field_idCard, field_phone, field_creditLimit;
    @FXML
    private DatePicker  field_birthDate;
    
    
    private final ClientLogic validate = new ClientLogic();                     // Instància del ClientLogic per carregar els mètodes de validacions    
    List<String> errors = new ArrayList<>();                                    // Llistat per recollir errors de validació del formulari    
    String defaultCredit = Float.toString(validate.getDefaultCreditLimit());    // String amb el valor per defecte pel Crèdit per assignar a un client
    
    /**
     * Inicialitza el controlador.
     * 
     * @param url The location used to resolve relative paths for the root
     * object, or null if the location is not known.
     * @param rb The resources used to localize the root object, or null if the
     * root object was not localized.
     * @author Txell Llanas - Creació / Implementació 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // Inserir valor per defecte del crèdit màxim d'un client al formulari de creació
        field_creditLimit.setText(defaultCredit);
        
        // Definir format:(dia/mes/any) a mostrar quan s'edita a dins el camp del calendari
        String datePattern = "dd/MM/yyyy";                                      // Format per aplicar a la Data
        field_birthDate.setPromptText("dd/mm/aaaa");                            // Texte que es mostra al camp Data                    
        field_birthDate.setConverter(new StringConverter<LocalDate>() {
            
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(datePattern);

            @Override 
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);                          // Aplico format a la data
                } else {
                    return "";
                }
            }

            @Override 
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);              // Aplico format a la data
                } else {
                    return null;
                }
            }
        });
    }

    /**
     * Mètode que redirigeix a l'usuari cap al llistat de clients.
     * 
     * @throws IOException Mostra un error si no troba la vista FXML cap a on es dirigeix
     * @author Txell Llanas - Creació / Implementació
     */
    @FXML
    private void goToCustomers() throws IOException {        
        cancelClient();                                                         // Confirmar que realment es desitja abandonar el formulari
    }

    /**
     * Mètode que permet iniciar el procés d'alta d'ún client.
     * - Primer valida les dades introduïdes al formulari d'alta.
     * - Si tot és correcte, crea el client.
     * 
     * @throws IOException Mostra un error si no troba la vista FXML cap a on es dirigeix
     * @throws SQLException Mostra un error si no pot connectar amb la BD 
     */
    @FXML
    private void saveClient() throws IOException, SQLException {
               
        validacions();                                                          // Validar camps formulari        
        if( !errors.isEmpty() ){

            String errorLines = "";
            for(int i = 0; i < errors.size(); i++)                              // Llistar els errors provinents de les validacions             
                errorLines += errors.get(i) + "\n";

            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("AVÍS IMPORTANT");
            alert.setHeaderText("Hi ha camps buits o errors. Revisi'ls si us plau");
            alert.setContentText(errorLines);
            alert.show();                                                       // Mostrar errors
            
        } else {
            
            Client c = new Client(field_customerEmail.getText().toLowerCase(),
                                  field_idCard.getText().toUpperCase(), 
                                  field_customerName.getText(), 
                                  field_phone.getText(), 
                                  Float.parseFloat(field_creditLimit.getText()), 
                                  Date.valueOf(field_birthDate.getValue()));        
        
            ClientDAO dadesClient = new ClientDAO();                            // Si tot ok, crear nou client amb les dades introduïdes al formulari
            dadesClient.save(c);

            App.setRoot("clients");                                             // Redirigir a l'usuari al llistat de clients
        }
        
    }

    /**
     * Mètode que mostra una alerta per confirmar que es volen descartar els 
     * canvis realitzats al formulari actual.
     * 
     * @throws IOException Mostra un error si no troba la vista FXML cap a on es dirigeix
     * @author Txell Llanas - Creació / Implementació
     */
    @FXML
    private void cancelClient() throws IOException {
        
            Alert alert = new Alert(AlertType.CONFIRMATION);                    // Mostrar alerta per confirmar si cancel·lar el procés d'alta
            alert.setTitle("CONFIRMI UNA OPCIÓ");
            alert.setHeaderText("Desitja descartar l'alta actual?");

            ButtonType yesButton = new ButtonType("Descartar");
            ButtonType cancelButton = new ButtonType("Seguir Editant");

            alert.getButtonTypes().setAll(yesButton, cancelButton);
            
            if( alert.showAndWait().get() == yesButton )
                App.setRoot("clients");                                         // Redirigir a l'usuari al llistat de clients
            else
                alert.close();

    }

    /**
     * Mètode per validar el formulari d'alta d'un nou client.
     * Validacions:
     * - Que no hi hagi camps buits
     * - Que el client no sigui un menor (RF44)
     * - Que no se sobrepassi el crèdit màxim preestablert a la BD (RF34)
     * - Que no existeixi un usuari amb el mateix email i dni (dades úniques)
     * 
     * @author Txell Llanas - Creació / Implementació
     */
    public void validacions() {

        errors.clear();                                                         // Buidar llistat d'errors
        
        field_customerName.getStyleClass().remove("filled");                    // Netejar estils (camps requerits en vermell)
        label_customerName.getStyleClass().remove("filled");
        field_customerName.applyCss();
        label_customerName.applyCss();
        
        field_customerEmail.getStyleClass().remove("filled");
        label_customerEmail.getStyleClass().remove("filled");
        field_customerEmail.applyCss();
        label_customerEmail.applyCss();
        
        field_idCard.getStyleClass().remove("filled");
        label_idCard.getStyleClass().remove("filled");
        field_idCard.applyCss();
        label_idCard.applyCss();
        
        field_phone.getStyleClass().remove("filled");
        label_phone.getStyleClass().remove("filled");
        field_phone.applyCss();
        label_phone.applyCss();
        
        field_birthDate.getStyleClass().remove("filled");
        label_birthDate.getStyleClass().remove("filled");
        field_birthDate.applyCss();
        label_birthDate.applyCss();
        
        field_creditLimit.getStyleClass().remove("filled");
        label_creditLimit.getStyleClass().remove("filled");
        label_euro.getStyleClass().remove("filled");
        field_creditLimit.applyCss();
        label_creditLimit.applyCss();
        label_euro.applyCss();        
            
        // VALIDACIONS: Detectar camps buits, usuari no duplicat, edat mínima i crèdit màxim
        if( field_customerName.getText().isEmpty() || field_customerName.getText().isBlank() ) {
            errors.add("El camp 'Nom' és obligatori.");
            field_customerName.getStyleClass().add("required");
            label_customerName.getStyleClass().add("required");            
        } else {
            field_customerName.getStyleClass().add("filled");
            label_customerName.getStyleClass().add("filled");
        }
        
        
        if( field_customerEmail.getText().isEmpty() || field_customerEmail.getText().isBlank() ) {            
            errors.add("El camp 'Email' és obligatori.");
            field_customerEmail.getStyleClass().add("required");
            label_customerEmail.getStyleClass().add("required");
        } else {
            
            if ( !validate.checkUserMailExists(field_customerEmail) ) {
                field_customerEmail.getStyleClass().add("filled");
                label_customerEmail.getStyleClass().add("filled");
            } else {
                errors.add("ERROR: No es pot donar d'alta l'usuari: ja existeix un usuari amb aquest email.");
                field_customerEmail.getStyleClass().add("required");
                label_customerEmail.getStyleClass().add("required");
            }               
            
        }
        
        
        if( field_idCard.getText().isEmpty() || field_idCard.getText().isBlank() ) {
            errors.add("El camp 'Dni' és obligatori.");
            field_idCard.getStyleClass().add("required");
            label_idCard.getStyleClass().add("required");            
        } else {
            
            if ( !validate.checkUserIdCardExists(field_idCard) ) {      
                field_idCard.getStyleClass().add("filled");
                label_idCard.getStyleClass().add("filled");
            } else {
                errors.add("ERROR: No es pot donar d'alta l'usuari: ja existeix un usuari amb aquest dni.");
                field_idCard.getStyleClass().add("required");
                label_idCard.getStyleClass().add("required"); 
            }
        }
        
        
        if( field_phone.getText().isEmpty() || field_phone.getText().isBlank() ) {
            errors.add("El camp 'Telèfon' és obligatori.");            
            field_phone.getStyleClass().add("required");
            label_phone.getStyleClass().add("required");
            
        } else {            
            field_phone.getStyleClass().addAll("filled");
            label_phone.getStyleClass().add("filled");           
        }
        
        
        if( field_birthDate.getValue() == null || field_birthDate.getValue().toString().isBlank() ) {
            errors.add("El camp 'Aniversari' és obligatori.");
            field_birthDate.getStyleClass().add("required");
            label_birthDate.getStyleClass().add("required");
        } else {
            
            if( validate.checkMinCustomerAge(field_birthDate) ) {
                field_birthDate.getStyleClass().add("filled");
                label_birthDate.getStyleClass().add("filled");
            } else {
                errors.add("ERROR 'Aniversari': No es pot donar d'alta a un usuari que és menor d'edat!");
                field_birthDate.getStyleClass().add("required");
                label_birthDate.getStyleClass().add("required");
            }
        }
        
        
        if( field_creditLimit.getText().isEmpty() || field_creditLimit.getText().isBlank() ) {
            errors.add("El camp 'Crèdit Màxim' és obligatori.");
            field_creditLimit.getStyleClass().addAll("required");
            label_creditLimit.getStyleClass().add("required");
            label_euro.getStyleClass().add("required");          
        } else {
            
            if( validate.checkCreditLimit(field_creditLimit) ) {
                field_creditLimit.getStyleClass().add("filled");
                label_creditLimit.getStyleClass().add("filled"); 
                label_euro.getStyleClass().add("filled");
            } else {
                errors.add("ERROR: 'Crèdit Màxim' incorrecte, indicar un valor entre 0 i "+ defaultCredit + " (valors decimals: \".\")");
                field_creditLimit.getStyleClass().addAll("required");
                label_creditLimit.getStyleClass().add("required");
                label_euro.getStyleClass().add("required");
            }            
        } 
    }
}
