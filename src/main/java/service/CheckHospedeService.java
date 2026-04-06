package service;

import model.DAO.CheckHospedeDAO;
import model.CheckHospede;

public class CheckHospedeService extends AbstractService<CheckHospede, CheckHospedeDAO> {

    public CheckHospedeService() {
        super(new CheckHospedeDAO());
    }
}