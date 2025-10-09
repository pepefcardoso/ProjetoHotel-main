package service;

import model.DAO.QuartoDAO;
import model.Quarto;

public class QuartoService extends AbstractService<Quarto, QuartoDAO> {
    public QuartoService() {
        super(new QuartoDAO());
    }
}