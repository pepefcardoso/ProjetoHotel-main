package model.DAO;

import java.util.List;

import model.CheckHospede;

public class CheckHospedeDAO extends BaseDAO<CheckHospede> {

    public CheckHospedeDAO() {
        super(CheckHospede.class);
    }

    public List<CheckHospede> findByCheckId(int checkId) {
    String jpql = "SELECT c FROM CheckHospede c WHERE c.check.id = :checkId";
    return em.createQuery(jpql, CheckHospede.class)
             .setParameter("checkId", checkId)
             .getResultList();
}
}