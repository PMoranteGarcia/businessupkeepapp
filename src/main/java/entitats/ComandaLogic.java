package entitats;

import dades.AppConfigDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Aquí anirà la lògica de negoci de les comandes, amb les comprovacions
 * pertinents
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
     * (RF48) Mètode per recuperar el valor per defecte del 'cost màxim' d'una
     * comanda indicat a la taula 'appConfig' de la BD.
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
    /***
     * (RF42) Mètode per recuperar el valor per defecte de les hores mínimes que han de pasar
     * per poder fer l'ordre de la comanda
     * @return int amb les hores
     * @author Pablo Morante - Creació/Implementació
     */
    public int getMinShippingHours() {
        int hores = 0;
        try {

            dataDefaults = new AppConfigDAO();
            valuesList.addAll(dataDefaults.getAll());

            hores = valuesList.get(0).getMinShippingHours();

        } catch (SQLException ex) {
            Logger.getLogger(ClientLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hores;
    }
    /***
     * (RF36) Mètode per recuperar el valor per defecte de la quantitat que es posarà
     * a l'afegir un producte a una comanda
     * @return int quantitat del producte
     * @author Pablo Morante - Creació/Implementació
     */
    public int getDefaultQuantityOrdered() {
        int quantity = 0;
        try {

            dataDefaults = new AppConfigDAO();
            valuesList.addAll(dataDefaults.getAll());

            quantity = valuesList.get(0).getDefaultQuantityOrdered();

        } catch (SQLException ex) {
            Logger.getLogger(ClientLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
        return quantity;
    }
    /***
     * (RF38) Mètode per recuperar el benefici extra de cada producte per defecte
     * @return int percentatge extra
     * @author Pablo Morante - Creació/Implementació
     */
    public int getDefaultProductBenefit() {
        int DBenefit = 0;
        try {

            dataDefaults = new AppConfigDAO();
            valuesList.addAll(dataDefaults.getAll());

            DBenefit = valuesList.get(0).getDefaultProductBenefit();

        } catch (SQLException ex) {
            Logger.getLogger(ClientLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
        return DBenefit;
    }
    
    /***
     * (RF46) Mètode per recuperar el valor per defecte del màxim de productes que poden
     * haver-hi en una comanda
     * @return int número màxim de productes
     * @author Izan Jimenez
     */
    public int getmaxLinesPerOrder(){
        int max = 0;
        try {

            dataDefaults = new AppConfigDAO();
            valuesList.addAll(dataDefaults.getAll());

            max = valuesList.get(0).getMaxLinesPerOrdes();

        } catch (SQLException ex) {
            Logger.getLogger(ClientLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
        return max;
    }
}
