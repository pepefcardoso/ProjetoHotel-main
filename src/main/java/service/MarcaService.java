package service;

import model.DAO.MarcaDAO;
import model.Marca;

public class MarcaService extends AbstractService<Marca, MarcaDAO> {

    public MarcaService() {
        super(new MarcaDAO());
    }
}
