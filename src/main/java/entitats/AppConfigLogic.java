package entitats;

import dades.AppConfigDAO;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * Regles de negoci d'AppConfig, amb les comprovacions pertinents
 *
 * @author Pablo Morante - Creació/Implementació
 * @author Victor García - Creació/Implementació
 */
public class AppConfigLogic {
    
    private AppConfigDAO appConfigVal;

    // Definir una llista observable d'objectes de tipus AppConfig
    private ObservableList<AppConfig> llistaObservableAppConfig = FXCollections.observableArrayList();
    
    /**
     * Mètode per comprovar que existeix almenys un registre a la taula
     * appConfig abans de carregar la primera vista, si no hi ha res,
     * directament no s'obre l'aplicació.(RF30).
     *
     * @author Víctor García - Creació/Implementació
     */
    public void appConfigValidation() throws SQLException {

        appConfigVal = new AppConfigDAO();
        try {
            llistaObservableAppConfig.addAll(appConfigVal.getAll());
            if (llistaObservableAppConfig.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);                 // Mostrar alerta per avisar que l'aplicació es tancarà
                        alert.setTitle("ERROR");
                        alert.setHeaderText("No hi ha cap regla de negoci a la base de dades. L'aplicació es tancarà.");

                        ButtonType okButton = new ButtonType("D'acord");

                        alert.getButtonTypes().setAll(okButton);
                        if (alert.showAndWait().get() == okButton) {
                            alert.close();
                            Platform.exit();
                            System.exit(0);
                        }
                
            }
        } catch (Exception ex) {
            Logger.getLogger(AppConfigDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
