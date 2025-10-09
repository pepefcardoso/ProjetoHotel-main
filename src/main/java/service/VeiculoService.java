package service;

import model.DAO.VeiculoDAO;
import model.Veiculo;

public class VeiculoService extends AbstractService<Veiculo, VeiculoDAO> {

    public VeiculoService() {
        super(new VeiculoDAO());
    }
}
