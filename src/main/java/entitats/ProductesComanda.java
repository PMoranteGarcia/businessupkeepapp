/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entitats;

/**
 *
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

    /**
     * *
     * Constructor ProductesComanda
     *
     */
    public ProductesComanda() {
    }

    /**
     * *
     * Constructor ProductesComanda
     *
     * @param idProducte idProducte
     * @param nom nom
     * @param quantitat quantitat
     * @param unitaryPrice unitaryPrice
     * @param total total
     * @param numberLine numberLine
     */
    public ProductesComanda(int idProducte, String nom, int quantitat, float unitaryPrice, float total, int numberLine) {
        this.idProducte = idProducte;
        this.nom = nom;

        this.quantitat = quantitat;
        this.unitaryPrice = unitaryPrice;
        this.total = total;
        this.numberLine = numberLine;
    }

    /**
     * *setIdProducte
     *
     * @param idProducte idProducte
     */
    public void setIdProducte(int idProducte) {
        this.idProducte = idProducte;
    }

    /**
     * setNom
     *
     * @param nom nom
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * setQuantitat
     *
     * @param quantitat quantitat
     */
    public void setQuantitat(int quantitat) {
        this.quantitat = quantitat;
    }

    /**
     * *
     * setUnitaryPrice
     *
     * @param unitaryPrice unitaryPrice
     */
    public void setUnitaryPrice(float unitaryPrice) {
        this.unitaryPrice = unitaryPrice;
    }

    /**
     * *
     * setTotal
     *
     * @param total total
     */
    public void setTotal(float total) {
        this.total = total;
    }

    /**
     * *
     * setNumberLine
     *
     * @param numberLine numberLine
     */
    public void setNumberLine(int numberLine) {
        this.numberLine = numberLine;
    }

    /**
     * *
     * setOrderNummber
     *
     * @param orderNumber orderNumber
     */
    public void setOrderNummber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    /**
     * *
     * getIdProducte
     *
     * @return idProducte
     */
    public int getIdProducte() {
        return idProducte;
    }

    /**
     * *
     * getNom
     *
     * @return nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * *
     * getQuantitat
     *
     * @return quantitat
     */
    public int getQuantitat() {
        return quantitat;
    }

    /**
     * *
     * getUnitaryPrice
     *
     * @return unitaryPrice
     */
    public float getUnitaryPrice() {
        return unitaryPrice;
    }

    /**
     * *
     * getTotal
     *
     * @return total
     */
    public float getTotal() {
        return total;
    }

    /**
     * *
     * getNumberLine
     *
     * @return numberLine
     */
    public int getNumberLine() {
        return numberLine;
    }

    /**
     * *
     * getOrderNumber
     *
     * @return orderNumber
     */
    public int getOrderNumber() {
        return orderNumber;
    }
}
