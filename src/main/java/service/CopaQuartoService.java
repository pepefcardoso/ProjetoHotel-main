package service;

import java.math.BigDecimal;

import model.CopaQuarto;
import model.DAO.CopaQuartoDAO;

public class CopaQuartoService extends AbstractService<CopaQuarto, CopaQuartoDAO> {

    public CopaQuartoService() {
        super(new CopaQuartoDAO());
    }

    public BigDecimal buscarTotalConsumo(int checkId) {
        return this.dao.buscarTotalConsumo(checkId);
    }
}