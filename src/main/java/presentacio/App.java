package presentacio;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * @author GrupD
 */
public class App extends Application {
    private static Scene scene;
    
    public static void main(String[] args) {
        App.starter(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        try {
            String fxml = "presentacio/primary";
           
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getClassLoader().getResource(fxml + ".fxml"));
            
            Parent p = fxmlLoader.load();
            
            scene = new Scene(p);
            // falta carregar full css
            
            primaryStage.setScene(scene);
            primaryStage.setTitle("Mantenimient empresa");
            primaryStage.setMinWidth(700);
            primaryStage.setMinHeight(500);
            primaryStage.show();
        } catch (IOException ex) {
            System.out.println("No s'ha pogut carregat la interfície d'usuari.");
            System.out.println(ex.toString());
        }
    }
    
    public static void starter(String[] args) {
        launch();
    }
}
