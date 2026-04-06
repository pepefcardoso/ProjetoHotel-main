package model.DAO;

import java.util.Collections;
import java.util.List;

import jakarta.persistence.EntityManager;
import model.ReservaQuarto;

public class ReservaQuartoDAO extends BaseDAO<ReservaQuarto> {

    public ReservaQuartoDAO() {
        super(ReservaQuarto.class);
    }

    public List<ReservaQuarto> findByReservaId(int reservaId) {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            return em.createQuery(
                    "SELECT rq FROM ReservaQuarto rq WHERE rq.reserva.id = :reservaId",
                    ReservaQuarto.class)
                    .setParameter("reservaId", reservaId)
                    .getResultList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}