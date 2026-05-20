package org.example.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
public class Categoria extends Base {
    private String nombre;
    private String descripcion;
    @Builder.Default
    private List<Producto> productos = new ArrayList<>();

    public void agregarProducto(Producto prod) {
        this.productos.add(prod);
    }
}
