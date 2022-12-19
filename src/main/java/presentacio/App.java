package presentacio;

import entitats.AppConfigLogic;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Classe que defineix l'objecte Aplicació. Permet configurar la finestra de
 * l'aplicació i què i com ho mostrarà.
 *
 * @author Pablo Morante - Creació/Implementació
 * @author Victor García - Creació/Implementació
 * @author Txell Llanas - Creació/Implementació
 */
public class App extends Application {

    private static Scene scene;
    
    // Instància del ClientLogic per carregar els mètodes de validacions
    private final AppConfigLogic validate = new AppConfigLogic();

    public static void main(String[] args) {
        App.starter(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException, SQLException {

        //(RF30) Comprovem que existeix almenys un registre a la taula appConfig
        validate.appConfigValidation();

        try {

            // Carregar vista principal "COMANDES (Llistat)"
            Parent p = FXMLLoader.load(this.getClass().getClassLoader().getResource("presentacio/comandes.fxml"));

            scene = new Scene(p);

            // Fulla d'estils CSS
            scene.getStylesheets().add("/css/styles.css");

            // Propietats de la finestra
            primaryStage.setScene(scene);
            primaryStage.setTitle("Manteniment empresa");
            primaryStage.setMinWidth(1000);
            primaryStage.setWidth(1000);
            primaryStage.setMinHeight(600);
            primaryStage.show();
        } catch (IOException ex) {
            System.out.println("No s'ha pogut carregat la interfície d'usuari.");
            System.out.println(ex.toString());
        }
    }

    /**
     * Mètode per carregar una vista *.fxml dins un Stage definit com a
     * principal (Root).
     *
     * @param fxml Layout *.fxml a visualitzar dins l'Stage
     * @throws IOException Excepció a mostrar en cas que no es trobi el Layout
     * @author Txell Llanas - Creació/Implementació
     */
    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    /**
     * Mètode per carregar una vista *.fxml dins un Stage.
     *
     * @param fxml Ruta al layout *.fxml
     *
     * @return Parent que conté el layout *.fxml a visualitzar dins l'Stage
     * @throws IOException Excepció a mostrar en cas que no es trobi el Layout
     * @author Txell Llanas - Creació/Implementació
     */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    /**
     * Executa l'aplicació.
     *
     * @param args
     * @author Txell Llanas - Creació/Implementació
     */
    public static void starter(String[] args) {
        launch();
    }
}
