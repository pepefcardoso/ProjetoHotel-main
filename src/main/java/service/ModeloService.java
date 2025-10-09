package service;

import model.DAO.ModeloDAO;
import model.Modelo;

public class ModeloService extends AbstractService<Modelo, ModeloDAO> {

    public ModeloService() {
        super(new ModeloDAO());
    }
}
