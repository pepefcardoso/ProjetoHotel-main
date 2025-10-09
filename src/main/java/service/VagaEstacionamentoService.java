package service;

import model.DAO.VagaEstacionamentoDAO;
import model.VagaEstacionamento;

public class VagaEstacionamentoService extends AbstractService<VagaEstacionamento, VagaEstacionamentoDAO> {

    public VagaEstacionamentoService() {
        super(new VagaEstacionamentoDAO());
    }
}
