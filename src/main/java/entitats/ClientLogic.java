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
 * Llògica de negoci de clients amb les comprovacions pertinents
 * 
 * @author Pablo Morante - Creació
 * @author Victor García - Creació
 * @author Txell Llanas - Implementació (RF34 i RF44)
 */
public class ClientLogic {

    private AppConfig values = new AppConfig();
    
    private AppConfigDAO dataDefaults;                                          // Instància d'AppConfigDAO per carregar els registres de la taula 'appConfig'
    private ClientDAO dataClient;
    private ComandaDAO dataOrder;
    
    private final List<Client> clientsList = new ArrayList<>();                 // Llistat amb clients (taula: customers)
    private final List<Comanda> ordersList = new ArrayList<>();                 // Llistat amb comandes (taula: orders)
    private final List<AppConfig> valuesList = new ArrayList<>();               // Llistat per desar valors per defecte (Regles de negoci, taula: appConfig)
    
    public ClientLogic() {
    }
    
    public Float getDefaultCreditLimit() {
        
        Float credit = 0.0F;
        
        try {
            
            dataDefaults = new AppConfigDAO();
            valuesList.addAll(dataDefaults.getAll());
                
                credit = valuesList.get(0).getDefaultCreditLimit();
           
            
        } catch (SQLException ex) {
            Logger.getLogger(ClientLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return credit;
        
    }
    
    public int getDefaultMinCustomerAge() {
        
        int edat = 0;
        
        try {
            
            dataDefaults = new AppConfigDAO();
            valuesList.addAll(dataDefaults.getAll());
            
            edat = valuesList.get(0).getMinCustomerAge();
            
        } catch (SQLException ex) {
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
        String mail = f.getText().trim();
        int count = 0;
        
        try {
            
            dataClient = new ClientDAO();
            clientsList.addAll(dataClient.getAll());
            
            for(int i = 0; i < clientsList.size(); i++){
                
                if(mail.equals(clientsList.get(i).getCustomerEmail()))
                    count++;
                
            }            
            
        } catch (SQLException ex) {
            Logger.getLogger(ClientLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if( count > 0 )
            res = true;
        
        return res;
    }
    
    /**
     * Mètode per verificar que l'usuari no existeixi ja a la BD (Dni).
     * 
     * @param f Camp de texte del formulari on s'hi indica el mail del client
     * @return boolean (True/False) si es troben coincidències o no amb la BD
     * @author Txell Llanas - Implementació
     */
    public boolean checkUserIdCardExists(TextField f) {
        
        boolean res = false;
        String idCard = f.getText().trim();
        int count = 0;
        
        try {
            
            dataClient = new ClientDAO();
            clientsList.addAll(dataClient.getAll());
            
            for(int i = 0; i < clientsList.size(); i++){
                
                if(idCard.equals(clientsList.get(i).getIdCard()))
                    count++;
                
            }            
            
        } catch (SQLException ex) {
            Logger.getLogger(ClientLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if( count > 0 )
            res = true;
        
        return res;
    }
    
    /**
     * Mètode per comprovar que el client no té cap comanda en curs.
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
            Logger.getLogger(ClientLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return numComandes;
    }
    
    public boolean checkCreditLimit(TextField f) {
        
        boolean ret = false;
        Float userValue = Float.parseFloat(f.getText());
        Float maxValue = valuesList.get(0).getDefaultCreditLimit();
        System.out.println("userValue: " + userValue);
        System.out.println("maxValue: " + maxValue);

        if( (userValue > 0) && (userValue <= maxValue) )
            ret = true;
        
        return ret;
    }
    
    public boolean checkMinCustomerAge(DatePicker d) {                          // Validar edat mínima  
        
        int minCustomerAge = valuesList.get(0).getMinCustomerAge();
        LocalDate birthday = d.getValue();                             
        LocalDate today = LocalDate.now();
        Period period = Period.between(birthday, today);
            
        System.out.println("És major d'edat? (mín. "+minCustomerAge+" anys) -> anys: "+period.getYears()+", "+ (period.getYears() >= minCustomerAge));
        return (period.getYears() >= valuesList.get(0).getMinCustomerAge());
    }
}
