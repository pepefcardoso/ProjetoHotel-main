package service;

import java.sql.SQLException;
import java.util.List;

import model.DAO.ReservaQuartoDAO;
import model.ReservaQuarto;

public class ReservaQuartoService extends AbstractService<ReservaQuarto, ReservaQuartoDAO> {

    public ReservaQuartoService() {
        super(new ReservaQuartoDAO());
    }

    public List<ReservaQuarto> findByReservaId(int reservaId) throws SQLException {
        return dao.findByReservaId(reservaId);
    }
}