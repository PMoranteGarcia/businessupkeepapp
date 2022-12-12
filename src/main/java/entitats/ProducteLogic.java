package entitats;

import dades.ComandaDAO;
import dades.ProducteDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TextField;

/**
 * Aquí anirà la lògica de negoci de productes, amb les comprovacions pertinents
 *
 * @author Pablo Morante - Creació
 * @author Victor García - Creació
 * @author Izan Jimenez - Creació/Implementació
 */
public class ProducteLogic {

//    private AppConfigDAO dataDefaults;
    private ProducteDAO dataProduct;
    private ComandaDAO dataOrder;
    private final List<Comanda> ordersList = new ArrayList<>();

    public ProducteLogic() {
    }

    /**
     * Mètode per comprovar que el producte no existeix en cap comanda.
     *
     * @param p Instància de la classe Client
     * @return boolean (True/False) si es troben coincidències o no amb la BD
     * @author Txell Llanas - Implementació
     */
    public int productIsInOrders(Producte p) {

        int productCode = p.getProductCode();
        int numComandes = 0;
//
//        try {
//
//            dataProduct = new ProducteDAO();
//            ordersList.addAll(dataOrder.getAll());
//
//            for (int i = 0; i < ordersList.size(); i++) {
//
//                if (productCode.equals(ordersList.get(i).getCustomers_customerEmail())) {
//                    numComandes++;
//                }
//
//            }
//
//        } catch (SQLException ex) {
//            Logger.getLogger(ClientLogic.class.getName()).log(Level.SEVERE, null, ex);
//        }

        return numComandes;
    }

    public Float getDefaultStock() {

        Float credit = 1.0F;

//        try {
//            
//            dataDefaults = new AppConfigDAO();
//            valuesList.addAll(dataDefaults.getAll());
//                
//                credit = valuesList.get(0).getDefaultCreditLimit();
//           
//            
//        } catch (SQLException ex) {
//            Logger.getLogger(ClientLogic.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
        return credit;

    }
    
    
     public boolean checkStock(TextField f) {
        
        boolean ret = false;
//        Float userValue = Float.parseFloat(f.getText());
//        Float maxValue = valuesList.get(0).getDefaultCreditLimit();
//        System.out.println("userValue: " + userValue);
//        System.out.println("maxValue: " + maxValue);
//
//        if( (userValue > 0) && (userValue <= maxValue) )
//            ret = true;
        
        return ret;
    }
    
}
