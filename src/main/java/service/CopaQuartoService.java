package service;

import java.math.BigDecimal;

import model.CopaQuarto;
import model.DAO.CopaQuartoDAO;

public class CopaQuartoService extends AbstractService<CopaQuarto, CopaQuartoDAO> {

    public CopaQuartoService() {
        super(new CopaQuartoDAO());
    }

    public BigDecimal buscarTotalConsumo(int checkId) {
        // Instancia o DAO para acessar a consulta customizada
        return new CopaQuartoDAO().buscarTotalConsumo(checkId);
    }
}