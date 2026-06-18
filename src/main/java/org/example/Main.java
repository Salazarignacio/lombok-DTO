package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.DTOs.UsuarioDTO;
import org.example.enums.Estado;
import org.example.enums.FormaPago;
import org.example.enums.Rol;
import org.example.model.*;
import org.example.repository.CategoriaRepository;
import org.example.repository.ProductoRepository;
import org.example.util.JPAUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import java.util.stream.Collectors;


public class Main {
    public static void main(String[] args) {
        CategoriaRepository repositorioCategoria = new CategoriaRepository();

        ProductoRepository repositorioProducto = new ProductoRepository();
/*Implementar en la clase Main un submenu de Categorias con las siguientes opciones:
6. Alta: solicitar nombre y descripcion, crear la instancia, persistir y mostrar el ID generado.*/
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese nombre de categoría: ");
        String nombreCategoria = scanner.nextLine();
        System.out.print("Ingrese descripcion de categoría: ");
        String descripcionCategoria = scanner.nextLine();
        Categoria categoria1 = Categoria.builder()
                .nombre(nombreCategoria)
                .descripcion(descripcionCategoria)
                .build();
        repositorioCategoria.guardar(categoria1);
        List<Categoria> categorias = repositorioCategoria.listarActivos();
        Categoria ultimaCategoria = categorias.get(categorias.size() - 1);
        categoria1.setId(ultimaCategoria.getId());
        System.out.println("Categoria creada con ID: " + categoria1.getId());
        /*7. Baja logica: solicitar el ID, marcar eliminado = true. Si no existe, mostrar mensaje de error.*/
        for (Categoria cat : categorias) {
            System.out.println("ID: " + cat.getId());
            System.out.println("Nombre: " + cat.getNombre());
        }
        System.out.print("Ingrese el ID que desea eliminar: ");
        Long eliminarID = Long.parseLong(scanner.nextLine());
        Optional<Categoria> catAEliminar = repositorioCategoria.buscarPorId(eliminarID);

        if (catAEliminar != null && !catAEliminar.get().isEliminado()) {
            repositorioCategoria.eliminarLogico(eliminarID);
            System.out.println("Categoria eliminada");
        } else {
            System.out.println("ERROR - No se encontro el ID");
        }
/*8. Modificacion: solicitar el ID, mostrar valores actuales, permitir editar nombre y/o descripcion,
persistir.*/
        for (Categoria cat : categorias) {
            System.out.println("ID: " + cat.getId());
            System.out.println("Nombre: " + cat.getNombre());
        }
        System.out.print("Ingrese ID de la categoria a modificar: ");
        Long idAModificar = Long.parseLong(scanner.nextLine());
        Optional<Categoria> categoriaEncontrada = repositorioCategoria.buscarPorId(idAModificar);
        if (categoriaEncontrada.isPresent()) {
            Categoria catAModificar = categoriaEncontrada.get();
            System.out.println("Valores actuales");
            System.out.println("Nombre: " + catAModificar.getNombre());
            System.out.println("Descripcion: " + catAModificar.getDescripcion());
            System.out.print("Ingrese Opcion a modificar: ");
            System.out.print("1 para modificar Nombre ");
            System.out.print("2 para modificar Descripcion ");
            System.out.print("0 Descartar ");

            int opcion = Integer.parseInt(scanner.nextLine());
            switch (opcion) {
                case 1:
                    System.out.print("Ingrese nuevo nombre: ");
                    String nuevoNombre = scanner.nextLine();
                    catAModificar.setNombre(nuevoNombre);
                    break;
                case 2:
                    System.out.print("Ingrese nueva descripcion: ");
                    String nuevaDescripcion = scanner.nextLine();
                    catAModificar.setDescripcion(nuevaDescripcion);
                    break;
                case 0:
                    System.out.println("Descartado");
                    break;
            }
            EntityManagerFactory emf = JPAUtil.getEmf();
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            em.merge(catAModificar);
            em.getTransaction().commit();
            em.close();
        } else System.out.println("No se encontro ID");
        /*9. Listado: mostrar todas las categorias activas con ID, nombre y descripcion.*/
        System.out.println("################ IDs Categorias Activas ################");
        for (Categoria cat : repositorioCategoria.listarActivos()) {
            System.out.println("ID: " + cat.getId());
            System.out.println("Nombre: " + cat.getNombre());
            System.out.println("Descripcion: " + cat.getDescripcion());
        }
 /*10. Alta: listar categorias activas para seleccionar, solicitar nombre, precio, descripcion y stock,
persistir.*/
        List<Categoria> categoriasCreadas = repositorioCategoria.listarActivos();
        System.out.println("###### Elige el ID de una categoria ######");
        for (Categoria cat : categoriasCreadas) {
            System.out.println("ID: " + cat.getId());
            System.out.println("Nombre: " + cat.getNombre());
        }
        Long idSeleccionado = Long.parseLong(scanner.nextLine());
        Optional<Categoria> catSeleccionada = repositorioCategoria.buscarPorId(idSeleccionado);

        if (catSeleccionada.isPresent()) {
            System.out.println("Ingrese nombre de nuevo producto");
            String nombreProducto = scanner.nextLine();
            System.out.println("Ingrese el precio");
            Double precioProducto = Double.parseDouble(scanner.nextLine());
            System.out.println("Ingrese descripcion");
            String descripcionProducto = scanner.nextLine();
            Producto nuevoProducto = Producto.builder()
                    .createdAt(LocalDateTime.now())
                    .eliminado(false)
                    .nombre(nombreProducto)
                    .categoria(catSeleccionada.get())
                    .precio(precioProducto)
                    .descripcion(descripcionProducto)
                    .build();
            repositorioProducto.guardar(nuevoProducto);
            List<Producto> productos = repositorioProducto.listarActivos();
            Producto ultimoProducto = productos.get(productos.size() - 1);
            nuevoProducto.setId(ultimoProducto.getId());
        }
        /*11. Baja logica: solicitar el ID del producto. Si no existe o ya esta dado de baja, mostrar error.*/
        for (Producto prod : repositorioProducto.listarActivos()) {
            System.out.println("ID: " + prod.getId());
            System.out.println("Nombre: " + prod.getNombre());
        }
        System.out.println("Elija el ID de un producto para eliminar");
        Long idProdAEliminar = Long.parseLong(scanner.nextLine());
        Optional<Producto> prodElegido = repositorioProducto.buscarPorId(idProdAEliminar);
        if (prodElegido != null && !prodElegido.get().isEliminado()) {
            repositorioProducto.eliminarLogico(idProdAEliminar);
            System.out.println("Producto Eliminado");
        } else {
            System.out.println("ERROR - No se encontro el ID");
        }
        /*12. Modificacion: solicitar el ID, mostrar valores actuales, permitir editar nombre, precio y stock.*/
        System.out.println("Ingrese el ID del producto a modificar");
        for (Producto prod : repositorioProducto.listarActivos()) {
            System.out.println("ID: " + prod.getId());
            System.out.println("ID: " + prod.getNombre());
        }
        Long idElegido = Long.parseLong(scanner.nextLine());
        Optional<Producto> prodEncontrado = repositorioProducto.buscarPorId(idElegido);
        if (prodEncontrado.isPresent()) {
            Producto prodAModificar = prodEncontrado.get();
            System.out.println("Para modificar nombre ingrese 1");
            System.out.println("Para modificar descripcion ingrese 2");
            System.out.println("Para modificar precio ingrese 3");
            System.out.println("Para modificar stock ingrese 4");
            System.out.println("Para descartar 0");
            int opcionElegida = Integer.parseInt(scanner.nextLine());
            switch (opcionElegida) {
                case 1:
                    System.out.print("Ingrese nuevo nombre: ");
                    String nuevoNombre = scanner.nextLine();
                    prodAModificar.setNombre(nuevoNombre);
                    break;
                case 2:
                    System.out.print("Ingrese nueva descripcion: ");
                    String nuevaDescripcion = scanner.nextLine();
                    prodAModificar.setDescripcion(nuevaDescripcion);
                    break;
                case 3:
                    System.out.println("Ingrese nuevo precio");
                    Double nuevoPrecio = Double.parseDouble(scanner.nextLine());
                    prodAModificar.setPrecio(nuevoPrecio);
                case 4:
                    System.out.printf("Ingrese nuevo stock");
                    int nuevoStock = Integer.parseInt(scanner.nextLine());
                    prodAModificar.setStock(nuevoStock);
                case 0:
                    System.out.println("Descartado");
                    break;
            }
            EntityManagerFactory emf = JPAUtil.getEmf();
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            em.merge(prodAModificar);
            em.getTransaction().commit();
            em.close();
        }
        /*13. Listado: mostrar todos los productos activos con ID, nombre, precio, stock y nombre de su
        categoria*/
        for (Producto prod : repositorioProducto.listarActivos()) {
            System.out.println("ID: " + prod.getId());
            System.out.println("Nombre: " + prod.getNombre());
            System.out.println("Precio: " + prod.getPrecio());
            System.out.println("Stock: " + prod.getStock());
            System.out.println("Categoria: " + prod.getCategoria());
        }
    }
}