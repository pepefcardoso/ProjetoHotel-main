package service;

import java.math.BigDecimal;
import java.util.List;

import model.CopaQuarto;
import model.DAO.CopaQuartoDAO;

public class CopaQuartoService extends AbstractService<CopaQuarto, CopaQuartoDAO> {

    public CopaQuartoService() {
        super(new CopaQuartoDAO());
    }

    /** Soma o valor total dos consumos vinculados a um check-in (via quartos). */
    public BigDecimal buscarTotalConsumo(int checkId) {
        return this.dao.buscarTotalConsumo(checkId);
    }

    /** Lista todos os pedidos de copa de um quarto específico. */
    public List<CopaQuarto> findByQuartoId(int quartoId) {
        return this.dao.findByQuartoId(quartoId);
    }

    /** Lista pedidos de copa de todos os quartos associados a um check. */
    public List<CopaQuarto> findByCheckId(int checkId) {
        return this.dao.findByCheckId(checkId);
    }
}
