package entitats;

import dades.AppConfigDAO;
import dades.ClientDAO;
import dades.ComandaDAO;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 * Lògica de negoci de clients amb les comprovacions pertinents
 * 
 * @author Pablo Morante - Creació
 * @author Victor García - Creació
 * @author Txell Llanas - Implementació (RF34 i RF44)
 */
public class ClientLogic {
    
    private AppConfigDAO dataDefaults;                                          // Instància d'AppConfigDAO per carregar els registres de la taula 'appConfig'
    private ClientDAO dataClient;                                               // Instància de ClientDAO per carregar els registres de la taula 'customers'
    private ComandaDAO dataOrder;                                               // Instància de ComandaDAO per carregar els registres de la taula 'orders'
    
    private final List<Client> clientsList = new ArrayList<>();                 // Llistat amb clients (taula: customers)
    private final List<Comanda> ordersList = new ArrayList<>();                 // Llistat amb comandes (taula: orders)
    private final List<AppConfig> valuesList = new ArrayList<>();               // Llistat per desar valors per defecte (Regles de negoci, taula: appConfig)
    
    public ClientLogic() {
    }
    
    /**
     * Mètode per recuperar el valor per defecte de 'limitació de crèdit' d'un client
     * indicat a la taula 'appConfig' de la BD.
     * 
     * @return Float amb el valor del crèdit màxim permès a un client
     * @author Txell Llanas - Implementació
     */
    public Float getDefaultCreditLimit() {
        
        Float credit = 0.0F;
        
        try {
            
            dataDefaults = new AppConfigDAO();
            valuesList.addAll(dataDefaults.getAll());
                
            credit = valuesList.get(0).getDefaultCreditLimit();
           
            
        } catch (SQLException ex) {
            System.out.println("Error gestionant la connexió a MySQL !!!");
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            Logger.getLogger(ClientLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return credit;
        
    }
    
    /**
     * Mètode per recuperar el valor per defecte de l'edat mínima per donar 
     * d'alta un client indicat a la taula 'appConfig' de la BD.
     * 
     * @return int amb el valor de l'edat mínima per poder donar d'alta a un client
     * @author Txell Llanas - Implementació
     */
    public int getDefaultMinCustomerAge() {
        
        int edat = 0;
        
        try {
            
            dataDefaults = new AppConfigDAO();
            valuesList.addAll(dataDefaults.getAll());
            
            edat = valuesList.get(0).getMinCustomerAge();
            
        } catch (SQLException ex) {
            System.out.println("Error gestionant la connexió a MySQL !!!");
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            Logger.getLogger(ClientLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return edat;
        
    }
    
    /**
     * Mètode per verificar que l'usuari no existeixi ja a la BD (email).
     * 
     * @param f Camp de texte del formulari on s'hi indica el mail del client
     * @return boolean (True/False) si es troben coincidències o no amb la BD
     * @author Txell Llanas - Implementació
     */
    public boolean checkUserMailExists(TextField f) {
        
        boolean res = false;
        String mail = f.getText().toLowerCase().trim();
        int count = 0;
        
        try {
            
            dataClient = new ClientDAO();
            clientsList.addAll(dataClient.getAll());
            
            for(int i = 0; i < clientsList.size(); i++){
                
                if(mail.equals(clientsList.get(i).getCustomerEmail()))
                    count++;
                
            }            
            
        } catch (SQLException ex) {
            System.out.println("Error gestionant la connexió a MySQL !!!");
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            Logger.getLogger(ClientLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if( count > 0 )
            res = true;
        
        return res;
    }
    
    /**
     * Mètode per verificar que l'usuari no existeixi ja a la BD (Dni).
     * 
     * @param f Camp de texte del formulari on s'hi indica el dni del client
     * @return boolean (True/False) si es troben coincidències o no amb la BD
     * @author Txell Llanas - Implementació
     */
    public boolean checkUserIdCardExists(TextField f) {
        
        boolean res = false;
        String idCard = f.getText().toUpperCase().trim();
        int count = 0;
        
        try {
            
            dataClient = new ClientDAO();
            clientsList.addAll(dataClient.getAll());
            
            for(int i = 0; i < clientsList.size(); i++){
                
                if(idCard.equals(clientsList.get(i).getIdCard()))
                    count++;
                
            }            
            
        } catch (SQLException ex) {
            System.out.println("Error gestionant la connexió a MySQL !!!");
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            Logger.getLogger(ClientLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if( count > 0 )
            res = true;
        
        return res;
    }
    
    /**
     * Mètode per comprovar si  el client té alguna comanda en curs.
     * 
     * @param c Instància de la classe Client
     * @return boolean (True/False) si es troben coincidències o no amb la BD
     * @author Txell Llanas - Implementació
     */
    public int clientHasOrders(Client c){
        
        String mail = c.getCustomerEmail();
        int numComandes = 0;
        
        try {
            
            dataOrder = new ComandaDAO();
            ordersList.addAll(dataOrder.getAll());
            
            for(int i = 0; i < ordersList.size(); i++){
                
                if(mail.equals(ordersList.get(i).getCustomers_customerEmail()))
                    numComandes++;
                
            }            
            
        } catch (SQLException ex) {
            System.out.println("Error gestionant la connexió a MySQL !!!");
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            Logger.getLogger(ClientLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return numComandes;
    }
    
    /**
     * Mètode per comprovar que el crèdit especificat per un client estigui dins 
     * dels paràmetres per defecte especificats a la taula 'appConfig' (RF34).
     * 
     * @param f Camp de texte del formulari on s'hi indica el crèdit màxim assignat al client
     * @return boolean (True/False) si el valor passat es troba dins els límits permesos
     * @author Txell Llanas - Implementació
     */
    public boolean checkCreditLimit(TextField f) {
        
        boolean ret = false;
        
        try {
            
            Float userValue = Float.parseFloat(f.getText());
            Float maxValue = valuesList.get(0).getDefaultCreditLimit();

            if( (userValue > 0) && (userValue <= maxValue) )
                ret = true;
            
        } catch (NumberFormatException e) {
            System.out.println("Les dades introduïdes no són numèriques!!!");
            System.out.println("NumberFormatException: " + e.getMessage());
            Logger.getLogger(ClientLogic.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return ret;
    }
    
    /**
     * Mètode per comprovar que l'edat d'un client estigui dins dels paràmetres 
     * per defecte especificats a la taula 'appConfig' (RF44).
     * 
     * @param d Camp de tipus DatePicker del formulari on s'hi indica la data de naixement assignada al client
     * @return boolean (True/False) si el valor passat es troba dins els límits permesos
     */
    public boolean checkMinCustomerAge(DatePicker d) {

        LocalDate birthday = d.getValue();                             
        LocalDate today = LocalDate.now();
        Period period = Period.between(birthday, today);
            
        return (period.getYears() >= valuesList.get(0).getMinCustomerAge());
    }
}
