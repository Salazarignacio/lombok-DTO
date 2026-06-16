package org.example.repository;

import lombok.experimental.SuperBuilder;
import org.example.model.Categoria;


public class CategoriaRepository extends BaseRepository<Categoria> {
    /*Extiende BaseRepository<Categoria>
    . Sin metodos adicionales.*/
    public CategoriaRepository() {
        super(Categoria.class);
    }
}
