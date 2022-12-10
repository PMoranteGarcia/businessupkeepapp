package dades;

import entitats.Client;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * CRUD de l'entitat customers a la BBDD.
 * 
 * @author Pablo Morante - Creació
 * @author Victor García - Creació
 * @author Txell Llanas - Implementació
 */
public class ClientDAO extends DataLayer implements DAOInterface<Client> {
    
    // Preparar instància per realitzar una consulta SQL
    private final Statement stmt = con.createStatement();
    
        
    public ClientDAO() throws SQLException {
        super();
    }
    

    /**
     * Mostrar tots els clients de la taula 'customers' (RF56)
     * @author Txell Llanas
     * @throws SQLException
     */
    @Override
    public List<Client> getAll() throws SQLException {
        
        // Crear llistat on desar els registres dels clients
        List<Client> ret = new ArrayList<>();

        // Definir consulta SQL per llegir totes les dades de la taula 'clients'
        ResultSet resultats = stmt.executeQuery("SELECT * FROM customers;"); 

        // Mostrar resultats disponibles a la taula via nom del camp
        while (resultats.next())
        {
            Client c = new Client();

            c.setCustomerEmail(resultats.getString("customerEmail"));
            c.setIdCard(resultats.getString("idCard"));
            c.setCustomerName(resultats.getString("customerName"));
            c.setPhone(resultats.getString("phone"));
            c.setCreditLimit(resultats.getFloat("creditLimit"));
            c.setBirthDate(resultats.getDate("birthDate"));

            ret.add(c);
        }
        
        return ret;
    }

    @Override
    public void save(Client c) {
        
        // Definir consulta SQL per desar les dades especificades dins la taula 'clients'
        String consulta = "INSERT INTO customers () VALUES (?,?,?,?,?,?);";
        
        PreparedStatement preparedStatement;
        try {
            
            preparedStatement = con.prepareStatement(consulta);
            
            preparedStatement.setString(1, c.getCustomerEmail());
            preparedStatement.setString(2, c.getIdCard());
            preparedStatement.setString(3, c.getCustomerName());
            preparedStatement.setString(4, c.getPhone());
            preparedStatement.setFloat(5, c.getCreditLimit());
            preparedStatement.setDate(6, c.getBirthDate());
            
            preparedStatement.executeUpdate();                
                
            //this.con.close(); //nou
            
        } catch (SQLException ex) {
            Logger.getLogger(ClientDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @Override
    public void update(Client c) {
        // Definir consulta SQL per actualitzar les dades modificades dins la taula 'clients'
        String consulta = "UPDATE customers SET "
                            + "customerName = ? ,"
                            + "phone = ? ,"
                            + "creditLimit = ? ,"
                            + "birthDate = ? "
                        + "WHERE customerEmail = ?;";
        
        PreparedStatement preparedStatement;
        try {
            
            preparedStatement = con.prepareStatement(consulta);
            
            preparedStatement.setString(1, c.getCustomerName());
            preparedStatement.setString(2, c.getPhone());
            preparedStatement.setFloat( 3, c.getCreditLimit());
            preparedStatement.setDate(  4, c.getBirthDate());
            preparedStatement.setString(5, c.getCustomerEmail());
        
            preparedStatement.executeUpdate();
            
            //this.con.close(); //nou
            
        } catch (SQLException ex) {
            Logger.getLogger(ClientDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public void delete(Client c) {
        
        // Definir consulta SQL per eliminar el registre actual de la taula 'clients'
        String consulta = "DELETE FROM customers WHERE customerEmail = ?;";
        
        PreparedStatement preparedStatement;
        try {
            
            preparedStatement = con.prepareStatement(consulta);
            
            preparedStatement.setString(1, c.getCustomerEmail());
            preparedStatement.executeUpdate();
            System.out.println(">> Eliminat client: "+c.toString());
            
            //this.con.close(); //nou
            
        } catch (SQLException ex) {
            Logger.getLogger(ClientDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public Client getOne(Client c) {
        
        // Definir consulta SQL per llegir totes les dades de la taula 'clients'
        String consulta = "SELECT * FROM customers WHERE customerEmail=?"; 
        String id = c.getCustomerEmail();
        PreparedStatement preparedStatement;
        
        try {
            
            preparedStatement = con.prepareStatement(consulta);            
            preparedStatement.setString(1, id);
            
            ResultSet resultats = preparedStatement.executeQuery();
            System.out.println("Client recuperat: "+resultats);
            while (resultats.next()) {

            c.setCustomerName(resultats.getString("customerName"));
            c.setPhone(resultats.getString("phone"));
            c.setCreditLimit(resultats.getFloat("creditLimit"));
            c.setBirthDate(resultats.getDate("birthDate"));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ClientDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return c;
    }
    
}
