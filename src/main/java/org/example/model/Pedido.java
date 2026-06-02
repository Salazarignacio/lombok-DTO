package org.example.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.example.enums.Estado;
import org.example.enums.FormaPago;
import org.example.interfaces.Calculable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Pedido extends Base implements Calculable {
    private LocalDate fecha;
    private Estado estado;
    private Double total;
    private FormaPago formaPago;
    private Usuario usuario;
    @Builder.Default
    private List<DetallePedido> detallePedidos = new ArrayList<DetallePedido>();


    public void addDetallePedido(int cant, Producto prod) {
        detallePedidos.add(new DetallePedido(cant, prod));
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

    public Double calcularTotal() {
        return detallePedidos.stream()
                .mapToDouble(DetallePedido::getSubtotal)
                .sum();
    }

    ;
}
