package service;

import java.util.List;

import model.CheckQuarto;
import model.DAO.CheckQuartoDAO;

public class CheckQuartoService extends AbstractService<CheckQuarto, CheckQuartoDAO> {

    public CheckQuartoService() {
        super(new CheckQuartoDAO());
    }

    public List<CheckQuarto> findByCheckId(int checkId) {
    return new CheckQuartoDAO().findByCheckId(checkId);
}
}