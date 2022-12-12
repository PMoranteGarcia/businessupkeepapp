package presentacio;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

/**
 * Classe que regula els controladors
 *
 * @author Pablo Morante - Creació
 * @author Victor García - Creació
 */
public abstract class PresentationLayer {
    
    private entitats.ClientLogic clientLogic;
    entitats.ComandaLogic comandaLogic;
    entitats.ProducteLogic producteLogic;
    
    /***
     * Mostra un missatge d'error
     * @param txt 
     */
    public void alertError(String txt)
    {
          Alert a = new Alert(Alert.AlertType.ERROR, "", ButtonType.OK);
          a.setTitle("ERROR: ");
          a.setContentText(txt);
          
          //refresquem vista
          //App.getStage().setScene(scene);
          
          //mostrem error
          a.show();
    }
    
    public void alertInfo(String txt)
    {
          Alert a = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
          a.setTitle("AVÍS: ");
          a.setContentText(txt);
          
          //refresquem vista
          //App.getStage().setScene(scene);
          
          //mostrem error
          a.show();
    }
    
    public void alertConfirmacio(String txt)
    {
          Alert a = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
          a.setTitle("CONFIRMA UNA OPCIÓ: ");
          a.setContentText(txt);
          
          //refresquem vista
          //App.getStage().setScene(scene);
          
          //mostrem error
          a.show();
    }
    
}

