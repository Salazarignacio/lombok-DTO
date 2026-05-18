package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.example.enums.Estado;
import org.example.enums.FormaPago;
import org.example.interfaces.Calculable;

import java.time.LocalDate;
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
    private List<DetallePedido> detallePedidos = new ArrayList<DetallePedido>();

    public Pedido(LocalDate fecha, Estado estado, FormaPago formaPago) {
        this.fecha = fecha;
        this.estado = estado;
        this.formaPago = formaPago;

    }

    public void addDetallePedido(int cant, Producto prod) {
        detallePedidos.add(new DetallePedido(cant, prod));
    }

    public void CalcularTotal() {
        Double total = 0.0;
        for (DetallePedido det : detallePedidos) {
            total += det.getSubtotal();
        }
        System.out.printf("Total: R$ %.2f%n", total);
    }

    ;
}
