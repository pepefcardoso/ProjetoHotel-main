package model.DAO;

import model.Receber;

public class ReceberDAO extends BaseDAO<Receber> {

    public ReceberDAO() {
        super(Receber.class);
    }

    public Receber findByCheckId(int checkId) {
    String jpql = "SELECT r FROM Receber r WHERE r.check.id = :checkId";
    try {
        return em.createQuery(jpql, Receber.class)
                 .setParameter("checkId", checkId)
                 .getSingleResult();
    } catch (jakarta.persistence.NoResultException e) {
        return null;
    }
}
}