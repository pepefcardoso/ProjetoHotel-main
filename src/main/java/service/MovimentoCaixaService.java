package service;

import java.util.List;

import model.MovimentoCaixa;
import model.DAO.MovimentoCaixaDAO;

public class MovimentoCaixaService extends AbstractService<MovimentoCaixa, MovimentoCaixaDAO> {

    public MovimentoCaixaService() {
        super(new MovimentoCaixaDAO());
    }

    public List<MovimentoCaixa> findByCaixaId(int caixaId) {
        return dao.findByCaixaId(caixaId);
    }
}
