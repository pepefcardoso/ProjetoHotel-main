package service;

import model.Caixa;
import model.DAO.CaixaDAO;

public class CaixaService extends AbstractService<Caixa, CaixaDAO> {

    public CaixaService() {
        super(new CaixaDAO());
    }

    public boolean isCaixaAberto() {
        // Instancia o DAO para acessar a consulta customizada
        return new CaixaDAO().isCaixaAberto(); 
    }
}