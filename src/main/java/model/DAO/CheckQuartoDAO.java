package model.DAO;

import java.util.List;

import model.CheckQuarto;

public class CheckQuartoDAO extends BaseDAO<CheckQuarto> {

    public CheckQuartoDAO() {
        super(CheckQuarto.class);
    }

    public List<CheckQuarto> findByCheckId(int checkId) {
    String jpql = "SELECT c FROM CheckQuarto c WHERE c.check.id = :checkId";
    return em.createQuery(jpql, CheckQuarto.class)
             .setParameter("checkId", checkId)
             .getResultList();
}
}