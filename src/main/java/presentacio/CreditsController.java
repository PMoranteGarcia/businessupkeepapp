package presentacio;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * Controlador de la vista 'credits.fxml'. 
 * Permet a l'usuari visualitzar informació sobre l'aplicació.
 * 
 * @author Txell Llanas - Creació vista FXML / Implementació
 */
public class CreditsController {

    @FXML
    private Button btnOrders, btnCustomers, btnProducts, btnAbout;


    /**
     * Mostra l'apartat 'Comandes' i al llistat que conté tots els registres de 
     * la BD.
     * 
     * @throws IOException Excepció a mostrar en cas que no es trobi el Layout
     * @author Txell Llanas - Creació/Implementació
     */
    @FXML
    private void goToOrders() throws IOException {
        App.setRoot("comandes");
    }

    /**
     * Mostra l'apartat 'Clients' i al llistat que conté tots els registres de 
     * la BD.
     * 
     * @throws IOException Excepció a mostrar en cas que no es trobi el Layout
     * @author Txell Llanas - Creació/Implementació
     */
    @FXML
    private void gotoCustomers(ActionEvent event) throws IOException {
        App.setRoot("clients");
    }

    /**
     * Mostra l'apartat 'Productes' i al llistat que conté tots els registres de 
     * la BD.
     * 
     * @throws IOException Excepció a mostrar en cas que no es trobi el Layout
     * @author Txell Llanas - Creació/Implementació
     */
    @FXML
    private void goToProducts() throws IOException {
        App.setRoot("productes");

    }
    
}
