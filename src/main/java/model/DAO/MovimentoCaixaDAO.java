package model.DAO;

import java.util.Collections;
import java.util.List;

import model.MovimentoCaixa;

public class MovimentoCaixaDAO extends BaseDAO<MovimentoCaixa> {

    public MovimentoCaixaDAO() {
        super(MovimentoCaixa.class);
    }

    public List<MovimentoCaixa> findByCaixaId(int caixaId) {
        try {
            return em.createQuery(
                    "SELECT m FROM MovimentoCaixa m WHERE m.caixa.id = :caixaId ORDER BY m.dataHoraMovimento",
                    MovimentoCaixa.class)
                    .setParameter("caixaId", caixaId)
                    .getResultList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}
