package dades;

import entitats.AppConfig;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * CRUD de l'entitat appConfig a la BBDD.
 * 
 * @author Txell Llanas - Creació/Implementació
 */
public class AppConfigDAO extends DataLayer implements DAOInterface<AppConfig> {
    
    // Preparar instància per realitzar una consulta SQL
    private final Statement statement = con.createStatement();
    
    // Instància per gestionar objectes de tipus AppConfig
    AppConfig defaults;
    
        
    public AppConfigDAO() throws SQLException {
        super();
    }

    /**
     * Mètode que permet recuperar els valors especificats a la taula 'appConfig'
     * @return List amb tots els valors definits per defecte de l'aplicació
     */
    @Override
    public List<AppConfig> getAll() {
        
        // Crear llistat on desar els registres dels valors per defecte
        List<AppConfig> ret = new ArrayList<>();

        // Definir consulta SQL per llegir totes les dades de la taula 'appConfig'
        ResultSet resultats;

        try {
            
            statement.setMaxRows(1);
            resultats = statement.executeQuery("SELECT * FROM appConfig;"); 
            
            while (resultats.next())                                            // Mostrar resultats disponibles a la taula via nom del camp
            {
                defaults = new AppConfig();

                defaults.setDefaultCreditLimit(resultats.getFloat("defaultCreditLimit"));
                defaults.setMinCustomerAge(resultats.getInt("minCustomerAge"));
                defaults.setDefaultStock(resultats.getInt("defaultQuantityInStock"));
                //defaults.setComandesList(resultats.getObject(""));

                ret.add(defaults);
            }

            return ret;
            
        } catch (SQLException ex) {
            Logger.getLogger(AppConfigDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return ret;
    }

    @Override
    public void save(AppConfig t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(AppConfig t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(AppConfig t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     * 
     * @param defaults
     * @return 
     */
    @Override
    public AppConfig getOne(AppConfig defaults) {

        // Definir consulta SQL per llegir el primer registre de la taula 'appConfig'
        String consulta = "SELECT * FROM appConfig";
        try {
            
            statement.setMaxRows(1);
            ResultSet resultats = statement.executeQuery(consulta);
            
            // Mostrar resultats disponibles a la taula via nom del camp
            while (resultats.next())
            {
                defaults.setDefaultCreditLimit(resultats.getFloat("defaultCreditLimit"));
                defaults.setMinCustomerAge(resultats.getInt("minCustomerAge"));
            }            
            
        } catch (SQLException ex) {
            Logger.getLogger(AppConfigDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return defaults;
    }
    
    public AppConfig getDefaults() {
        
        // Definir consulta SQL per llegir el primer registre de la taula 'appConfig'
        String consulta = "SELECT * FROM appConfig";
        try {
            
            defaults = new AppConfig();
            statement.setMaxRows(1);
            ResultSet resultats = statement.executeQuery(consulta);
            
            System.out.println(">> Carregar RS a objecte DAO");
            // Mostrar resultats disponibles a la taula via nom del camp
           // while (resultats.next())
           // {
                defaults.setDefaultCreditLimit(resultats.getFloat("defaultCreditLimit"));
                defaults.setMinCustomerAge(resultats.getInt("minCustomerAge"));
            //}            
            
        } catch (SQLException ex) {
            Logger.getLogger(AppConfigDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("DAO: "+defaults.getDefaultCreditLimit());
        return defaults;
    }
}
