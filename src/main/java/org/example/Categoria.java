package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
public class Categoria extends Base{
    private String nombre;
    private String descripcion;
    private List<Producto> productos;

    public void agregarProductos(Producto prod){
        this.productos.add(prod);
    }
}
