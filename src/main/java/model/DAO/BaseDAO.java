package model.DAO;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public abstract class BaseDAO<T> implements InterfaceDAO<T> {

    private final Class<T> classeEntidade;

    public BaseDAO(Class<T> classeEntidade) {
        this.classeEntidade = classeEntidade;
    }

    @Override
    public void Create(T objeto) {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            em.getTransaction().begin();
            em.persist(objeto);
            em.getTransaction().commit();
        }
    }

    @Override
    public T Retrieve(int id) {
        T objeto;
        try (EntityManager em = JPAUtil.getEntityManager()) {
            objeto = em.find(classeEntidade, id);
        }
        return objeto;
    }

    @Override
    public List<T> Retrieve(String atributo, String valor) {
        List<T> resultado;
        try (EntityManager em = JPAUtil.getEntityManager()) {
            String jpql = "SELECT o FROM " + classeEntidade.getSimpleName() + " o WHERE o." + atributo + " LIKE :valor";
            TypedQuery<T> query = em.createQuery(jpql, classeEntidade);
            query.setParameter("valor", "%" + valor + "%");
            resultado = query.getResultList();
        }
        return resultado;
    }

    @Override
    public void Update(T objeto) {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            em.getTransaction().begin();
            em.merge(objeto);
            em.getTransaction().commit();
        }
    }

    @Override
    public void Delete(T objeto) {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            em.getTransaction().begin();
            T objetoGerenciado = em.contains(objeto) ? objeto : em.merge(objeto);
            em.remove(objetoGerenciado);
            em.getTransaction().commit();
        }
    }

    @Override
    public void AtivarInativar(int id, boolean ativar) {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            em.getTransaction().begin();
            String status = ativar ? "A" : "I";
            String jpql = "UPDATE " + classeEntidade.getSimpleName() + " o SET o.status = :status WHERE o.id = :id";
            em.createQuery(jpql)
                    .setParameter("status", status.charAt(0))
                    .setParameter("id", id)
                    .executeUpdate();
            em.getTransaction().commit();
        }
    }
}
