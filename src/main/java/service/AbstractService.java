package service;

import java.sql.SQLException;
import java.util.List;

import model.DAO.InterfaceDAO;

public abstract class AbstractService<T, D extends InterfaceDAO<T>> implements InterfaceService<T> {

    protected final D dao;

    protected AbstractService(D dao) {
        this.dao = dao;
    }

    @Override
    public void Criar(T objeto) throws SQLException {
        dao.Create(objeto);
    }

    @Override
    public T Carregar(int id) throws SQLException {
        return dao.Retrieve(id);
    }

    @Override
    public List<T> Carregar(String atributo, String valor) throws SQLException {
        return dao.Retrieve(atributo, valor);
    }

    @Override
    public void Atualizar(T objeto) throws SQLException {
        dao.Update(objeto);
    }

    @Override
    public void Apagar(T objeto) throws SQLException {
        dao.Delete(objeto);
    }

    @Override
    public void AtivarInativar(int id, boolean ativar) throws SQLException {
        dao.AtivarInativar(id, ativar);
    }

    protected D getDao() {
        return dao;
    }
}
