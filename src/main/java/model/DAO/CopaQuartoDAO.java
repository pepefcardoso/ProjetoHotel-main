package model.DAO;

import java.math.BigDecimal;

import model.CopaQuarto;

public class CopaQuartoDAO extends BaseDAO<CopaQuarto> {

    public CopaQuartoDAO() {
        super(CopaQuarto.class);
    }

    // Soma o valor total dos consumos vinculados a um check-in específico
    public BigDecimal buscarTotalConsumo(int checkId) {
        // Ajuste 'c.valorTotal' e 'c.check.id' para os nomes reais dos atributos na sua entidade CopaQuarto
        String jpql = "SELECT SUM(c.valorTotal) FROM CopaQuarto c WHERE c.check.id = :checkId";
        try {
            BigDecimal total = em.createQuery(jpql, BigDecimal.class)
                                 .setParameter("checkId", checkId)
                                 .getSingleResult();
            
            return total != null ? total : BigDecimal.ZERO;
        } catch (Exception e) {
            e.printStackTrace();
            return BigDecimal.ZERO;
        }
    }
}