package model.DAO;

import model.Caixa;

public class CaixaDAO extends BaseDAO<Caixa> {

    public CaixaDAO() {
        super(Caixa.class);
    }

    public boolean isCaixaAberto() {
        String jpql = "SELECT COUNT(c) FROM Caixa c WHERE c.status = 'A'";
        try {
            Long count = em.createQuery(jpql, Long.class).getSingleResult();
            return count != null && count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}