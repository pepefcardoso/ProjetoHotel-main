package service;

import model.DAO.ReceberDAO;
import model.Receber;

public class ReceberService extends AbstractService<Receber, ReceberDAO> {

    public ReceberService() {
        super(new ReceberDAO());
    }
}