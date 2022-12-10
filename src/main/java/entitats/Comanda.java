package entitats;

import java.sql.Date;

/**
 * Aquí anirà la classe/entitat de comandes
 *
 * @author Pablo Morante - Creació/Implementació
 * @author Victor García - Creació/Implementació
 */
public class Comanda {
    
    private int orderNumber;
    private Date orderDate, requiredDate, shippedDate;
    private String customers_customerEmail;

    public Comanda() {
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getRequiredDate() {
        return requiredDate;
    }

    public void setRequiredDate(Date requiredDate) {
        this.requiredDate = requiredDate;
    }

    public Date getShippedDate() {
        return shippedDate;
    }

    public void setShippedDate(Date shippedDate) {
        this.shippedDate = shippedDate;
    }

    public String getCustomers_customerEmail() {
        return customers_customerEmail;
    }

    public void setCustomers_customerEmail(String customers_customerEmail) {
        this.customers_customerEmail = customers_customerEmail;
    }
    
    
}
