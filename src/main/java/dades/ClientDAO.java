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
    
    /**
     * Constructor d'una instància de tipus ClientDAO, que permet utilitzar la 
     * connexió actual per accedir a la taula 'customers' i operar amb els seu registres.
     *
     * @throws java.sql.SQLException Mostra un error si no pot connectar amb la BD
     * @author Txell Llanas Creació - Implementació
     */    
    public ClientDAO() throws SQLException {
        super();
    }
    

    /**
     * Mostrar tots els clients de la taula 'customers' (RF56)
     * @return List de tipus CLient amb tots els registres de la BD
     * @author Txell Llanas - Implementació
     */
    @Override
    public List<Client> getAll() {
        
        // Crear llistat on desar els registres dels clients
        List<Client> ret = new ArrayList<>();

        // Definir consulta SQL per llegir totes les dades de la taula 'clients'
        ResultSet resultats = null;
        
        try {
            
            resultats = stmt.executeQuery("SELECT * FROM customers;");

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

    /**
     * Desar un client a la taula 'customers' (RF50)
     * @param c Instància de tipus Client a desar
     * @author Txell Llanas - Implementació
     */
    @Override
    public void save(Client c) {
        
        // Definir consulta SQL per desar les dades especificades dins la taula 'clients'
        String consulta = "INSERT INTO customers () VALUES (?,?,?,?,?,?);";
        
        PreparedStatement preparedStatement = null;
        try {
            
            preparedStatement = con.prepareStatement(consulta);
            
            preparedStatement.setString(1, c.getCustomerEmail());
            preparedStatement.setString(2, c.getIdCard());
            preparedStatement.setString(3, c.getCustomerName());
            preparedStatement.setString(4, c.getPhone());
            preparedStatement.setFloat(5, c.getCreditLimit());
            preparedStatement.setDate(6, c.getBirthDate());
            
            preparedStatement.executeUpdate();
            
        } catch (SQLException ex) {
            
            System.out.println("Error gestionant la connexió a MySQL !!!");
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
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
     * Modifica un client a la taula 'customers' (RF54)
     * @param c Instància de tipus Client a modificar
     * @author Txell Llanas - Implementació
     */
    @Override
    public void update(Client c) {
        // Definir consulta SQL per actualitzar les dades modificades dins la taula 'clients'
        String consulta = "UPDATE customers SET "
                            + "customerName = ? ,"
                            + "phone = ? ,"
                            + "creditLimit = ? ,"
                            + "birthDate = ? "
                        + "WHERE customerEmail = ?;";
        
        PreparedStatement preparedStatement = null;
        try {
            
            preparedStatement = con.prepareStatement(consulta);
            
            preparedStatement.setString(1, c.getCustomerName());
            preparedStatement.setString(2, c.getPhone());
            preparedStatement.setFloat( 3, c.getCreditLimit());
            preparedStatement.setDate(  4, c.getBirthDate());
            preparedStatement.setString(5, c.getCustomerEmail());
        
            preparedStatement.executeUpdate();
            
        } catch (SQLException ex) {
            
            System.out.println("Error gestionant la connexió a MySQL !!!");
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
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
     * Elimina un client a la taula 'customers' (RF52)
     * @param c Instància de tipus Client a eliminar
     * @author Txell Llanas - Implementació
     */
    @Override
    public void delete(Client c) {
        
        // Definir consulta SQL per eliminar el registre actual de la taula 'clients'
        String consulta = "DELETE FROM customers WHERE customerEmail = ?;";
        
        PreparedStatement preparedStatement = null;
        try {
            
            preparedStatement = con.prepareStatement(consulta);
            
            preparedStatement.setString(1, c.getCustomerEmail());
            preparedStatement.executeUpdate();
            System.out.println(">> Eliminat client: "+c.toString());
            
        } catch (SQLException ex) {
            
            System.out.println("Error gestionant la connexió a MySQL !!!");
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
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
     * Recupera un client de la taula 'customers'
     * @param c Instància de tipus Client a recuperar
     * @return Instància de tipus Client amb totes les dades recuperades de la BD
     * @author Txell Llanas - Implementació
     */
    @Override
    public Client getOne(Client c) {
        
        // Definir consulta SQL per llegir totes les dades de la taula 'clients'
        String consulta = "SELECT * FROM customers WHERE customerEmail=?"; 
        String id = c.getCustomerEmail();
        PreparedStatement preparedStatement = null;
         ResultSet resultats = null;
        
        try {
            
            preparedStatement = con.prepareStatement(consulta);            
            preparedStatement.setString(1, id);
            
            resultats = preparedStatement.executeQuery();
            while (resultats.next()) {

            c.setCustomerName(resultats.getString("customerName"));
            c.setPhone(resultats.getString("phone"));
            c.setCreditLimit(resultats.getFloat("creditLimit"));
            c.setBirthDate(resultats.getDate("birthDate"));
            }
            
        } catch (SQLException ex) {
            
            System.out.println("Error gestionant la connexió a MySQL !!!");
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            Logger.getLogger(ClientDAO.class.getName()).log(Level.SEVERE, null, ex);
            
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
        
        return c;
    }
    
}
