package org.example.repository;

import jakarta.persistence.EntityManager;
import lombok.experimental.SuperBuilder;
import org.example.model.Categoria;
import org.example.model.Producto;


public class CategoriaRepository extends BaseRepository<Categoria> {
    public CategoriaRepository() {
        super(Categoria.class);
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
