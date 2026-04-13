package model.DAO;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import model.CopaQuarto;

public class CopaQuartoDAO extends BaseDAO<CopaQuarto> {

    public CopaQuartoDAO() {
        super(CopaQuarto.class);
    }

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

    public BigDecimal buscarTotalConsumoByQuartos(List<Integer> quartoIds) {
        if (quartoIds == null || quartoIds.isEmpty()) return BigDecimal.ZERO;
        String jpql =
            "SELECT COALESCE(SUM(c.quantidade * c.produto.valor), 0) " +
            "FROM CopaQuarto c WHERE c.quarto.id IN :quartoIds";
        try {
            BigDecimal total = em.createQuery(jpql, BigDecimal.class)
                                 .setParameter("quartoIds", quartoIds)
                                 .getSingleResult();
            return total != null ? total : BigDecimal.ZERO;
        } catch (Exception e) {
            e.printStackTrace();
            return BigDecimal.ZERO;
        }
    }

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

    public List<CopaQuarto> findByCaixaId(int caixaId) {
        String jpql =
            "SELECT DISTINCT c FROM CopaQuarto c " +
            "WHERE c.quarto.id IN (" +
            "  SELECT cq.quarto.id FROM CheckQuarto cq " +
            "  WHERE cq.check.id IN (" +
            "    SELECT r.check.id FROM Receber r " +
            "    WHERE r.id IN (" +
            "      SELECT m.receber.id FROM MovimentoCaixa m WHERE m.caixa.id = :caixaId" +
            "    )" +
            "  )" +
            ") ORDER BY c.dataHoraPedido";
        try {
            return em.createQuery(jpql, CopaQuarto.class)
                     .setParameter("caixaId", caixaId)
                     .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}