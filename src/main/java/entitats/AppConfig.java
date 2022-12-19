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
    private int maxLinesPerOrdes;

    /**
     * *CONSTRUCTOR AppConfig
     */
    public AppConfig() {

    }

    /**
     * *maxLInesPerOrdes
     *
     * @return maxLInesPerOrdes
     */
    public int getMaxLinesPerOrdes() {
        return maxLinesPerOrdes;
    }

    /**
     * *
     * maxLinesPerOrdes
     *
     * @param maxLinesPerOrdes maxLinesPerOrdes
     */
    public void setMaxLinesPerOrdes(int maxLinesPerOrdes) {
        this.maxLinesPerOrdes = maxLinesPerOrdes;
    }

    /**
     * *
     * defaultCreditLimit
     *
     * @return defaultCreditLimit
     */
    public float getDefaultCreditLimit() {
        return defaultCreditLimit;
    }

    /**
     * *
     * defaultCreditLimit
     *
     * @return defaultCreditLimit
     */
    public List<Comanda> getComandesList() {
        return comandesList;
    }

    /**
     * *
     * maxOrderAmount
     *
     * @return maxOrderAmount
     */
    public float getMaxOrderAmount() {
        return maxOrderAmount;
    }

    /**
     * *
     * maxOrderAmount
     *
     * @param maxOrderAmount maxOrderAmount
     */
    public void setMaxOrderAmount(float maxOrderAmount) {
        this.maxOrderAmount = maxOrderAmount;
    }

    /**
     * *
     * comandesList
     *
     * @param comandesList comandesList
     */
    public void setComandesList(List<Comanda> comandesList) {
        this.comandesList = comandesList;
    }

    /**
     * *
     * defaultCreditLimit
     *
     * @param defaultCreditLimit defaultCreditLimit
     */
    public void setDefaultCreditLimit(float defaultCreditLimit) {
        this.defaultCreditLimit = defaultCreditLimit;
    }

    /**
     * *minCustomerAge
     *
     * @return minCustomerAge
     */
    public int getMinCustomerAge() {
        return minCustomerAge;
    }

    /**
     * *
     * minCustomerAge
     *
     * @param minCustomerAge minCustomerAge
     */
    public void setMinCustomerAge(int minCustomerAge) {
        this.minCustomerAge = minCustomerAge;
    }

    /**
     * *
     * defaultStock
     *
     * @return defaultStock
     */
    public int getDefaultStock() {
        return defaultStock;
    }

    /**
     * setDefaultStock
     *
     * @param defaultStock setDefaultStock
     */
    public void setDefaultStock(int defaultStock) {
        this.defaultStock = defaultStock;
    }

    /**
     * minShippingHours
     *
     * @return minShippingHours
     */
    public int getMinShippingHours() {
        return minShippingHours;
    }

    /**
     * *
     * minShippingHours
     *
     * @param minShippingHours minShippingHours
     */
    public void setMinShippingHours(int minShippingHours) {
        this.minShippingHours = minShippingHours;
    }

    /**
     * defaultQuantityOrdered
     *
     * @return defaultQuantityOrdered
     */
    public int getDefaultQuantityOrdered() {
        return defaultQuantityOrdered;
    }

    /**
     * *
     * defaultQuantityOrdered
     *
     * @param defaultQuantityOrdered defaultQuantityOrdered
     */
    public void setDefaultQuantityOrdered(int defaultQuantityOrdered) {
        this.defaultQuantityOrdered = defaultQuantityOrdered;
    }

    /**
     * getDefaultProductBenefit
     *
     * @return getDefaultProductBenefit
     */
    public int getDefaultProductBenefit() {
        return defaultProductBenefit;
    }

    /**
     * getDefaultProductBenefit
     *
     * @param defaultProductBenefit getDefaultProductBenefit
     */
    public void setDefaultProductBenefit(int defaultProductBenefit) {
        this.defaultProductBenefit = defaultProductBenefit;
    }

}
