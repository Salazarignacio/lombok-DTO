package org.example.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.example.enums.Estado;
import org.example.enums.FormaPago;
import org.example.interfaces.Calculable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuperBuilder
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "PEDIDOS")
public class Pedido extends Base implements Calculable {
    private LocalDate fecha;
    private Estado estado;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Transient
    private Double total;
    private FormaPago formaPago;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "pedido_id")
    private Set<DetallePedido> detallePedidos = new HashSet<DetallePedido>();


    public void addDetallePedido(int cant, Producto prod) {
        DetallePedido det = new DetallePedido(cant, prod);
        detallePedidos.add(det);
    }

    public java.util.Optional<DetallePedido> findDetallePedidoByProducto(Producto prod) {
        return detallePedidos.stream()
                .filter(det -> det.getProducto().equals(prod))
                .findFirst();
    }

    public void deleteDetallePedidoByProducto(Producto prod) {
        findDetallePedidoByProducto(prod)
                .ifPresent(detallePedidos::remove);
    }

    public void calcularTotal() {
        this.total = detallePedidos.stream()
                .map(det -> det.getSubtotal()).reduce(0.0, (a, b) -> a + b);
    }
}
