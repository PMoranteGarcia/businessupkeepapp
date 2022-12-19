package dades;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

/**
 * Connexió a BBDD.
 *
 * @author Pablo Morante - Creació/Implementació
 * @author Victor García - Creació/Implementació
 */
public abstract class DataLayer {

    Connection con;

    /**
     * *
     * Contructor DataLayer
     * @throws SQLException SQLException
     */
    public DataLayer() throws SQLException {
        this.con = connectarBD("m03uf6_22_23", "user", "password");
    }

    /**
     * Metode per crear la conexió amb la BBDD
     *
     * @param db url de la BBDD
     * @param user usuari de la BBDD
     * @param password password de la BBDD
     * @return retorna la conexió
     * @throws SQLException SQLException
     */
    public Connection connectarBD(String db, String user, String password) throws SQLException {
        Connection ret = null;
        ret = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + db + "?useUnicode=true&"
                + "useJDBCCompliantTimezoneShift=true&"
                + "useLegacyDatetimeCode=false&serverTimezone=UTC", user, password);
        return ret;
    }

    /**
     * *
     * getCon
     *
     * @return Connection
     */
    public Connection getCon() {
        return con;
    }

    /**
     * *
     * setCon
     *
     * @param _con conexió
     */
    public void setCon(Connection _con) {
        con = _con;
    }

    /**
     * *
     * Close
     *
     * @throws SQLException SQLException
     */
    public void Close() throws SQLException {
        if (con != null) {
            this.con.close();
        }
    }

}
