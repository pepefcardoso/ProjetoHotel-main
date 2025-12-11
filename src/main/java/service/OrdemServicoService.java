package service;

import model.DAO.OrdemServicoDAO;
import model.OrdemServico;

public class OrdemServicoService extends AbstractService<OrdemServico, OrdemServicoDAO> {

    public OrdemServicoService() {
        super(new OrdemServicoDAO());
    }
}
