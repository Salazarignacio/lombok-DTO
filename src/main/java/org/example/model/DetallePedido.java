package org.example.model;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
public class DetallePedido extends Base {
    private int cantidad;

    private Double subtotal;

    private Producto producto;

    public DetallePedido(int cantidad, Producto producto) {
        super();
        this.cantidad = cantidad;
        this.producto = producto;
        this.subtotal = cantidad * producto.getPrecio();
    }

}
