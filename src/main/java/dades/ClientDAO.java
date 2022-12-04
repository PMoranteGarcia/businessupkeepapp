package dades;

import entitats.Client;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import presentacio.ClientsFormController;

/**
 * CRUD de l'entitat customers a la BBDD.
 * 
 * @author Pablo Morante - Creació
 * @author Victor García - Creació
 * @author Txell Llanas - Implementació
 */
public class ClientDAO extends DataLayer implements DAOInterface<Client> {
    
    // Preparar instància per realitzar una consulta SQL
    private Statement stmt = con.createStatement();
    
        
    public ClientDAO() throws SQLException{
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
        //System.out.println("ret: "+ret);
        return ret;
    }

    @Override
    public void save(Client c) throws SQLException {
        
        ClientsFormController fields = new ClientsFormController();
        
        // Definir consulta SQL per llegir totes les dades de la taula 'clients'
        String consulta = "INSERT INTO customers () VALUES (?,?,?,?,?,?);"; 
        
        PreparedStatement preparedStatement = con.prepareStatement(consulta);
        preparedStatement.setString(1, c.getCustomerEmail());
        preparedStatement.setString(2, c.getIdCard());
        preparedStatement.setString(3, c.getCustomerName());
        preparedStatement.setString(4, c.getPhone());
        preparedStatement.setFloat(5, c.getCreditLimit());
        preparedStatement.setDate(6, c.getBirthDate());
        
        preparedStatement.executeUpdate();
    }

    @Override
    public void update(Client t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(Client t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Client getOne(Client t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
