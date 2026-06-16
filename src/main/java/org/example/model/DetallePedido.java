package org.example.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "DETALLES_PEDIDO")
public class DetallePedido extends Base {
    private int cantidad;
    @Transient
    private Double subtotal;
    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @Setter(AccessLevel.PACKAGE)
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    public DetallePedido(int cantidad, Producto producto) {
        super();
        this.setCreatedAt(java.time.LocalDateTime.now());
        this.producto = producto;
        this.setCantidad(cantidad);
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;

        if (Objects.nonNull(this.producto)) {
            this.subtotal = cantidad * this.producto.getPrecio();
        } else {
            this.subtotal = 0.0;
        }
    }

}
