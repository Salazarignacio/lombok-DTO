package org.example;

import org.example.DTOs.UsuarioDTO;
import org.example.enums.Estado;
import org.example.enums.FormaPago;
import org.example.enums.Rol;
import org.example.model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.reflect.Array.set;

public class Main {
    public static void main(String[] args) {
        Usuario user1 = Usuario.builder()
                .id(2L)
                .createdAt(LocalDateTime.now())
                .nombre("Ignacio")
                .apellido("Salazar")
                .mail("ignacio@example.com")
                .contrasenia("ABC123")
                .eliminado(false)
                .celular("334466")
                .rol(Rol.ADMIN)
                .build();
        Usuario user2 = Usuario.builder()
                .id(3L)
                .createdAt(LocalDateTime.now())
                .nombre("Florencia")
                .apellido("Campora")
                .mail("florencia@example.com")
                .contrasenia("123ABC")
                .eliminado(false)
                .celular("99988")
                .rol(Rol.USUARIO)
                .build();
        Categoria cat1 = Categoria.builder()
                .id(2L)
                .createdAt(LocalDateTime.now())
                .eliminado(false)
                .nombre("Lacteos")
                .descripcion("Productos lacteos")
                .build();
        Categoria cat2 = Categoria.builder()
                .id(3L)
                .createdAt(LocalDateTime.now())
                .eliminado(false)
                .nombre("Almacén")
                .descripcion("Productos de uso diario como arroz, fideos, harina, azúcar, etc.")
                .build();
        Categoria cat3 = Categoria.builder()
                .id(4L)
                .createdAt(LocalDateTime.now())
                .eliminado(false)
                .nombre("Panadería")
                .descripcion("Productos panificados y derivados")
                .build();

        Producto prod1 = Producto.builder()
                .id(2L)
                .createdAt(LocalDateTime.now())
                .eliminado(false)
                .nombre("Leche")
                .precio(2200.00)
                .descripcion("Leche descremada")
                .stock(30)
                .disponible(true)
                .imagen("leche.jpg")
                .build();
        Producto prod2 = Producto.builder()
                .id(3L)
                .createdAt(LocalDateTime.now())
                .eliminado(false)
                .nombre("Pan")
                .precio(3350.00)
                .descripcion("Pan Blanco")
                .stock(3)
                .disponible(true)
                .imagen("pan.jpg")
                .build();
        Producto prod3 = Producto.builder()
                .id(4L)
                .createdAt(LocalDateTime.now())
                .eliminado(false)
                .nombre("Leche")
                .precio(1800.00)
                .descripcion("Leche Entera 1L")
                .stock(30)
                .disponible(true)
                .imagen("leche.jpg")
                .build();
        Producto prod4 = Producto.builder()
                .id(5L)
                .createdAt(LocalDateTime.now())
                .eliminado(false)
                .nombre("Queso")
                .precio(4200.00)
                .descripcion("Queso Cremoso")
                .stock(2)
                .disponible(true)
                .imagen("queso.jpg")
                .build();
        Producto prod5 = Producto.builder()
                .id(6L)
                .createdAt(LocalDateTime.now())
                .eliminado(false)
                .nombre("Arroz")
                .precio(2500.00)
                .descripcion("Arroz Largo Fino 1kg")
                .stock(40)
                .disponible(true)
                .imagen("arroz.jpg")
                .build();
        Producto prod6 = Producto.builder()
                .id(7L)
                .createdAt(LocalDateTime.now())
                .eliminado(false)
                .nombre("Fideos")
                .precio(2200.00)
                .descripcion("Fideos Spaghetti 500g")
                .stock(35)
                .disponible(true)
                .imagen("fideos.jpg")
                .build();
        Producto prod7 = Producto.builder()
                .id(8L)
                .createdAt(LocalDateTime.now())
                .eliminado(false)
                .nombre("Azúcar")
                .precio(2100.00)
                .descripcion("Azúcar Blanca 1kg")
                .stock(45)
                .disponible(true)
                .imagen("azucar.jpg")
                .build();
        Producto prod8 = Producto.builder()
                .id(9L)
                .createdAt(LocalDateTime.now())
                .eliminado(false)
                .nombre("Sal")
                .precio(900.00)
                .descripcion("Sal Fina 500g")
                .stock(60)
                .disponible(true)
                .imagen("sal.jpg")
                .build();
        Producto prod9 = Producto.builder()
                .id(10L)
                .createdAt(LocalDateTime.now())
                .eliminado(false)
                .nombre("Aceite")
                .precio(3800.00)
                .descripcion("Aceite de Girasol 900ml")
                .stock(25)
                .disponible(true)
                .imagen("aceite.jpg")
                .build();
        Producto prod10 = Producto.builder()
                .id(11L)
                .createdAt(LocalDateTime.now())
                .eliminado(false)
                .nombre("Harina")
                .precio(1900.00)
                .descripcion("Harina de Trigo 000 1kg")
                .stock(50)
                .disponible(false)
                .imagen("harina.jpg")
                .build();

        cat1.agregarProducto(prod1);
        cat1.agregarProducto(prod3);
        cat1.agregarProducto(prod4);

        cat2.agregarProducto(prod5);
        cat2.agregarProducto(prod6);
        cat2.agregarProducto(prod7);
        cat2.agregarProducto(prod8);
        cat2.agregarProducto(prod9);
        cat2.agregarProducto(prod10);

        cat3.agregarProducto(prod2);

        Pedido pedido1 = Pedido.builder()
                .id(1L)
                .createdAt(LocalDateTime.now())
                .fecha(LocalDate.now())
                .usuario(user1)
                .estado(Estado.PENDIENTE)
                .formaPago(FormaPago.EFECTIVO)
                .build();
        Pedido pedido2 = Pedido.builder()
                .id(2L)
                .createdAt(LocalDateTime.now())
                .fecha(LocalDate.now())
                .usuario(user1)
                .estado(Estado.PENDIENTE)
                .formaPago(FormaPago.EFECTIVO)
                .build();
        Pedido pedido3 = Pedido.builder()
                .id(3L)
                .createdAt(LocalDateTime.now())
                .fecha(LocalDate.now())
                .usuario(user2)
                .estado(Estado.TERMINADO)
                .formaPago(FormaPago.TARJETA)
                .build();

        pedido1.addDetallePedido(4, prod1);
        pedido1.addDetallePedido(6, prod3);

        pedido2.addDetallePedido(1, prod2);
        pedido2.addDetallePedido(2, prod4);

        pedido3.addDetallePedido(5, prod7);
        pedido3.addDetallePedido(2, prod5);

        UsuarioDTO userDTO1 = new UsuarioDTO(user1.getNombre()
                , user1.getApellido()
                , user1.getMail()
                , user1.getCelular());

        /* 1) Desarrolle un metodo en clase Pedido que se encargue de calcular el total. */
        System.out.println("Total Pedido 3: $" + pedido3.calcularTotal());

        /* 2) Mostrar por consola productos disponibles */
        System.out.println("\n--- Productos Disponibles ---");
        Set<Producto> prods = Set.of(prod1, prod2, prod3, prod4, prod5, prod6, prod7, prod8, prod9, prod10);
        prods.stream()
                .filter(Producto::getDisponible)
                .forEach(System.out::println);

        /* 3) Mostrar por consola la cantidad de ítems que tiene un pedido */
        System.out.println("\n--- Cantidad de ítems en Pedido 2 ---");
        int items = pedido2.getDetallePedidos().stream()
                .mapToInt(DetallePedido::getCantidad)
                .sum();
        System.out.println("Items: " + items);

        /* 4) Detectar productos que tengan menos de 5 como valor en stock */
        System.out.println("\n--- Productos con bajo stock (<5) ---");
        prods.stream()
                .filter(p -> p.getStock() < 5)
                .collect(Collectors.toList())
                .forEach(System.out::println);

        /* --- NUEVAS CONSULTAS (PARA CUMPLIR CON EL TP) --- */

        /* 5) Agrupar productos por categoría */
        System.out.println("\n--- Productos Agrupados por Categoría ---");
        Map<String, List<Producto>> productosPorCategoria = List.of(cat1, cat2, cat3).stream()
                .collect(Collectors.toMap(
                        Categoria::getNombre,
                        Categoria::getProductos
                ));
        productosPorCategoria.forEach((cat, lista) -> {
            System.out.println("Categoría: " + cat);
            lista.forEach(p -> System.out.println(" - " + p.getNombre()));
        });

        /* 6) Calcular facturación total (Suma de todos los pedidos terminados) */
        Double facturacionTotal = List.of(pedido1, pedido2, pedido3).stream()
                .filter(p -> p.getEstado() == Estado.TERMINADO)
                .mapToDouble(Pedido::calcularTotal)
                .sum();
        System.out.println("\nFacturación Total (Pedidos Terminados): $" + facturacionTotal);

        /* 7) Uso de flatMap: Lista de productos únicos comprados por un usuario */
        System.out.println("\n--- Productos comprados por Ignacio Salazar ---");
        List.of(pedido1, pedido2, pedido3).stream()
                .filter(p -> p.getUsuario().getNombre().equals("Ignacio"))
                .flatMap(p -> p.getDetallePedidos().stream())
                .map(det -> det.getProducto().getNombre())
                .distinct()
                .forEach(name -> System.out.println(" - " + name));

        /* 8) Uso de Optional para búsqueda segura */
        System.out.println("\n--- Búsqueda de producto 'Harina' ---");
        prods.stream()
                .filter(p -> p.getNombre().equalsIgnoreCase("Harina"))
                .findFirst()
                .ifPresentOrElse(
                        p -> System.out.println("Encontrado: " + p.getNombre() + " - Stock: " + p.getStock()),
                        () -> System.out.println("Producto no encontrado")
                );

    }
}
