package org.example.repository;

import jakarta.persistence.EntityManager;
import org.example.model.Producto;


import java.util.List;

public class ProductoRepository extends BaseRepository<Producto> {
    /*Extiende BaseRepository<Producto>.
     Agrega buscarPorCategoria(Long id) con JPQL.*/

    public ProductoRepository(Class<Producto> clazz) {
        super(clazz);
    }

    /*buscarPorCategoria(Long categoriaId): retorna List<Producto> con los productos activos
    de esa categoria. La consulta debe escribirse en JPQL con un parametro nombrado, filtrar
    por eliminado = false, y retornar un List<Producto>. */
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
