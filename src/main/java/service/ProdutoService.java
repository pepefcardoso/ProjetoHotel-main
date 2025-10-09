package service;

import model.DAO.ProdutoDAO;
import model.Produto;

public class ProdutoService extends AbstractService<Produto, ProdutoDAO> {

    public ProdutoService() {
        super(new ProdutoDAO());
    }
}
