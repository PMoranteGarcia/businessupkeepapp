/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entitats;

/**
 * Classe de productes per la tabla de comandes
 * @author pablomorante
 */
public class ProductesComanda {
    private int idProducte;
    private String nom;
    private int quantitat;
    private float unitaryPrice;
    private float total;
    private int numberLine;
    private int orderNumber;

    /***
     * Constructor buit de ProductesComanda
     */
    public ProductesComanda() {
    }
    
    /***
     * Constructor ple de ProductesComanda
     * @param idProducte int
     * @param nom String
     * @param quantitat int
     * @param unitaryPrice float
     * @param total float
     * @param numberLine int
     */
    public ProductesComanda(int idProducte, String nom, int quantitat, float unitaryPrice, float total, int numberLine) {
        this.idProducte = idProducte;
        this.nom = nom;
        
        this.quantitat = quantitat;
        this.unitaryPrice = unitaryPrice;
        this.total = total;
        this.numberLine = numberLine;
    }
    /***
     * setter de idProducte
     * @param idProducte int
     */
    public void setIdProducte(int idProducte) {
        this.idProducte = idProducte;
    }
    /**
     * setter de nom de producte
     * @param nom String
     */
    public void setNom(String nom) {
        this.nom = nom;
    }
    /***
     * setter de quantitat de producte
     * @param quantitat int
     */
    public void setQuantitat(int quantitat) {
        this.quantitat = quantitat;
    }
    /***
     * setter de preu unitari de producte
     * @param unitaryPrice float
     */
    public void setUnitaryPrice(float unitaryPrice) {
        this.unitaryPrice = unitaryPrice;
    }
    /***
     * setter de total preu del producte amb quantitat
     * @param total float
     */
    public void setTotal(float total) {
        this.total = total;
    }
    /***
     * setter del número de línia de la comanda
     * @param numberLine int
     */
    public void setNumberLine(int numberLine) {
        this.numberLine = numberLine;
    }
    /***
     * setter de la idComanda a la que pertany el producte
     * @param orderNumber int
     */
    public void setOrderNummber(int orderNumber) {
        this.orderNumber = orderNumber;
    }
    /***
     * getter id de producte
     * @return int
     */
    public int getIdProducte() {
        return idProducte;
    }
    /***
     * getter nom de producte
     * @return String
     */
    public String getNom() {
        return nom;
    }
    /***
     * getter de quantitat de producte
     * @return int
     */
    public int getQuantitat() {
        return quantitat;
    }
    /***
     * getter de preu unitari de producte
     * @return float
     */
    public float getUnitaryPrice() {
        return unitaryPrice;
    }
    /***
     * getter del total del producte amb quantitat
     * @return float
     */
    public float getTotal() {
        return total;
    }
    /***
     * getter del número de línia del producte
     * @return int
     */
    public int getNumberLine() {
        return numberLine;
    }
    /***
     * getter de id comanda que pertany el producte
     * @return int
     */
    public int getOrderNumber() {
        return orderNumber;
    }
}
