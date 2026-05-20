package org.example;

import org.example.enums.Estado;
import org.example.enums.FormaPago;
import org.example.enums.Rol;
import org.example.model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
         Usuario user1 = Usuario.builder()
                .id(2)
                .nombre("Ignacio")
                .apellido("Salazar")
                .email("ignacio@example.com")
                .eliminado(false)
                .celular("334466")
                .rol(Rol.ADMIN)
                .build();
        Categoria cat1 = Categoria.builder()
                .id(2)
                .createdAt(LocalDateTime.now())
                .eliminado(false)
                .nombre("Lacteos")
                .descripcion("Productos lacteos")
                .build();

        Producto prod1 = Producto.builder()
                .id(2)
                .createdAt(LocalDateTime.now())
                .eliminado(false)
                .nombre("Leche")
                .precio(2200.00)
                .descripcion("lechita")
                .stock(30)
                .disponible(true)
                .imagen("lechita.jpg")
                .build();
        Producto prod2 = Producto.builder()
                .id(3)
                .createdAt(LocalDateTime.now())
                .eliminado(false)
                .nombre("Pan")
                .precio(3350.00)
                .descripcion("Pan pan")
                .stock(50)
                .disponible(true)
                .imagen("pan.jpg")
                .build();

        cat1.agregarProducto(prod1);
        cat1.agregarProducto(prod2);

        Pedido pedido1 = Pedido.builder()
                .id(1)
                .createdAt(LocalDateTime.now())
                .fecha(LocalDate.now())
                .estado(Estado.PENDIENTE)
                .formaPago(FormaPago.EFECTIVO)
                .build();


        System.out.printf("Holaaaaaaaaa");
       pedido1.addDetallePedido(2, prod2);
        pedido1.addDetallePedido(1, prod1);
        pedido1.calcularTotal();


    }
}
