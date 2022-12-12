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
    
    public DataLayer() throws SQLException {
        this.con = connectarBD("m03uf6_22_23","root","izan1234");
    }
    
    public Connection connectarBD (String db, String user, String password) throws SQLException {
        Connection ret = null;
        ret = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+db+"?useUnicode=true&"
                            + "useJDBCCompliantTimezoneShift=true&"
                            + "useLegacyDatetimeCode=false&serverTimezone=UTC", user, password);
        return ret;
    }
    
    public Connection getCon() {
        return con;
    }
    
    public void setCon(Connection _con) {
        con = _con;
    }
    
    public void Close() throws SQLException {
        if (con != null) {
            this.con.close();
        }
    }
    
}
