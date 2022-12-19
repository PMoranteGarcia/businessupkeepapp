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
 * @author Pablo Morante - Implementació
 * @author Victor García - Implementació
 * @author Izan Jimenez - Implementació
 */
public class AppConfigDAO extends DataLayer implements DAOInterface<AppConfig> {

    private final Statement statement = con.createStatement();                  // Preparar instància per realitzar una consulta SQL, recuperant la connexió des de DataLayer

    AppConfig defaults;                                                         // Instància per gestionar objectes de tipus AppConfig

    /**
     * *
     * AppConfigDAO()
     *
     * @throws SQLException SQLException
     */
    public AppConfigDAO() throws SQLException {
        super();
    }

    /**
     * Mètode que permet recuperar els valors especificats a la taula
     * 'appConfig'
     *
     * @return List amb tots els valors definits per defecte de l'aplicació
     * @author Txell Llanas - Creació/Implementació
     * @author Pablo Morante - Implementació
     * @author Victor García - Implementació
     * @author Izan Jimenez - Implementació
     */
    @Override
    public List<AppConfig> getAll() {

        List<AppConfig> ret = new ArrayList<>();                                // Crear llistat on desar els registres dels valors per defecte
        ResultSet resultats;                                                    // Instància per recollir resultats provinents de la consulta a realitzar

        try {

            statement.setMaxRows(1);                                            // Limitar a 1 registre: interessa recuperar només el 1r registre
            resultats = statement.executeQuery("SELECT * FROM appConfig;");     // Definir consulta SQL per llegir totes les dades de la taula 'appConfig'

            while (resultats.next()) // Mostrar resultats disponibles a la taula via nom del camp
            {
                defaults = new AppConfig();

                defaults.setDefaultCreditLimit(resultats.getFloat("defaultCreditLimit"));
                defaults.setMinCustomerAge(resultats.getInt("minCustomerAge"));
                defaults.setDefaultStock(resultats.getInt("defaultQuantityInStock"));
                defaults.setMaxOrderAmount(resultats.getFloat("maxOrderAmount"));
                defaults.setMinShippingHours(resultats.getInt("minShippingHours"));
                defaults.setDefaultQuantityOrdered(resultats.getInt("defaultQuantityOrdered"));
                defaults.setDefaultProductBenefit(resultats.getInt("defaultProductBenefit"));
                defaults.setMaxLinesPerOrdes(resultats.getInt("maxLinesPerOrder"));

                ret.add(defaults);
            }

            return ret;

        } catch (SQLException ex) {
            Logger.getLogger(AppConfigDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.out.println("ERROR AL TANCAR STATEMENT EN ACCEDIR A APPCONFIG: " + e);
                }
            }
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

    @Override
    public AppConfig getOne(AppConfig defaults) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
