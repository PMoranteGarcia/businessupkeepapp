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
     * @param p Instància de la classe Porducte
     * @return boolean (True/False) si es troben coincidències o no amb la BD
     * @author Izan Jimenez - Implementació
     */
    public boolean productIsInOrders(Producte p) {  

        int productCode = p.getProductCode();
        boolean existeix = false;

        try {

            dataProduct = new ProducteDAO();
            dataOrder = new ComandaDAO();

            ordersList.addAll(dataOrder.getAll());
            System.out.println("OrderListSize "+ordersList.size());
            for (int i = 0; i <= ordersList.size()-1; i++) {
                System.out.println("Dentro de oderlistSize");
                System.out.println("OrderList GET i "+ordersList.get(i));
                
                //aa
                        
                List<ProductesComanda> llistProductesInComanda = ordersList.get(i).getProductes();

                for (ProductesComanda productesComanda : llistProductesInComanda) {
                       System.out.println("ProductesComandaGetComanda "+productesComanda.getIdProducte());
                    if (productCode == productesComanda.getIdProducte()) {
                        System.out.println("LO ESSSS");
                        return true;
                    }
                }
            }

        } catch (SQLException ex) {
        }

        return existeix;
    }

    /**
     * Mètode per retornar la quantitat minima d'stock (RF32).
     *
     * @author Izan Jimenez - Implementació
     */
    public int getDefaultStock() {

        int stock = 0;
        try {
            dataDefaults = new AppConfigDAO();
            valuesList.addAll(dataDefaults.getAll());
            stock = valuesList.get(0).getDefaultStock();
        } catch (SQLException ex) {
            Logger.getLogger(ClientLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
        return stock;
    }

    /**
     * *
     * Métode per comprovar si el parametre introduit es major al minim Stock
     *
     * @param f Camp de texte del formulari
     * @return boolean (True/False) si el valor es valid
     * @author Izan Jimenez - Implementació
     */
    public boolean checkStock(TextField f) {
        boolean ret = false;
        try {

            int stock = Integer.parseInt(f.getText());
            int minStock = getDefaultStock();

            if (stock >= minStock) {
                ret = true;
            }
        } catch (NumberFormatException e) {
        }
        return ret;
    }

    /**
     * Mètode per verificar que el prodcute no existeixi ja a la BD (nom).
     *
     * @param f Camp de texte del formulari
     * @return boolean (True/False) si es troben coincidències o no amb la BD
     * @author Izan Jimenez - Implementació
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
        }

        if (count > 0) {
            res = true;
        }

        return res;
    }

    /**
     * Mètode per verificar que el preu sigui valid.
     *
     * @param f Camp de texte del formulari
     * @return boolean (True/False) si es valid
     * @author Izan Jimenez - Implementació
     */
    public boolean checkPreu(TextField f) {
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
