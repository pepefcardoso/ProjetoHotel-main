package model.DAO;

import model.Produto;

public class ProdutoDAO extends BaseDAO<Produto> {

    public ProdutoDAO() {
        super(Produto.class);
    }
}