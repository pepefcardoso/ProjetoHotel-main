package service;

import model.DAO.AlocacaoVagaDAO;
import model.AlocacaoVaga;

public class AlocacaoVagaService extends AbstractService<AlocacaoVaga, AlocacaoVagaDAO> {

    public AlocacaoVagaService() {
        super(new AlocacaoVagaDAO());
    }
}