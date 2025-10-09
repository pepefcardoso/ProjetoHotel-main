package service;

import model.DAO.HospedeDAO;
import model.Hospede;

public class HospedeService extends AbstractService<Hospede, HospedeDAO> {

    public HospedeService() {
        super(new HospedeDAO());
    }
}
