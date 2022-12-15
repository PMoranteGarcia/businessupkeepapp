package entitats;

import java.sql.Date;
import java.util.List;

/**
 * Aquí anirà la classe/entitat de comandes
 *
 * @author Pablo Morante - Creació/Implementació
 * @author Victor García - Creació/Implementació
 */
public class Comanda {
    private int numOrdre;           // PK de comanda
    private Date dataOrdre;
    private Date dataEntrega;
    private Date dataEnviament;
    private Client client;
    private float total;
    private List<ProductesComanda> productes;
    private String customers_customerEmail;
    
    public Comanda () {
        
    }

    //TEMPORAL NOVA COMANDA
    public Comanda(Date dataOrdre, Date dataEntrega, String customers_customerEmail) {
        this.dataOrdre = dataOrdre;
        this.dataEntrega = dataEntrega;
        this.customers_customerEmail = customers_customerEmail;
    }
    
    public Comanda(int numOrdre, Date dataOrdre, Date dataEntrega, Date dataEnviament, Client client, float total, List<ProductesComanda> productes) {
        this.numOrdre = numOrdre;
        this.dataOrdre = dataOrdre;
        this.dataEntrega = dataEntrega;
        this.dataEnviament = dataEnviament;
        this.client = client;
        this.total = total;
        this.productes = productes;
    }

    public int getNumOrdre() {
        return numOrdre;
    }
    
    public Date getDataOrdre() {
        return dataOrdre;
    }

    public Date getDataEntrega() {
        return dataEntrega;
    }

    public Date getDataEnviament() {
        return dataEnviament;
    }

    public Client getClient() {
        return client;
    }

    public float getTotal() {
        return total;
    }

    public List<ProductesComanda> getProductes() {
        return productes;
    }
    
    public String getCustomers_customerEmail() {
        return customers_customerEmail;
    }

    public void setNumOrdre(int numOrdre) {
        this.numOrdre = numOrdre;
    }
    
    public void setDataOrdre(Date dataEntrega) {
        this.dataOrdre = dataOrdre;
    }

    public void setDataEntrega(Date dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public void setDataEnviament(Date dataEnviament) {
        this.dataEnviament = dataEnviament;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public void setProductes(List<ProductesComanda> productes) {
        this.productes = productes;
    }
    
    public void setCustomers_customerEmail(String customers_customerEmail) {
        this.customers_customerEmail = customers_customerEmail;
    }
    
    
}