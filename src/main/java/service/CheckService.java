package service;

import java.sql.SQLException;
import java.util.List;

import model.Check;
import model.DAO.CheckDAO;

public class CheckService extends AbstractService<Check, CheckDAO> {

    public CheckService() {
        super(new CheckDAO());
    }

    public List<Check> findActiveChecks() throws SQLException {
        return dao.findByStatus('A');
    }

    public List<Check> findByStatus(char status) throws SQLException {
        return dao.findByStatus(status);
    }
}