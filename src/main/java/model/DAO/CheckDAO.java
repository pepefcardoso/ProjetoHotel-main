package model.DAO;

import java.util.List;

import jakarta.persistence.EntityManager;
import model.Check;

public class CheckDAO extends BaseDAO<Check> {

    public CheckDAO() {
        super(Check.class);
    }

    public List<Check> findByStatus(char status) {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            return em.createQuery(
                    "SELECT c FROM Check c WHERE c.status = :status",
                    Check.class)
                    .setParameter("status", status)
                    .getResultList();
        }
    }
}