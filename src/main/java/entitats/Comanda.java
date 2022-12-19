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
    
    /***
     * Constructor comanda buit
     * 
     */
    public Comanda () {
        
    }
    /***
     * Constructor de comanda amb només id
     * @param numOrdre int
     */
    public Comanda(int numOrdre) {
        this.numOrdre = numOrdre;
    }
    
    /***
     * Constructor de comanda amb id i dataEntrega
     * @param numOrdre int
     * @param dataEntrega Timestamp
     */
    public Comanda(int numOrdre, Timestamp dataEntrega) {
        this.numOrdre = numOrdre;
        this.dataEntrega = dataEntrega;
    }

    /***
     * Constructor de comanda amb id, dataEntrega i client
     * @param dataOrdre int
     * @param dataEntrega Timestamp
     * @param customers_customerEmail String
     */
    public Comanda(Timestamp dataOrdre, Timestamp dataEntrega, String customers_customerEmail) {
        this.dataOrdre = dataOrdre;
        this.dataEntrega = dataEntrega;
        this.customers_customerEmail = customers_customerEmail;
    }
    
    /***
     * Constructor de comanda sencer
     * @param numOrdre int
     * @param dataOrdre Timestamp
     * @param dataEntrega Timestamp
     * @param dataEnviament Timestamp
     * @param client Client
     * @param total float
     * @param productes List
     */
    public Comanda(int numOrdre, Timestamp dataOrdre, Timestamp dataEntrega, Timestamp dataEnviament, Client client, float total, List<ProductesComanda> productes) {
        this.numOrdre = numOrdre;
        this.dataOrdre = dataOrdre;
        this.dataEntrega = dataEntrega;
        this.dataEnviament = dataEnviament;
        this.client = client;
        this.total = total;
        this.productes = productes;
    }
    /***
     * getter de idComanda
     * @return int
     */
    public int getNumOrdre() {
        return numOrdre;
    }
    
    /***
     * getter data ordre de comanda
     * @return Timestamp
     */
    public Timestamp getDataOrdre() {
        return dataOrdre;
    }
    /***
     * getter data entrega de comanda
     * @return Timestamp
     */
    public Timestamp getDataEntrega() {
        return dataEntrega;
    }
    /***
     * getter data enviament de comanda
     * @return Timestamp
     */
    public Timestamp getDataEnviament() {
        return dataEnviament;
    }
    /***
     * getter de client de comanda
     * @return Client
     */
    public Client getClient() {
        return client;
    }
    /***
     * getter total de comanda
     * @return float
     */
    public float getTotal() {
        return total;
    }
    /***
     * getter llista de productes de comanda
     * @return List
     */
    public List<ProductesComanda> getProductes() {
        return productes;
    }
    /***
     * getter d'email del client de la comanda
     * @return String
     */
    public String getCustomers_customerEmail() {
        return customers_customerEmail;
    }
    /***
     * setter idComanda
     * @param numOrdre int
     */
    public void setNumOrdre(int numOrdre) {
        this.numOrdre = numOrdre;
    }
    /***
     * setter data ordre de comanda
     * @param dataOrdre Timestamp
     */
    public void setDataOrdre(Timestamp dataOrdre) {
        this.dataOrdre = dataOrdre;
    }
    /***
     * setter data entrega de comanda
     * @param dataEntrega Timestamp
     */
    public void setDataEntrega(Timestamp dataEntrega) {
        this.dataEntrega = dataEntrega;
    }
    /***
     * setter data enviament comanda
     * @param dataEnviament Timestamp
     */
    public void setDataEnviament(Timestamp dataEnviament) {
        this.dataEnviament = dataEnviament;
    }
    /***
     * setter client de la comanda
     * @param client Client 
     */
    public void setClient(Client client) {
        this.client = client;
    }
    /***
     * setter total preu de la comanda
     * @param total float
     */
    public void setTotal(float total) {
        this.total = total;
    }
    /***
     * setter llista de productes de comanda
     * @param productes List
     */
    public void setProductes(List<ProductesComanda> productes) {
        this.productes = productes;
    }
    /***
     * setter email de client de comanda
     * @param customers_customerEmail String
     */
    public void setCustomers_customerEmail(String customers_customerEmail) {
        this.customers_customerEmail = customers_customerEmail;
    }
    
    
}