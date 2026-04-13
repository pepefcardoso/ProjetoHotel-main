package service;

import java.math.BigDecimal;
import java.util.List;

import model.CopaQuarto;
import model.DAO.CopaQuartoDAO;

public class CopaQuartoService extends AbstractService<CopaQuarto, CopaQuartoDAO> {

    public CopaQuartoService() {
        super(new CopaQuartoDAO());
    }

    public BigDecimal buscarTotalConsumo(int checkId) {
        return new CopaQuartoDAO().buscarTotalConsumo(checkId);
    }

    public BigDecimal buscarTotalConsumoByQuartos(List<Integer> quartoIds) {
        return new CopaQuartoDAO().buscarTotalConsumoByQuartos(quartoIds);
    }

    public List<CopaQuarto> findByQuartoId(int quartoId) {
        return this.dao.findByQuartoId(quartoId);
    }

    public List<CopaQuarto> findByCheckId(int checkId) {
        return this.dao.findByCheckId(checkId);
    }
}