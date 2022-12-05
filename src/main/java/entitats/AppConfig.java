package entitats;

import java.util.List;
import presentacio.PresentationLayer;


/**
 * Aquí anirà la classe/entitat pel manager (appconfig), connexió entre controladors
 *
 * @author Pablo Morante - Creació
 * @author Victor García - Creació
 */
public class AppConfig {
    
    private static AppConfig single_instance = null;
    private final List<PresentationLayer> controllers;

    public AppConfig(List<PresentationLayer> controllers) {
        this.controllers = controllers;
    }
    
    
}
