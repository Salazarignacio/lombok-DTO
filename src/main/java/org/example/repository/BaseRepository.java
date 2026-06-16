package org.example.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class BaseRepository<T> {
    private EntityManagerFactory emf;
    private final Class<T> clazz;

    public T guardar(T entity) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            T resultado = em.merge(entity);
            em.getTransaction().commit();
            return resultado;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    /*2. buscarPorId(Long id): retorna Optional<T> usando find(). Retorna Optional.empty() si no existe.*/
    public Optional<T> buscar(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            T resultado = em.find(clazz, id);
            return Optional.ofNullable(resultado);
        } finally {
            em.close();
        }
    }

    /*3. listarActivos(): retorna List<T> con los registros cuyo campo eliminado = false. Usa JPQL.*/
    public List<T> buscarTodos() {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT e FROM " + clazz.getSimpleName() + " e WHERE e.eliminado = false";
            return em.createQuery(jpql, clazz).getResultList();
        } finally {
            em.close();
        }
    }

    /*4. eliminarLogico(Long id): busca la entidad por ID, establece eliminado = true y persiste el cambio. Retorna boolean indicando si encontro el registro.*/
    public boolean eliminarLogico(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            String jpql = "UPDATE " + clazz.getSimpleName() + " x SET  x.eliminado = true  WHERE x.id = :id";
            int rowsChanged = em.createQuery(jpql, clazz).setParameter("id", id).executeUpdate();
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
