package service;

import model.DAO.CheckQuartoDAO;
import model.CheckQuarto;

public class CheckQuartoService extends AbstractService<CheckQuarto, CheckQuartoDAO> {

    public CheckQuartoService() {
        super(new CheckQuartoDAO());
    }
}