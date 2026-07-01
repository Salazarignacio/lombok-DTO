package org.example.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.example.model.enums.Estado;
import org.example.model.enums.FormaPago;
import org.example.interfaces.Calculable;

import java.time.LocalDate;
import java.util.HashSet;
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
    @Enumerated(EnumType.STRING)
    private Estado estado;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Double total;
    @Enumerated(EnumType.STRING)
    private FormaPago formaPago;

    @ManyToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private Usuario usuario;
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", referencedColumnName = "id")
    private Set<DetallePedido> detalles = new HashSet<DetallePedido>();


    public void addDetallePedido(int cant, Producto prod) {
        DetallePedido det = new DetallePedido(cant, prod);
        detalles.add(det);
    }

    public java.util.Optional<DetallePedido> findDetallePedidoByProducto(Producto prod) {
        return detalles.stream()
                .filter(det -> det.getProducto().equals(prod))
                .findFirst();
    }

    public void deleteDetallePedidoByProducto(Producto prod) {
        findDetallePedidoByProducto(prod)
                .ifPresent(detalles::remove);
    }

    public void calcularTotal() {
        System.out.println(this.total = detalles.stream()
                .map(det -> det.getSubtotal()).reduce(0.0, (a, b) -> a + b));
    }
}
