package service;

import model.DAO.FornecedorDAO;
import model.Fornecedor;

public class FornecedorService extends AbstractService<Fornecedor, FornecedorDAO> {

    public FornecedorService() {
        super(new FornecedorDAO());
    }
}
