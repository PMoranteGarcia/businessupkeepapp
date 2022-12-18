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
    private float maxOrderAmount;
    private int minShippingHours;
    private int defaultQuantityOrdered;
    private int defaultProductBenefit;
    private List<Comanda> comandesList;

    public AppConfig() {

    }

    public float getDefaultCreditLimit() {
        return defaultCreditLimit;
    }

    public List<Comanda> getComandesList() {
        return comandesList;
    }

    public float getMaxOrderAmount() {
        return maxOrderAmount;
    }

    public void setMaxOrderAmount(float maxOrderAmount) {
        this.maxOrderAmount = maxOrderAmount;
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

    public int getMinShippingHours() {
        return minShippingHours;
    }

    public void setMinShippingHours(int minShippingHours) {
        this.minShippingHours = minShippingHours;
    }

    public int getDefaultQuantityOrdered() {
        return defaultQuantityOrdered;
    }

    public void setDefaultQuantityOrdered(int defaultQuantityOrdered) {
        this.defaultQuantityOrdered = defaultQuantityOrdered;
    }

    public int getDefaultProductBenefit() {
        return defaultProductBenefit;
    }

    public void setDefaultProductBenefit(int defaultProductBenefit) {
        this.defaultProductBenefit = defaultProductBenefit;
    }
    
    

}
