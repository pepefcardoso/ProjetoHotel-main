package service;

import java.util.List;

import model.AlocacaoVaga;
import model.DAO.AlocacaoVagaDAO;

public class AlocacaoVagaService extends AbstractService<AlocacaoVaga, AlocacaoVagaDAO> {

    public AlocacaoVagaService() {
        super(new AlocacaoVagaDAO());
    }

    public List<AlocacaoVaga> findByCheckId(int checkId) {
    return new AlocacaoVagaDAO().findByCheckId(checkId);
}
}