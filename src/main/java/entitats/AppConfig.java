package entitats;

import dades.AppConfigDAO;
import java.util.List;

/**
 * Aquí anirà la classe/entitat pel manager (appconfig), connexió entre
 * controladors
 *
 * @author Pablo Morante - Creació
 * @author Victor García - Creació
 */
public class AppConfig {

    //private AppConfigDAO defaults;
    private float defaultCreditLimit;
    private int minCustomerAge;
    private int defaultStock;
    private List<Comanda> comandesList;

    public AppConfig() {

    }

    public float getDefaultCreditLimit() {
        return defaultCreditLimit;
    }

    public List<Comanda> getComandesList() {
        return comandesList;
    }

    public void setComandesList(List<Comanda> comandesList) {
        this.comandesList = comandesList;
    }

    public void setDefaultCreditLimit(float defaultCreditLimit) {
        this.defaultCreditLimit = defaultCreditLimit;
    }

    public int getMinCustomerAge() {
        return minCustomerAge;
    }

    public void setMinCustomerAge(int minCustomerAge) {
        this.minCustomerAge = minCustomerAge;
    }

    public int getDefaultStock() {
        return defaultStock;
    }

    public void setDefaultStock(int defaultStock) {
        this.defaultStock = defaultStock;
    }

}
