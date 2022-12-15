package entitats;

import dades.AppConfigDAO;
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

    private AppConfigDAO dataDefaults;
    private ProducteDAO dataProduct;
    private ComandaDAO dataOrder;
    private final List<Producte> productList = new ArrayList<>();                 // Llistat amb clients (taula: customers)
    private final List<Comanda> ordersList = new ArrayList<>();
    private final List<AppConfig> valuesList = new ArrayList<>();               // Llistat per desar valors per defecte (Regles de negoci, taula: appConfig)

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

    public int getDefaultStock() {

        int credit = 0;

        try {

            dataDefaults = new AppConfigDAO();
            valuesList.addAll(dataDefaults.getAll());

            credit = valuesList.get(0).getDefaultStock();

        } catch (SQLException ex) {
            Logger.getLogger(ClientLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
//        
        return credit;

    }

    public boolean checkStock(TextField f) {
        boolean ret = false;
        try {

            int stock = Integer.parseInt(f.getText());
            System.out.println("stock" + stock);
            int minStock = valuesList.get(0).getDefaultStock();
            System.out.println("stock: " + stock);
            System.out.println("minStock: " + minStock);

            if (stock >= minStock) {
                ret = true;
            }
        } catch (NumberFormatException e) {

        }

        return ret;
    }

    /**
     * Mètode per verificar que l'usuari no existeixi ja a la BD (email).
     *
     * @param f Camp de texte del formulari on s'hi indica el mail del client
     * @return boolean (True/False) si es troben coincidències o no amb la BD
     * @author Txell Llanas - Implementació
     */
    public boolean checkProductExists(TextField f) {

        boolean res = false;
        String nom = f.getText().trim();
        int count = 0;

        try {

            dataProduct = new ProducteDAO();
            productList.addAll(dataProduct.getAll());

            for (int i = 0; i < productList.size(); i++) {
                if (nom.equals(productList.get(i).getProductName())) {
                    count++;
                }

            }

        } catch (SQLException ex) {
            Logger.getLogger(ClientLogic.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (count > 0) {
            res = true;
        }
//        
        return res;
    }

    public boolean checkPreu(TextField f) {
        System.out.println(f.getText());
        boolean ret = false;
        try {

            float preu = Float.parseFloat(f.getText());

            if (preu > 0.0) {
                ret = true;
            }
        } catch (NumberFormatException e) {

        }

        return ret;
    }

}
