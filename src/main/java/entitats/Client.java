package entitats;

import java.sql.Date;

/**
 * Classe que defineix l'objecte 'Client'. Permet Definir les propietats i
 * funcionalitats associades a un client.
 *
 * @author Pablo Morante - Creació
 * @author Victor García - Creació
 * @author Txell Llanas - Implementació
 */
public class Client {

    private String customerEmail, idCard, customerName, phone;
    private float creditLimit;
    private Date birthDate;

    /**
     * Constructor que serveix per crear un objecte de tipus Client.
     *
     * @author Txell Llanas - Creació/Implementació
     */
    public Client() {
    }

    /**
     * Constructor que serveix per crear un objecte de tipus Client definint
     * tots els atributs.
     *
     * @param customerEmail String que conté un email
     * @param idCard String que conté un dni
     * @param customerName String que conté un nom de Client
     * @param phone String que conté un nombre de telèfon
     * @param creditLimit Float que conté un valor pel límit de crèdit
     * @param birthDate Date per definir la data de naixement del Client
     * @author Txell Llanas - Creació/Implementació
     */
    public Client(String customerEmail, String idCard, String customerName, String phone, float creditLimit, Date birthDate) {
        this.customerEmail = customerEmail;
        this.idCard = idCard;
        this.customerName = customerName;
        this.phone = phone;
        this.creditLimit = creditLimit;
        this.birthDate = birthDate;
    }

    /**
     * Constructor que serveix per crear un objecte de tipus Client.
     *
     * @param customerEmail String que conté un email
     * @author Txell Llanas - Creació/Implementació
     */
    public Client(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    /**
     * Mètode que permet obtenir el mail del Client.
     *
     * @return String que conté un email
     * @author Txell Llanas - Creació/Implementació
     */
    public String getCustomerEmail() {
        return customerEmail;
    }

    /**
     * Mètode que permet definir el mail del Client.
     *
     * @param customerEmail String que conté un email
     * @author Txell Llanas - Creació/Implementació
     */
    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    /**
     * Mètode que permet obtenir el Dni del Client.
     *
     * @return String que conté un dni
     * @author Txell Llanas - Creació/Implementació
     */
    public String getIdCard() {
        return idCard;
    }

    /**
     * Mètode que permet definir el Dni del Client.
     *
     * @param idCard String que conté un dni
     * @author Txell Llanas - Creació/Implementació
     */
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    /**
     * Mètode que permet obtenir el Nom del Client.
     *
     * @return String que conté un nom de Client
     * @author Txell Llanas - Creació/Implementació
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Mètode que permet definir el Nom del Client.
     *
     * @param customerName String que conté un nom de Client
     * @author Txell Llanas - Creació/Implementació
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Mètode que permet obtenir el Telèfon del Client.
     *
     * @return String que conté un nombre de telèfon
     * @author Txell Llanas - Creació/Implementació
     */
    public String getPhone() {
        return phone.replaceAll(" ", "");
    }

    /**
     * Mètode que permet definir el Telèfon del Client.
     *
     * @param phone String que conté un nombre de telèfon
     * @author Txell Llanas - Creació/Implementació
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Mètode que permet obtenir el límit de crèdit del Client.
     *
     * @return Float que conté un valor pel límit de crèdit
     * @author Txell Llanas - Creació/Implementació
     */
    public float getCreditLimit() {
        return creditLimit;
    }

    /**
     * Mètode que permet definir el límit de crèdit del Client.
     *
     * @param creditLimit Float que conté un valor pel límit de crèdit
     * @author Txell Llanas - Creació/Implementació
     */
    public void setCreditLimit(float creditLimit) {
        this.creditLimit = creditLimit;
    }

    /**
     * Mètode que permet obtenir la data de naixement del Client.
     *
     * @return Date per definir la data de naixement del Client
     * @author Txell Llanas - Creació/Implementació
     */
    public Date getBirthDate() {
        return birthDate;
    }

    /**
     * Mètode que permet definir la data de naixement del Client.
     *
     * @param birthDate Date per definir la data de naixement del Client
     * @author Txell Llanas - Creació/Implementació
     */
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * Sobreescriu el mètode original per mostrar la informació d'un objecte de
     * tipus Client.
     *
     * @return String amb tota la informació d'un Client
     * @author Txell Llanas - Creació/Implementació
     */
    @Override
    public String toString() {
        return "Client{"
                + "email:" + customerEmail
                + ", dni:" + idCard
                + ", nom:" + customerName
                + ", telèfon:" + phone
                + ", crèdit màxim:" + creditLimit
                + ", data naixement:" + birthDate
                + "}";
    }

}
