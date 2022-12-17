package entitats;

import java.sql.Timestamp;
import java.util.List;

/**
 * Aquí anirà la classe/entitat de comandes
 *
 * @author Pablo Morante - Creació/Implementació
 * @author Victor García - Creació/Implementació
 */
public class Comanda {
    private int numOrdre;           // PK de comanda
    private Timestamp dataOrdre;
    private Timestamp dataEntrega;
    private Timestamp dataEnviament;
    private Client client;
    private float total;
    private List<ProductesComanda> productes;
    private String customers_customerEmail;
    
    public Comanda () {
        
    }
    
    public Comanda(int numOrdre) {
        this.numOrdre = numOrdre;
    }

    //TEMPORAL NOVA COMANDA
    public Comanda(Timestamp dataOrdre, Timestamp dataEntrega, String customers_customerEmail) {
        this.dataOrdre = dataOrdre;
        this.dataEntrega = dataEntrega;
        this.customers_customerEmail = customers_customerEmail;
    }
    
    public Comanda(int numOrdre, Timestamp dataOrdre, Timestamp dataEntrega, Timestamp dataEnviament, Client client, float total, List<ProductesComanda> productes) {
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
    
    public Timestamp getDataOrdre() {
        return dataOrdre;
    }

    public Timestamp getDataEntrega() {
        return dataEntrega;
    }

    public Timestamp getDataEnviament() {
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
    
    public void setDataOrdre(Timestamp dataEntrega) {
        this.dataOrdre = dataOrdre;
    }

    public void setDataEntrega(Timestamp dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public void setDataEnviament(Timestamp dataEnviament) {
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