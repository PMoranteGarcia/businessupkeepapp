package dades;

import entitats.Client;import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * CRUD de l'entitat customers a la BBDD.
 * 
 * @author Pablo Morante - Creació
 * @author Victor García - Creació
 * @author Txell Llanas - Implementació
 */
public class ClientDAO extends DataLayer implements DAOInterface<Client> {
    
    public ClientDAO() throws SQLException{
        super();
    }

    //<editor-fold defaultstate="collapsed" desc="Funcions C.R.U.D.">
    /**
     * Mostrar tots els clients de la taula 'customers' (RF56)
     * @author Txell Llanas
     * @throws SQLException
     */
    @Override
    public List<Client> getAll() throws SQLException {
        // Crear llistat on desar els registres dels clients
        List<Client> ret = new ArrayList<>();
        
        // Preparar consulta SQL
        Statement stmt = con.createStatement();

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
    public void save(Client t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
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
    //</editor-fold>
    
}
