package dades;

import entitats.Comanda;
import entitats.ProductesComanda;
import java.sql.PreparedStatement;
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
            
            o.setNumOrdre(resultats.getInt("orderNumber"));
            o.setDataOrdre(resultats.getDate("orderDate"));
            o.setDataEntrega(resultats.getDate("requiredDate"));
            o.setDataEnviament(resultats.getDate("shippedDate"));
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
    
    public List<ProductesComanda> getProductes(int id) throws SQLException {
        List<ProductesComanda> ret = new ArrayList<>();
        String consulta = "select productCode, quantityOrdered, priceEach, orderLineNumber from orderdetails where orderNumber = ?";
        PreparedStatement sentencia = this.getCon().prepareStatement(consulta);
        
        sentencia.setInt(1, id);
        
        ResultSet resultats = sentencia.executeQuery();
        
        while (resultats.next()) {
            ProductesComanda pc = new ProductesComanda();
            
            pc.setIdProducte(resultats.getInt("productCode"));
            pc.setNom("prueba");
            pc.setNumberLine(resultats.getInt("orderLineNumber"));
            pc.setQuantitat(resultats.getInt("quantityOrdered"));
            pc.setUnitaryPrice(resultats.getFloat("priceEach"));
            
            ret.add(pc);
        }
        
        return ret;
        
    }
    
}
