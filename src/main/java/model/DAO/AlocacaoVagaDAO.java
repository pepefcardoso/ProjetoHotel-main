package model.DAO;

import java.util.List;

import model.AlocacaoVaga;

public class AlocacaoVagaDAO extends BaseDAO<AlocacaoVaga> {

    public AlocacaoVagaDAO() {
        super(AlocacaoVaga.class);
    }

    public List<AlocacaoVaga> findByCheckId(int checkId) {
    String jpql = "SELECT a FROM AlocacaoVaga a WHERE a.check.id = :checkId";
    return em.createQuery(jpql, AlocacaoVaga.class)
             .setParameter("checkId", checkId)
             .getResultList();
}
}