package entitats;

import dades.AppConfigDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Aquí anirà la lògica de negoci de les comandes, amb les comprovacions pertinents
 *
 * @author Pablo Morante - Creació/Implementació
 * @author Victor García - Creació/Implementació
 */
public class ComandaLogic {
    
    private AppConfigDAO dataDefaults;                                          // Instància d'AppConfigDAO per carregar els registres de la taula 'appConfig'
    private final List<AppConfig> valuesList = new ArrayList<>();               // Llistat per desar valors per defecte (Regles de negoci, taula: appConfig)
    
    public ComandaLogic() {
        super();
    }
    
    
    /**
     * (RF48) Mètode per recuperar el valor per defecte del 'cost màxim' d'una comanda
     * indicat a la taula 'appConfig' de la BD.
     * 
     * @return Float amb el valor del cost màxim permès a una comanda
     * @author Víctor García - Creació/Implementació
     */
    public Float getMaxOrderAmount() {
        
        Float total = 0.0F;
        
        try {
            
            dataDefaults = new AppConfigDAO();
            valuesList.addAll(dataDefaults.getAll());
                
            total = valuesList.get(0).getMaxOrderAmount();
           
            
        } catch (SQLException ex) {
            Logger.getLogger(ClientLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return total;
        
    }
}
