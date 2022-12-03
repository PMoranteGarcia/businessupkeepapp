package dades;

import entitats.Client;
import java.sql.SQLException;
import java.util.List;

/**
 * CRUD de l'entitat customers a la BBDD.
 * 
 * @author Pablo Morante - Creació
 * @author Victor García - Creació
 * @author Txell Llanas  - Implementació
 */
public class ClientDAO extends DataLayer implements DAOInterface<Client> {
    
    public ClientDAO() throws SQLException{
        super();
    }

    @Override
    public List<Client> getAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void save(Client t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(Client t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(Client t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Client getOne(Client t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
