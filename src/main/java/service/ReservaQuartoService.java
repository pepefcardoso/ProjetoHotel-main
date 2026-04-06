package service;

import model.DAO.ReservaQuartoDAO;
import model.ReservaQuarto;

public class ReservaQuartoService extends AbstractService<ReservaQuarto, ReservaQuartoDAO> {

    public ReservaQuartoService() {
        super(new ReservaQuartoDAO());
    }
}