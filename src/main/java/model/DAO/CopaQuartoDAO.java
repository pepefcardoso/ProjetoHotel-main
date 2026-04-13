package model.DAO;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import model.CopaQuarto;

public class CopaQuartoDAO extends BaseDAO<CopaQuarto> {

    public CopaQuartoDAO() {
        super(CopaQuarto.class);
    }

    /**
     * Calcula o total de consumo de copa para todos os quartos de um check.
     * Faz o join via check_quarto para encontrar os quartos do check,
     * então soma quantidade * produto.valor de cada pedido de copa.
     */
    public BigDecimal buscarTotalConsumo(int checkId) {
        String jpql =
            "SELECT COALESCE(SUM(c.quantidade * c.produto.valor), 0) " +
            "FROM CopaQuarto c " +
            "WHERE c.quarto.id IN (" +
            "   SELECT cq.quarto.id FROM CheckQuarto cq WHERE cq.check.id = :checkId" +
            ")";
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

    /**
     * Lista todos os pedidos de copa de um quarto específico.
     */
    public List<CopaQuarto> findByQuartoId(int quartoId) {
        try {
            return em.createQuery(
                    "SELECT c FROM CopaQuarto c WHERE c.quarto.id = :quartoId ORDER BY c.dataHoraPedido DESC",
                    CopaQuarto.class)
                    .setParameter("quartoId", quartoId)
                    .getResultList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    /**
     * Lista pedidos de copa de todos os quartos de um check.
     */
    public List<CopaQuarto> findByCheckId(int checkId) {
        String jpql =
            "SELECT c FROM CopaQuarto c " +
            "WHERE c.quarto.id IN (" +
            "   SELECT cq.quarto.id FROM CheckQuarto cq WHERE cq.check.id = :checkId" +
            ") ORDER BY c.dataHoraPedido DESC";
        try {
            return em.createQuery(jpql, CopaQuarto.class)
                     .setParameter("checkId", checkId)
                     .getResultList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}
