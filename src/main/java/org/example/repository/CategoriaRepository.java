package org.example.repository;

import jakarta.persistence.EntityManager;
import lombok.experimental.SuperBuilder;
import org.example.model.Categoria;
import org.example.model.Producto;


public class CategoriaRepository extends BaseRepository<Categoria> {
    public CategoriaRepository() {
        super(Categoria.class);
    }

    public java.util.List<Producto> buscarProductosPorCategoria(Long categoriaId) {
        EntityManager em = emf.createEntityManager();
        try {
            // Consulta JPQL: retorna los productos activos de una categoría.
            // Como la relación es unidireccional y Categoria es la dueña, se navega desde Categoria hacia
            // su colección c.productos mediante JOIN.
            // Se filtra por el id de la categoría (parámetro nombrado :catId) y
            // por p.eliminado = false para excluir las bajas lógicas.
            String jpql = "SELECT p FROM Categoria c JOIN c.productos p " + "WHERE c.id = :catId AND p.eliminado = false";
            return em.createQuery(jpql, Producto.class)
                    .setParameter("catId", categoriaId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public void agregarProductoACategoria(Long categoriaId, Producto producto) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            Categoria categoria = em.find(Categoria.class, categoriaId);
            producto = em.merge(producto);

            categoria.getProductos().add(producto);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
