package dades;

import entitats.Client;
import entitats.Comanda;
import entitats.Producte;
import entitats.ProductesComanda;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    /**
     * Mostrar tots els clients de la taula 'orders'
     *
     * @author Víctor García
     * @throws SQLException
     */
    @Override
    public List<Comanda> getAll() throws SQLException {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        // Crear llistat on desar els registres dels clients
        List<Comanda> ret = new ArrayList<>();

        // Definir consulta SQL per llegir totes les dades de la taula 'clients'
        ResultSet resultats = stmt.executeQuery("SELECT * FROM orders;");

        // Mostrar resultats disponibles a la taula via nom del camp
        while (resultats.next()) {
            Comanda o = new Comanda();
            o.setNumOrdre(resultats.getInt("orderNumber"));
            o.setDataOrdre(resultats.getTimestamp("orderDate"));
            o.setDataEntrega(resultats.getTimestamp("requiredDate"));
            o.setDataEnviament(resultats.getTimestamp("shippedDate"));
            o.setCustomers_customerEmail(resultats.getString("customers_customerEmail"));

            ret.add(o);
        }

        return ret;
    }

    @Override
    public void save(Comanda c) throws SQLException {

    }

    public int saveCommand(Comanda c) throws SQLException {
        String consulta = "INSERT INTO orders (orderDate, requiredDate, customers_customerEmail) VALUES (?,?,?);";
        int newId = 0;

        PreparedStatement preparedStatement;
        try {

            preparedStatement = con.prepareStatement(consulta, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setTimestamp(1, c.getDataOrdre());
            preparedStatement.setTimestamp(2, c.getDataEntrega());
            preparedStatement.setString(3, c.getCustomers_customerEmail());

            preparedStatement.executeUpdate();
            ResultSet temp = preparedStatement.getGeneratedKeys();
            if (temp.next()) {
                newId = Integer.parseInt(temp.getString(1));
            }
        } catch (SQLException ex) {
            System.out.println("ERROR amb la connexió a SQL");
            System.out.println("SQLExceptionq: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            Logger.getLogger(ClientDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return newId;

    }

    @Override
    public void update(Comanda t) {
        String update = "UPDATE orders SET requiredDate = ? WHERE orderNumber = ?;";

        PreparedStatement preparedStatement;
        try {
            preparedStatement = con.prepareStatement(update);

            preparedStatement.setTimestamp(1, t.getDataEntrega());
            preparedStatement.setInt(2, t.getNumOrdre());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ClientDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Comanda t) throws java.sql.SQLIntegrityConstraintViolationException, SQLException {
        // Definir consulta SQL per eliminar el registre actual de la taula 'orders'
        String consulta = "DELETE FROM orders WHERE orderNumber = ?;";

        PreparedStatement preparedStatement;

        preparedStatement = con.prepareStatement(consulta);

        preparedStatement.setString(1, Integer.toString(t.getNumOrdre()));
        preparedStatement.executeUpdate();
        System.out.println(">> Eliminada ordre: " + t.toString());

    }

    @Override
    public Comanda getOne(Comanda t) {
        Comanda ret = new Comanda();
        String consulta = "SELECT * FROM orders WHERE orderNumber = ?;";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(consulta);

            preparedStatement.setInt(1, t.getNumOrdre());

            ResultSet resultats = preparedStatement.executeQuery();

            while (resultats.next()) {
                ret.setNumOrdre(resultats.getInt("orderNumber"));
                ret.setDataOrdre(resultats.getTimestamp("orderDate"));
                ret.setDataEnviament(resultats.getTimestamp("shippedDate"));
                ret.setDataEntrega(resultats.getTimestamp("requiredDate"));
                ret.setCustomers_customerEmail(resultats.getString("customers_customerEmail"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ComandaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    public List<ProductesComanda> getProductes(int id) throws SQLException {
        List<ProductesComanda> ret = new ArrayList<>();

        String consulta = "SELECT * FROM orderdetails WHERE orderNumber = ?;";
        String consultaNom = "SELECT productName FROM products WHERE productCode = ?;";
        PreparedStatement sentencia = con.prepareStatement(consulta);
        PreparedStatement sentencia2 = con.prepareStatement(consultaNom);

        sentencia.setInt(1, id);

        ResultSet resultats = sentencia.executeQuery();

        while (resultats.next()) {
            ProductesComanda pc = new ProductesComanda();

            pc.setOrderNummber(id);
            pc.setIdProducte(resultats.getInt("productCode"));
            pc.setNumberLine(resultats.getInt("orderLineNumber"));
            pc.setQuantitat(resultats.getInt("quantityOrdered"));
            pc.setUnitaryPrice(resultats.getFloat("priceEach"));
            pc.setTotal(resultats.getFloat("priceEach") * resultats.getInt("quantityOrdered"));

            sentencia2.setInt(1, resultats.getInt("productCode"));
            ResultSet resultatNom = sentencia2.executeQuery();
            while (resultatNom.next()) {
                pc.setNom(resultatNom.getString("productName"));
            }

            ret.add(pc);
        }

        return ret;

    }

    public void saveProduct(ProductesComanda p, int idComanda) {
        PreparedStatement preparedStatement;
        String insert = "insert into orderdetails values ((select orderNumber from orders where orderNumber = ?), (select productCode from products where productCode = ?), ?, ?, ?)";
        try {
            preparedStatement = con.prepareStatement(insert);

            preparedStatement.setInt(1, idComanda);
            preparedStatement.setInt(2, p.getIdProducte());
            preparedStatement.setInt(3, p.getQuantitat());
            preparedStatement.setFloat(4, p.getUnitaryPrice());
            preparedStatement.setInt(5, p.getOrderNumber());
            preparedStatement.executeUpdate();
            System.out.println(">> Producte insertat: " + p.toString());
        } catch (java.sql.SQLIntegrityConstraintViolationException ex) {
            updateProduct(p, idComanda);
        } catch (SQLException ex) {
            Logger.getLogger(ClientDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateProduct(ProductesComanda p, int idComanda) {
        String update = "UPDATE orderdetails SET quantityOrdered = ? WHERE orderNumber = ? AND productCode = ?;";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = con.prepareStatement(update);

            preparedStatement.setInt(1, p.getQuantitat());
            preparedStatement.setInt(2, idComanda);
            preparedStatement.setInt(3, p.getIdProducte());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ClientDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteProductFromComanda(ProductesComanda p, int idComanda) {
        PreparedStatement preparedStatement;
        String delete = "DELETE FROM orderdetails WHERE orderNumber = ? AND productCode = ?;";

        try {
            preparedStatement = con.prepareStatement(delete);

            preparedStatement.setInt(1, idComanda);
            preparedStatement.setInt(2, p.getIdProducte());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ComandaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteAllProductsFromComanda(int idComanda) {
        PreparedStatement preparedStatement;
        String delete = "DELETE FROM orderdetails WHERE orderNumber = ?;";

        try {
            preparedStatement = con.prepareStatement(delete);

            preparedStatement.setInt(1, idComanda);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ComandaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
