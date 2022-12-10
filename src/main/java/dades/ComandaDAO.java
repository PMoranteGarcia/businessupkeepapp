package dades;

import entitats.Comanda;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * CRUD de Comandes a la BBDD.
 * 
 * @author Pablo Morante - Creació/Implementació
 * @author Victor García - Creació/Implementació
 */
public class ComandaDAO extends DataLayer implements DAOInterface<Comanda> {
    
    // Preparar instància per realitzar una consulta SQL
    private final Statement stmt = con.createStatement();
    
    public ComandaDAO() throws SQLException {
        super();
    }

    @Override
    public List<Comanda> getAll() throws SQLException {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        // Crear llistat on desar els registres dels clients
        List<Comanda> ret = new ArrayList<>();

        // Definir consulta SQL per llegir totes les dades de la taula 'clients'
        ResultSet resultats = stmt.executeQuery("SELECT * FROM orders;"); 

        // Mostrar resultats disponibles a la taula via nom del camp
        while (resultats.next())
        {
            Comanda o = new Comanda();

            o.setOrderNumber(resultats.getInt("orderNumber"));
            o.setOrderDate(resultats.getDate("orderDate"));
            o.setRequiredDate(resultats.getDate("requiredDate"));
            o.setShippedDate(resultats.getDate("shippedDate"));
            o.setCustomers_customerEmail(resultats.getString("customers_customerEmail"));

            ret.add(o);
        }
        
        return ret;
    }

    @Override
    public void save(Comanda t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(Comanda t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Comanda t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Comanda getOne(Comanda t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
