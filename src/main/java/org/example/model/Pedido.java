package org.example.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.example.enums.Estado;
import org.example.enums.FormaPago;
import org.example.interfaces.Calculable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    @Builder.Default
    private List<DetallePedido> detallePedidos = new ArrayList<DetallePedido>();

    public Pedido(LocalDate fecha, Estado estado, FormaPago formaPago) {
        this.fecha = fecha;
        this.estado = estado;
        this.formaPago = formaPago;
    }

    public void addDetallePedido(int cant, Producto prod) {
        DetallePedido detalleP = new DetallePedido(cant, prod);
        detallePedidos.add(detalleP);
    }

    public void calcularTotal() {
        Double total = 0.0;
        for (DetallePedido det : detallePedidos) {
            total += det.getSubtotal();
        }
        System.out.printf("Total: $ %.2f%n", total);
    }

    ;
}
