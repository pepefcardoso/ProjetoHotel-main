package service;

import java.util.List;

import model.CheckHospede;
import model.DAO.CheckHospedeDAO;

public class CheckHospedeService extends AbstractService<CheckHospede, CheckHospedeDAO> {

    public CheckHospedeService() {
        super(new CheckHospedeDAO());
    }

    public List<CheckHospede> findByCheckId(int checkId) {
    return new CheckHospedeDAO().findByCheckId(checkId);
}
}