package model.DAO;

import java.util.List;

public interface InterfaceDAO<T> {

    void Create(T objeto);

    T Retrieve(int id);

    List<T> Retrieve(String atributo, String valor);

    void Update(T objeto);

    void Delete(T objeto);

    void AtivarInativar(int id, boolean ativar);
}
