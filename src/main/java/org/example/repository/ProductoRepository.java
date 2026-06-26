package org.example.repository;

import jakarta.persistence.EntityManager;
import org.example.model.Producto;


import java.util.List;

public class ProductoRepository extends BaseRepository<Producto> {

    public ProductoRepository() {
        super(Producto.class);
    }

    public List<Producto> buscarPorCategoria(Long categoria_id) {
        EntityManager em = emf.createEntityManager();
        try {
            // Consulta JPQL: retorna los productos activos de una categoría.
            // Como la relación es unidireccional y Categoria es la dueña, se // navega desde Categoria hacia su colección c.productos mediante JOIN.
            // Se filtra por el id de la categoría (parámetro nombrado :catId) y
            // por p.eliminado = false para excluir las bajas lógicas.
            String jpql = "SELECT p FROM Categoria c JOIN c.productos p " + "WHERE c.id = :catId AND p.eliminado = false";
            return em.createQuery(jpql, Producto.class).setParameter("catId", categoria_id).getResultList();

        } finally {
            em.close();
        }
    }
}
