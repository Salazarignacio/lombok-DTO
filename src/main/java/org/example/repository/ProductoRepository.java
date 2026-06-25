package org.example.repository;

import jakarta.persistence.EntityManager;
import org.example.model.Producto;


import java.util.List;

public class ProductoRepository extends BaseRepository<Producto> {

    public ProductoRepository() {
        super(Producto.class);
    }

    public List<Producto> buscarPorCategoria(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "select p from Producto p where p.categoria.id = :id and p.eliminado = false";
            return em.createQuery(jpql, Producto.class).setParameter("id", id).getResultList();
        } finally {
            em.close();
        }
    }
}
