package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.DTOs.UsuarioDTO;
import org.example.enums.Estado;
import org.example.enums.FormaPago;
import org.example.enums.Rol;
import org.example.model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import java.util.stream.Collectors;


public class Main {
    public static void main(String[] args) {

        /*Clase principal con menu de consola que expone el ABM
        de Categoria y Producto, y la consulta JPQL.*/
        Usuario user1 = Usuario.builder()

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

                .createdAt(LocalDateTime.now())
                .eliminado(false)
                .nombre("Lacteos")
                .descripcion("Productos lacteos")
                .build();
        Categoria cat2 = Categoria.builder()

                .createdAt(LocalDateTime.now())
                .eliminado(false)
                .nombre("Almacén")
                .descripcion("Productos de uso diario como arroz, fideos, harina, azúcar, etc.")
                .build();
        Categoria cat3 = Categoria.builder()

                .createdAt(LocalDateTime.now())
                .eliminado(false)
                .nombre("Panadería")
                .descripcion("Productos panificados y derivados")
                .build();

        Producto prod1 = Producto.builder()

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
                .createdAt(LocalDateTime.now())
                .fecha(LocalDate.now())
                .estado(Estado.PENDIENTE)
                .formaPago(FormaPago.EFECTIVO)
                .build();

        Pedido pedido2 = Pedido.builder()
                .createdAt(LocalDateTime.now())
                .fecha(LocalDate.now())
                .estado(Estado.CONFRIMADO)
                .formaPago(FormaPago.EFECTIVO)
                .build();
        Pedido pedido3 = Pedido.builder()
                .createdAt(LocalDateTime.now())
                .fecha(LocalDate.now())
                .estado(Estado.TERMINADO)
                .formaPago(FormaPago.TARJETA)
                .build();

        pedido1.setUsuario(user1);
        pedido2.setUsuario(user1);
        pedido3.setUsuario(user2);

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

        pedido1.setUsuario(user1);
        pedido2.setUsuario(user1);
        pedido3.setUsuario(user2);

        Set<Usuario> usuarios = new HashSet<>(Set.of(user1, user2));
        Set<Categoria> categorias = new HashSet<>(Set.of(cat1, cat2, cat3));
        Set<Pedido> pedidos = new HashSet<>(Set.of(pedido1, pedido2, pedido3));


        EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidad");
        EntityManager em = emf.createEntityManager();


        try {
            em.getTransaction().begin();
            recorredor(usuarios, em);
            recorredor(categorias, em);
            recorredor(pedidos, em);
            em.getTransaction().commit();

            /* --- TAREAS DE LA CONSIGNA --- */

            // 5. Actualizar al menos 2 productos
            em.getTransaction().begin();
            Producto p1 = em.find(Producto.class, 1L);
            Producto p2 = em.find(Producto.class, 2L);
            if (p1 != null) {
                p1.setPrecio(p1.getPrecio() * 1.10);
                System.out.println("Producto 1 actualizado: " + p1.getNombre());
            }
            if (p2 != null) {
                p2.setStock(p2.getStock() + 20);
                System.out.println("Producto 2 actualizado: " + p2.getNombre());
            }
            em.getTransaction().commit();

            // 6. Buscar Usuario por id
            Usuario userById = em.find(Usuario.class, 1L);
            if (userById != null) {
                System.out.println("Usuario encontrado por ID (1): " + userById.getNombre() + " " + userById.getApellido());
            }

            // 7. Buscar Usuario por mail
            try {
                Usuario userByMail = em.createQuery("SELECT u FROM Usuario u WHERE u.mail = :email", Usuario.class)
                        .setParameter("email", "ignacio@example.com")
                        .getSingleResult();
                System.out.println("Usuario encontrado por mail: " + userByMail.getNombre() + " (" + userByMail.getMail() + ")");
            } catch (Exception e) {
                System.out.println("Usuario no encontrado por mail.");
            }

            // 8. Borrar 1 producto
            em.getTransaction().begin();
            Producto pAEliminar = em.find(Producto.class, 3L);
            if (pAEliminar != null) {
                em.remove(pAEliminar);
                System.out.println("Producto 3 eliminado.");
            }
            em.getTransaction().commit();

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }

    }

    public static <E> void recorredor(Set<E> set, EntityManager em) {
        for (E entidad : set) {
            em.persist(entidad);
        }
    }
}
