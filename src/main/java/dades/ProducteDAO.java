package dades;

import entitats.Producte;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * CRUD de Productes a la BBDD.
 *
 * @author Pablo Morante - Creació
 * @author Victor García - Creació
 * @author Izan Jimenez - Creació/Implementació
 */
public class ProducteDAO extends DataLayer implements DAOInterface<Producte> {

    //comandes SQL
    final String INSERT = "INSERT INTO products(productName, productDescription, quantityInStock, buyPrice) VALUES (?, ?, ?, ?)";
    final String UPDATE = "UPDATE products SET productName = ?, productDescription = ?, quantityInStock = ?, buyPrice = ? WHERE productCode = ?";
    final String DELETE = "DELETE FROM products WHERE productCode = ?";
    final String GETALL = "SELECT * FROM products";
    final String GETONE = "SELECT * FROM products WHERE productCode = ?";

    /**
     * *
     * Al crear un ProducteDAO crea una connexió amb la BBDD
     *
     * @throws java.sql.SQLException
     */
    public ProducteDAO() throws SQLException {
        super();
    }

    /**
     * Desar un producte a la taula 'products' (RF58)
     *
     * @param p Instància de tipus Producte a desar
     * @author Izan Jimenez - Implementació
     */
    @Override
    public void save(Producte p) {
        //es prepara el Statement
        System.out.println("Guardant producte");
        PreparedStatement stat = null;
        ResultSet rs = null;

        try {
            //es carrega el statement amb la connexió i la comanda
            stat = super.getCon().prepareStatement(INSERT);
            //inserim els camps del producte al statement
            stat.setString(1, p.getProductName());
            stat.setString(2, p.getProductDescription());
            stat.setInt(3, p.getQuantityInStock());
            stat.setFloat(4, p.getBuyPrice());

            //si executeUpdate torna 0, no s'ha afegit/modificat cap fila a la BBDD
            if (stat.executeUpdate() == 0) {
                throw new SQLException();
            }
        } catch (SQLException e) {
            System.out.println("ERROR AL GUARDAR PRODUCTE: " + e);
        } finally {
            //tanquem l'statement
            if (stat != null) {
                try {
                    stat.close();
                } catch (SQLException e) {
                    System.out.println("ERROR AL TANCAR STAT " + e);
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.out.println("ERROR AL TANCAR RS: " + e);
                }
            }
        }
    }

    /**
     * Modifica un producte a la taula 'products' (RF62)
     *
     * @param t Instància de tipus Producte a modificar
     * @author Izan Jimenez - Implementació
     */
    @Override
    public void update(Producte t) {
        System.out.println("Modificant producte");
        PreparedStatement stat = null;
        try {
            //es carrega l'statement amb la connexió i la comanda
            stat = super.getCon().prepareStatement(UPDATE);
            //inserim els camps del producte al statement
            stat.setString(1, t.getProductName());
            stat.setString(2, t.getProductDescription());
            stat.setInt(3, t.getQuantityInStock());
            stat.setFloat(4, t.getBuyPrice());
            stat.setInt(5, t.getProductCode());

            //si executeUpdate torna 0, no s'ha afegit/modificat cap filaa la BBDD
            if (stat.executeUpdate() == 0) {
                throw new SQLException();
            }
        } catch (SQLException e) {
            System.out.println("ERROR AL GUARDAR PRODUCTE: " + e);
        } finally {
            //tanquem el statement
            if (stat != null) {
                try {
                    stat.close();
                } catch (SQLException e) {
                    System.out.println("ERROR AL TANCAR STAT: " + e);
                }

            }
        }
    }

    /**
     * Elimina un producte a la taula 'products' (RF60)
     *
     * @param t Instància de tipus Producte a eliminar
     * @author Izan Jimenez - Implementació
     */
    @Override
    public void delete(Producte t) {
        PreparedStatement stat = null;
        System.out.println("Eliminat producte : " + t.getProductCode());

        try {
            stat = super.getCon().prepareStatement(DELETE);
            //posem el codi del producte a eliminar en la consulta
            stat.setInt(1, t.getProductCode());
            //si executeUpdate torna 0, no s'ha afegit/modificat cap fila a la BBDD
            if (stat.executeUpdate() == 0) {
                throw new SQLException();
            }
        } catch (SQLException e) {
            System.out.println("ERROR EN ELIMINAR PRODUCTE: " + e);
        } finally {
            if (stat != null) {
                try {
                    stat.close();
                } catch (SQLException e) {
                    System.out.println("ERROR AL TANCAR STAT EN ELIMINAR PRODUCTE: " + e);
                }
            }
        }
    }

    /**
     * Recupera un producte de la taula 'products'
     *
     * @param t Instància de tipus Producte a recuperar
     * @return Instància de tipus Producte amb totes les dades recuperades de la
     * BBDD
     * @author Izan Jimenez - Implementació
     */
    @Override
    public Producte getOne(Producte t) {
        PreparedStatement stat = null;
        ResultSet rs = null;
        Producte p = null;

        try {
            //preparem la consulta
            stat = super.getCon().prepareStatement(GETONE);
            stat.setInt(1, t.getProductCode());
            rs = stat.executeQuery();
            //si rep algun paràmetre el transformem a Producte
            if (rs.next()) {
                p = convertir(rs);
            } else {
                System.out.println("No s'ha trobat aquest registre");
                throw new SQLException();
            }
        } catch (SQLException e) {
            System.out.println("Error al obtenir un producte: " + e);
        } finally {
            if (stat != null) {
                try {
                    stat.close();
                } catch (SQLException e) {
                    System.out.println("ERROR AL TANCAR STAT: " + e);
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.out.println("ERROR AL TANCAR RS: " + e);
                }

            }
        }
        return p;

    }

    /**
     * Recupera tots els productes de la taula 'productes' (RF64)
     *
     * @return List de Producte de tipus Producte amb tots els registres de la BBDD
     * @author Izan Jimenez - Implementació
     */
    @Override
    public List<Producte> getAll() {
        PreparedStatement stat = null;
        ResultSet rs = null;
        List<Producte> p = new ArrayList<>();

        try {
            //fem la consulta a la BBDD
            stat = super.getCon().prepareStatement(GETALL);
            rs = stat.executeQuery();
            //mentre retorni un resultat, el guardem a la llista que retornarem
            while (rs.next()) {
                p.add(convertir(rs));
            }

        } catch (SQLException e) {
            System.out.println("Error al obtenir un producte: " + e);
        } finally {
            if (stat != null) {
                try {
                    stat.close();
                } catch (SQLException e) {
                    System.out.println("ERROR AL TANCAR STAT: " + e);
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.out.println("ERROR AL TANCAR RS: " + e);
                }

            }
        }
        return p;
    }

    /**
     * *Transforma un Resulset no null a un instància Producte
     *
     * @param rs
     * @return Retorna una instància Producte del ResulSet
     * @throws SQLException
     * @author Izan JImenez - Implementació
     */
    private Producte convertir(ResultSet rs) throws SQLException {
//      agafem els atributs
        String nomProducte = rs.getString("productName");
        String descProducte = rs.getString("productDescription");
        int stock = rs.getInt("quantityInStock");
        float preu = rs.getFloat("buyPrice");
//      retornem una instància producte
        Producte p = new Producte(nomProducte, descProducte, stock, preu);
        p.setProductCode(rs.getInt("productCode"));

        return p;
    }

}
