package entitats;

import java.sql.Date; 

/**
 * Classe que defineix l'objecte 'Client'.
 * Permet Definir les propietats i funcionalitats associades a un client.
 *
 * @author Pablo Morante - Creació
 * @author Victor García - Creació
 * @author Txell Llanas - Implementació
 */
public class Client {
    
    private String customerEmail, idCard, customerName, phone;
    private float creditLimit;
    private Date birthDate;

    public Client(){        
    }
    
    public Client(String customerEmail, String idCard, String customerName, String phone, float creditLimit, Date birthDate) {
        this.customerEmail = customerEmail;
        this.idCard = idCard;
        this.customerName = customerName;
        this.phone = phone;
        this.creditLimit = creditLimit;
        this.birthDate = birthDate;
    }
    
    public Client(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhone() {
        return phone.replaceAll(" ", "");
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public float getCreditLimit() {
        //creditLimit = 500.00F;
        return creditLimit;
    }

    public void setCreditLimit(float creditLimit) {
        this.creditLimit = creditLimit;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "Client{" + 
                       "email:" + customerEmail + 
                       ", dni:" + idCard + 
                       ", nom:" + customerName +
                       ", telèfon:" + phone +
                       ", crèdit màxim:" + creditLimit +
                       ", data naixement:" + birthDate +
                "}";
    }
    
}
