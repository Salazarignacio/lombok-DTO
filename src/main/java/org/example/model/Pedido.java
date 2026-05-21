package org.example.model;

import lombok.*;
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
    private Usuario usuario;
    @Builder.Default
    private List<DetallePedido> detallePedidos = new ArrayList<DetallePedido>();


    public void addDetallePedido(int cant, Producto prod) {
        DetallePedido detalleP = new DetallePedido(cant, prod);
        detallePedidos.add(detalleP);
    }

    public DetallePedido findDetallePedidoByProducto(Producto prod) {
        for (DetallePedido det : detallePedidos) {
            if (prod.equals(det.getProducto())) {
                System.out.printf("Produto encontrado: %s\n", prod);
                return det;
            }
        }
        System.out.println("Produto no encontrado");
        return null;
    }

    public void deleteDetallePedidoByProducto(Producto prod) {
        DetallePedido prodEncontrado = findDetallePedidoByProducto(prod);
        if (prodEncontrado != null) {
            for (DetallePedido det : detallePedidos) {
                if (prodEncontrado.getProducto().equals(det.getProducto()))
                    detallePedidos.remove(prodEncontrado);
                return;
            }
        }
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
