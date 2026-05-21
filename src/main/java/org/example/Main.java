package org.example;

import org.example.DTOs.UsuarioDTO;
import org.example.enums.Estado;
import org.example.enums.FormaPago;
import org.example.enums.Rol;
import org.example.model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
                .stock(50)
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
                .stock(20)
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
                .disponible(true)
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

        /*
        4. En la clase Main se deberán tener las instancias solicitadas
        en el punto anterior y se deberá utilizar el metodo toString para
        mostrar por consola un producto, el listado de productos cargados
        y los pedidos del usuario que posea la mayor cantidad de pedidos.
        */
        System.out.println(prod3.toString());
        System.out.println(pedido1.getDetallePedidos());
        System.out.println(pedido2.getDetallePedidos());
        System.out.println(pedido3.getDetallePedidos());
        System.out.println(user1.toString());
        /*
        5. Instanciar un nuevo producto donde el/los campos utilizados en
        el metodo equals sean iguales a los de otro producto existente.
        Luego, comparar dicha instancia con todos los elementos de la
        colección de productos y mostrar los resultados por pantalla.
        */
        Producto prodEqual = Producto.builder()
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
        System.out.println("Productos iguales --> " + prodEqual.equals(prod6));

        List<Producto> todosLosProductos = List.of(prod1, prod2, prod3, prod4, prod5, prod6, prod7, prod8, prod9, prod10);
        for (Producto p : todosLosProductos) {
            System.out.println("¿Es prodEqual igual a " + p.getNombre() + "? " + prodEqual.equals(p));
        }

        /*
        6. Crear un nuevo paquete llamado DTOs y, dentro de él, una clase
         record llamada UsuarioDTO, que contendrá la misma información que
          la clase Usuario, evitando mostrar información sensible. Se
          deberán ocultar los siguientes atributos: a. Rol b. Contraseña
        */
        UsuarioDTO userDTO1 = new UsuarioDTO(user1.getNombre()
                ,user1.getApellido()
                ,user1.getMail()
                ,user1.getCelular());

        System.out.println(userDTO1.nombre());
        System.out.println(userDTO1.apellido());
        System.out.println(userDTO1.mail());
        System.out.println(userDTO1.celular());

    }
}
