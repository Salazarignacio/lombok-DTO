package org.example.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
public class Producto extends Base {
    private String nombre;
    private Double precio;
    private String descripcion;
    private int stock;
    private String imagen;
    private Boolean disponible;
}
