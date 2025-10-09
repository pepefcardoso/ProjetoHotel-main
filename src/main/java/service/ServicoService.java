package service;

import model.DAO.ServicoDAO;
import model.Servico;

public class ServicoService extends AbstractService<Servico, ServicoDAO> {

    public ServicoService() {
        super(new ServicoDAO());
    }
}
