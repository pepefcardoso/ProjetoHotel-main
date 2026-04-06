package model.DAO;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public abstract class BaseDAO<T> implements InterfaceDAO<T> {

    protected final EntityManager em;
    private final Class<T> classeEntidade;

    public BaseDAO(Class<T> classeEntidade) {
        this.em = JPAUtil.getEntityManager();
        this.classeEntidade = classeEntidade;
    }

    @Override
    public void Create(T objeto) {
        em.getTransaction().begin();
        try {
            em.persist(objeto);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }
    }

    @Override
    public T Retrieve(int id) {
        return em.find(classeEntidade, id);
    }

    @Override
    public List<T> Retrieve(String atributo, String valor) {
        String jpql = "SELECT o FROM " + classeEntidade.getSimpleName()
                + " o WHERE o." + atributo + " LIKE :valor";
        TypedQuery<T> query = em.createQuery(jpql, classeEntidade);
        query.setParameter("valor", "%" + valor + "%");
        return query.getResultList();
    }

    @Override
    public void Update(T objeto) {
        em.getTransaction().begin();
        try {
            em.merge(objeto);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }
    }

    @Override
    public void Delete(T objeto) {
        em.getTransaction().begin();
        try {
            T objetoGerenciado = em.contains(objeto) ? objeto : em.merge(objeto);
            em.remove(objetoGerenciado);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }
    }

    @Override
    public void AtivarInativar(int id, boolean ativar) {
        char status = ativar ? 'A' : 'I';
        String jpql = "UPDATE " + classeEntidade.getSimpleName()
                + " o SET o.status = :status WHERE o.id = :id";

        em.getTransaction().begin();
        try {
            em.createQuery(jpql)
                    .setParameter("status", status)
                    .setParameter("id", id)
                    .executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }
    }

    public List<T> listarTodos() {
        String jpql = "SELECT o FROM " + classeEntidade.getSimpleName() + " o";
        return em.createQuery(jpql, classeEntidade).getResultList();
    }
}
