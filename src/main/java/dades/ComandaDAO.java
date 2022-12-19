package dades;

import entitats.Comanda;
import entitats.ProductesComanda;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
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
     * Mostra totes les comandes de la taula 'orders'
     *
     * @author Víctor García Creació/Implementació
     * @throws SQLException
     */
    @Override
    public List<Comanda> getAll() {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        // Crear llistat on desar els registres dels clients
        List<Comanda> ret = new ArrayList<>();

        // Definir consulta SQL per llegir totes les dades de la taula 'clients'
        ResultSet resultats = null;
        
        try {
            resultats = stmt.executeQuery("SELECT * FROM orders;");

            // Mostrar resultats disponibles a la taula via nom del camp
            while (resultats.next()) {
                
                Comanda o = new Comanda();
                o.setNumOrdre(resultats.getInt("orderNumber"));
                o.setDataOrdre(resultats.getTimestamp("orderDate"));
                o.setDataEntrega(resultats.getTimestamp("requiredDate"));
                o.setDataEnviament(resultats.getTimestamp("shippedDate"));
                o.setCustomers_customerEmail(resultats.getString("customers_customerEmail"));
                

                List<ProductesComanda> productes = this.getProductes(resultats.getInt("orderNumber"));
                float total = 0;
                
                for (int i = 0; i < productes.size(); i++) {
                    total = total + productes.get(i).getTotal();
                }
                o.setTotal(total);
                ret.add(o);
            }
        } catch (SQLException ex) {
            
            System.out.println("Error gestionant la connexió a MySQL !!!");
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            Logger.getLogger(ClientDAO.class.getName()).log(Level.SEVERE, null, ex);
            
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    System.out.println("ERROR AL TANCAR STATEMENT: " + e);
                }
            }
            if (resultats != null) {
                try {
                    resultats.close();
                } catch (SQLException e) {
                    System.out.println("ERROR AL TANCAR RESULT SET: " + e);
                }

            }
        }

        return ret;
    }

    // En aquest cas no hem utilitzat la interfície
    @Override
    public void save(Comanda c) throws SQLException {
    }

    /**
     * Guarda una comanda
     * 
     * @param c
     * @return Retorna una id autoincremental
     * @throws SQLException
     * @author Víctor García Creació/Implementació
     * @author Pablo Morante - Creació/Implementació
     */
    public int saveCommand(Comanda c) throws SQLException {
        String consulta = "INSERT INTO orders (orderDate, requiredDate, shippedDate, customers_customerEmail) VALUES (?,?,?,?);";
        int newId = 0;

        PreparedStatement preparedStatement = null;
        try {

            preparedStatement = con.prepareStatement(consulta, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setTimestamp(1, c.getDataOrdre());
            preparedStatement.setTimestamp(2, c.getDataEntrega());
            preparedStatement.setTimestamp(3, c.getDataEnviament());
            preparedStatement.setString(4, c.getCustomers_customerEmail());

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
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    System.out.println("ERROR AL TANCAR STATEMENT EN ACCEDIR A COMANDA: " + e);
                }
            }
        }

        return newId;

    }

    /**
     * Actualitza una comanda
     * 
     * @param t
     * @throws SQLException
     * @author Pablo Morante - Creació/Implementació
     */
    @Override
    public void update(Comanda t) {
        String update = "UPDATE orders SET requiredDate = ? WHERE orderNumber = ?;";

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = con.prepareStatement(update);

            preparedStatement.setTimestamp(1, t.getDataEntrega());
            preparedStatement.setInt(2, t.getNumOrdre());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ClientDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    System.out.println("ERROR AL TANCAR STATEMENT EN ACCEDIR A CLIENT: " + e);
                }
            }
        }
    }

    /**
     * Elimina una comanda 
     * 
     * @param t
     * @throws java.sql.SQLIntegrityConstraintViolationException
     * @throws SQLException
     * @author Víctor Garcia - Creació/Implementació
     * @author Pablo Morante - Creació/Implementació
     */
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

    /**
     * Mostra una comanda de la taula 'orders'
     * 
     * @param t
     * @author Víctor Garcia - Creació/Implementació
     * @author Pablo Morante - Creació/Implementació
     */
    @Override
    public Comanda getOne(Comanda t) {
        Comanda ret = new Comanda();
        String consulta = "SELECT * FROM orders WHERE orderNumber = ?;";
        PreparedStatement preparedStatement = null;
        ResultSet resultats = null;
        
        try {
            
            preparedStatement = con.prepareStatement(consulta);

            preparedStatement.setInt(1, t.getNumOrdre());

            resultats = preparedStatement.executeQuery();

            while (resultats.next()) {
                ret.setNumOrdre(resultats.getInt("orderNumber"));
                ret.setDataOrdre(resultats.getTimestamp("orderDate"));
                ret.setDataEnviament(resultats.getTimestamp("shippedDate"));
                ret.setDataEntrega(resultats.getTimestamp("requiredDate"));
                ret.setCustomers_customerEmail(resultats.getString("customers_customerEmail"));
            }
            
        } catch (SQLException ex) {
            
            Logger.getLogger(ComandaDAO.class.getName()).log(Level.SEVERE, null, ex);
            
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    System.out.println("ERROR AL TANCAR STATEMENT: " + e);
                }
            }
            if (resultats != null) {
                try {
                    resultats.close();
                } catch (SQLException e) {
                    System.out.println("ERROR AL TANCAR RESULTSET: " + e);
                }

            }
        }
        return ret;
    }

    /**
     * Retorna tots els productes d'una comanda
     * 
     * @param id
     * @return Retorna una llista de ProductesComanda de la comanda amb el codi del parametre id
     * @throws SQLException
     * @author Izan Jimenez - Creació/Implementació
     */
    public List<ProductesComanda> getProductes(int id) {
        List<ProductesComanda> ret = new ArrayList<>();

        String consulta = "SELECT * FROM orderdetails WHERE orderNumber = ?;";
        String consultaNom = "SELECT productName FROM products WHERE productCode = ?;";
        PreparedStatement sentencia = null;
        PreparedStatement sentencia2 = null;
         ResultSet resultats = null;

        try {
            
            sentencia = con.prepareStatement(consulta);
            sentencia2 = con.prepareStatement(consultaNom);
            sentencia.setInt(1, id);

            resultats = sentencia.executeQuery();

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

        } catch (SQLException ex) {
            
            System.out.println("Error gestionant la connexió a MySQL !!!");
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            Logger.getLogger(ClientDAO.class.getName()).log(Level.SEVERE, null, ex);
            
        } finally {
            if (sentencia != null) {
                try {
                    sentencia.close();
                } catch (SQLException e) {
                    System.out.println("ERROR AL TANCAR STATEMENT: " + e);
                }
            }
            if (resultats != null) {
                try {
                    resultats.close();
                } catch (SQLException e) {
                    System.out.println("ERROR AL TANCAR RESULT SET: " + e);
                }

            }
        }
        
        return ret;

    }

    /**
     * Afegeix un producte a la taula 'orderDetails' amb una id de comanda
     * 
     * @param p
     * @param idComanda
     * @author Pablo Morante - Creació/Implementació
     */
    public void saveProduct(ProductesComanda p, int idComanda) {
        PreparedStatement preparedStatement = null;
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
        } finally {
            //tanquem el statement
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    System.out.println("ERROR AL TANCAR STAT: " + e);
                }

            }
        }
    }

    /**
     * Actualitza un producte a la taula 'orderDetails' amb una id de comanda
     * 
     * @param p
     * @param idComanda
     * @author Pablo Morante - Creació/Implementació
     */
    public void updateProduct(ProductesComanda p, int idComanda) {
        String update = "UPDATE orderdetails SET quantityOrdered = ? WHERE orderNumber = ? AND productCode = ?;";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = con.prepareStatement(update);

            preparedStatement.setInt(1, p.getQuantitat());
            preparedStatement.setInt(2, idComanda);
            preparedStatement.setInt(3, p.getIdProducte());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ClientDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //tanquem el statement
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    System.out.println("ERROR AL TANCAR STAT: " + e);
                }

            }
        }
    }

    /**
     * Elimina un producte a la taula 'orderDetails' amb una id de comanda
     * 
     * @param p
     * @param idComanda
     * @author Pablo Morante - Creació/Implementació
     */
    public void deleteProductFromComanda(ProductesComanda p, int idComanda) {
        PreparedStatement preparedStatement = null;
        String delete = "DELETE FROM orderdetails WHERE orderNumber = ? AND productCode = ?;";

        try {
            preparedStatement = con.prepareStatement(delete);

            preparedStatement.setInt(1, idComanda);
            preparedStatement.setInt(2, p.getIdProducte());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ComandaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //tanquem el statement
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    System.out.println("ERROR AL TANCAR STAT: " + e);
                }

            }
        }
    }

    /**
     * Elimina tots els productes a la taula 'orderDetails' amb una id de comanda
     * 
     * @param p
     * @param idComanda
     * @author Pablo Morante - Creació/Implementació
     */
    public void deleteAllProductsFromComanda(int idComanda) {
        PreparedStatement preparedStatement = null;
        String delete = "DELETE FROM orderdetails WHERE orderNumber = ?;";

        try {
            preparedStatement = con.prepareStatement(delete);

            preparedStatement.setInt(1, idComanda);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ComandaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //tanquem el statement
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    System.out.println("ERROR AL TANCAR STAT: " + e);
                }

            }
        }
    }

}
