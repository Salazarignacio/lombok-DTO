package org.example.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.example.model.Base;
import org.example.util.JPAUtil;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class BaseRepository<T> {
    protected final EntityManagerFactory emf = JPAUtil.getEmf();
    private final Class<T> clazz;

    public T guardar(T entity) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            T resultado = null;
            if (entity instanceof Base baseEntity) {
                Long id = baseEntity.getId();
                if (id == null) {
                    em.persist(entity);
                    resultado = entity;
                } else {
                    resultado = em.merge(entity);
                }
            }
            em.getTransaction().commit();
            return resultado;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public Optional<T> buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            T resultado = em.find(clazz, id);
            return Optional.ofNullable(resultado);
        } finally {
            em.close();
        }
    }

    public List<T> listarActivos() {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT e FROM " + clazz.getSimpleName() + " e WHERE e.eliminado = false";
            return em.createQuery(jpql, clazz).getResultList();
        } finally {
            em.close();
        }
    }

    public boolean eliminarLogico(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            String jpql = "UPDATE " + clazz.getSimpleName() + " x SET  x.eliminado = true  WHERE x.id = :id";
            int rowsChanged = em.createQuery(jpql).setParameter("id", id).executeUpdate();
            em.getTransaction().commit();
            return rowsChanged > 0;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException(e);
        } finally {
            em.close();
        }
    }

}
