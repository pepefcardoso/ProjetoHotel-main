package service;

import model.DAO.FuncionarioDAO;
import model.Funcionario;

public class FuncionarioService extends AbstractService<Funcionario, FuncionarioDAO> {

    public FuncionarioService() {
        super(new FuncionarioDAO());
    }
}
