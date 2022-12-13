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

    public Producte(String productName, String productDescription, int quantityInStock, float buyPrice) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.quantityInStock = quantityInStock;
        this.buyPrice = buyPrice;
    }
   

    public int getProductCode() {
        return productCode;
    }

    public void setProductCode(int productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public float getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(float buyPrice) {
        this.buyPrice = buyPrice;
    }

    @Override
    public String toString() {
        return "Producte{" + "productCode=" + productCode + ", productName=" + productName + ", productDescription=" + productDescription + ", quantityInStock=" + quantityInStock + ", buyPrice=" + buyPrice + '}';
    }
}
