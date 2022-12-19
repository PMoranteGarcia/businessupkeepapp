package entitats;

/**
 * Aquí anirà la classe/entitat de productes
 *
 * @author Pablo Morante - Creació
 * @author Victor García - Creació
 * @author Izan Jimenez - Creació/Implementació
 */
public class Producte {

    private int productCode;
    private String productName;
    private String productDescription;
    private int quantityInStock;
    private float buyPrice;

    /**
     * *
     * Constructor instància Producte
     *
     * @param productName productName
     * @param productDescription productDescription
     * @param quantityInStock quantityInStock
     * @param buyPrice buyPrice
     */
    public Producte(String productName, String productDescription, int quantityInStock, float buyPrice) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.quantityInStock = quantityInStock;
        this.buyPrice = buyPrice;
    }

    /**
     * *
     * getProductCode
     *
     * @return productCode
     */
    public int getProductCode() {
        return productCode;
    }

    /**
     * *
     * setProductCode
     *
     * @param productCode productCode
     */
    public void setProductCode(int productCode) {
        this.productCode = productCode;
    }

    /**
     * *getProductName
     *
     * @return productName
     */
    public String getProductName() {
        return productName;
    }

    /**
     * *
     * setProductName
     *
     * @param productName productName
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * *
     * getProductDescription
     *
     * @return productDescription
     */
    public String getProductDescription() {
        return productDescription;
    }

    /**
     * *
     * setProductDescription
     *
     * @param productDescription productDescription
     */
    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    /**
     * *
     * getQuantityInStock
     *
     * @return quantityInStock
     */
    public int getQuantityInStock() {
        return quantityInStock;
    }

    /**
     * *
     * setQuantityInStock
     *
     * @param quantityInStock quantityInStock
     */
    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    /**
     * *
     * getBuyPrice
     *
     * @return buyPrice
     */
    public float getBuyPrice() {
        return buyPrice;
    }

    /**
     * setBuyPrice
     *
     * @param buyPrice buyPrice
     */
    public void setBuyPrice(float buyPrice) {
        this.buyPrice = buyPrice;
    }

    @Override
    public String toString() {
        return "Producte{" + "productCode=" + productCode + ", productName=" + productName + ", productDescription=" + productDescription + ", quantityInStock=" + quantityInStock + ", buyPrice=" + buyPrice + '}';
    }
}
