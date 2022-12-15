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

    public ProductesComanda() {
    }
    

    public ProductesComanda(int idProducte, String nom, int quantitat, float unitaryPrice, float total, int numberLine) {
        this.idProducte = idProducte;
        this.nom = nom;
        
        this.quantitat = quantitat;
        this.unitaryPrice = unitaryPrice;
        this.total = total;
        this.numberLine = numberLine;
    }

    public void setIdProducte(int idProducte) {
        this.idProducte = idProducte;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setQuantitat(int quantitat) {
        this.quantitat = quantitat;
    }

    public void setUnitaryPrice(float unitaryPrice) {
        this.unitaryPrice = unitaryPrice;
    }

    public void setTotal(float total) {
        this.total = total;
    }
    
    public void setNumberLine(int numberLine) {
        this.numberLine = numberLine;
    }
    
    public void setOrderNummber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getIdProducte() {
        return idProducte;
    }

    public String getNom() {
        return nom;
    }

    public int getQuantitat() {
        return quantitat;
    }

    public float getUnitaryPrice() {
        return unitaryPrice;
    }

    public float getTotal() {
        return total;
    }
    
    public int getNumberLine() {
        return numberLine;
    }
    
    public int getOrderNumber() {
        return orderNumber;
    }
}
