package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
public class Categoria extends Base{
    private String nombre;
    private String descripcion;
    private List<Producto> productos;

    public void agregarProductos(Producto prod){
        this.productos.add(prod);
    }
}
