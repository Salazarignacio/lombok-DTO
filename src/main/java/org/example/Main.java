package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import org.example.model.*;
import org.example.repository.CategoriaRepository;
import org.example.repository.ProductoRepository;
import org.example.util.JPAUtil;

import java.time.LocalDateTime;
import java.util.*;


public class Main {
    public static void main(String[] args) {
        handleMenu();

    }

    public static void handleCategoria() {
        CategoriaRepository repositorioCategoria = new CategoriaRepository();

        System.out.println("");
        System.out.println("-- Gestion Categorias --");
        System.out.println("");
        Scanner scanner = new Scanner(System.in);
        System.out.println("1 Crear Categoria");
        System.out.println("2 Para eliminar una categoria");
        System.out.println("3 Para modificar categoria");
        System.out.println("4 Para ver las categorias disponibles");
        System.out.println("0 Para volver al menos principal");
        int opcionCategoria = Integer.parseInt(scanner.nextLine());
        switch (opcionCategoria) {
            case 0:
                handleMenu();
                break;
            case 1:
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
                System.out.println("########### Categoria creada con ID: " + categoria1.getId() + " ###########");
                break;
            case 2:
                System.out.print("Ingrese el ID que desea eliminar: ");
                Long eliminarID = Long.parseLong(scanner.nextLine());
                Optional<Categoria> catAEliminar = repositorioCategoria.buscarPorId(eliminarID);
                if (catAEliminar.isPresent() && !catAEliminar.get().isEliminado()) {
                    repositorioCategoria.eliminarLogico(eliminarID);
                    System.out.println("Categoria eliminada");
                } else {
                    System.out.println("ERROR - No se encontro el ID");
                }
                break;
            case 3:
                for (Categoria cat : repositorioCategoria.listarActivos()) {
                    System.out.println("ID: " + cat.getId());
                    System.out.println("Nombre: " + cat.getNombre());
                }
                System.out.println("Ingrese ID de la categoria a modificar: ");
                Long idAModificar = Long.parseLong(scanner.nextLine());
                Optional<Categoria> categoriaEncontrada = repositorioCategoria.buscarPorId(idAModificar);
                if (categoriaEncontrada.isPresent() && !categoriaEncontrada.get().isEliminado()) {
                    Categoria catAModificar = categoriaEncontrada.get();
                    System.out.println("Valores actuales");
                    System.out.println("Nombre: " + catAModificar.getNombre());
                    System.out.println("Descripcion: " + catAModificar.getDescripcion());
                    System.out.println("Ingrese Opcion a modificar: ");
                    System.out.println("1 para modificar Nombre ");
                    System.out.println("2 para modificar Descripcion ");
                    System.out.println("0 Descartar ");

                    int opcion = Integer.parseInt(scanner.nextLine());
                    switch (opcion) {
                        case 1:
                            System.out.println("Ingrese nuevo nombre: ");
                            System.out.println("");
                            String nuevoNombre = scanner.nextLine();
                            catAModificar.setNombre(nuevoNombre);
                            break;
                        case 2:
                            System.out.println("Ingrese nueva descripcion: ");
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
                    System.out.println("Categoria modificada");
                } else {
                    System.out.println("No se encontro ID");
                }
                break;
            case 4:
                System.out.println("IDs Categorias Activas");
                System.out.println("");
                for (Categoria cat : repositorioCategoria.listarActivos()) {
                    System.out.println("ID: " + cat.getId());
                    System.out.println("Nombre: " + cat.getNombre());
                    System.out.println("Descripcion: " + cat.getDescripcion());
                    System.out.println("");
                }
                break;
        }
        handleCategoria();
    }

    public static void handleProducto() {
        System.out.println("");
        System.out.println("-- Gestion Producto --");
        System.out.println("");
        CategoriaRepository repositorioCategoria = new CategoriaRepository();
        ProductoRepository repositorioProducto = new ProductoRepository();

        Scanner scanner = new Scanner(System.in);
        System.out.println("1 Crear Producto");
        System.out.println("2 Para eliminar un producto");
        System.out.println("3 Para modificar un productos");
        System.out.println("4 Para productos disponibles");
        System.out.println("5 Para ver productos por categoria");
        System.out.println("0 Para volver al menos principal");
        int opcionCategoria = Integer.parseInt(scanner.nextLine());
        switch (opcionCategoria) {
            case 0:
                System.out.println("Descartado");
                handleMenu();
                break;
            case 1:
                List<Categoria> categoriasCreadas = repositorioCategoria.listarActivos();
                System.out.println("Elige el ID de una categoria");
                System.out.println("");
                for (Categoria cat : categoriasCreadas) {
                    System.out.println("ID: " + cat.getId());
                    System.out.println("Nombre: " + cat.getNombre());
                }
                System.out.println("");
                System.out.println("Elige el ID de una categoria");
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
                            .nombre(nombreProducto)
                            .categoria(catSeleccionada.get())
                            .precio(precioProducto)
                            .descripcion(descripcionProducto)
                            .build();
                    repositorioProducto.guardar(nuevoProducto);
                    List<Producto> productos = repositorioProducto.listarActivos();
                    Producto ultimoProducto = productos.get(productos.size() - 1);
                    nuevoProducto.setId(ultimoProducto.getId());
                    System.out.println("");
                    System.out.println("Producto creado con ID: " + nuevoProducto.getId());
                    System.out.println("");
                }
                break;
            case 2:
                for (Producto prod : repositorioProducto.listarActivos()) {
                    System.out.println("ID: " + prod.getId());
                    System.out.println("Nombre: " + prod.getNombre());
                    System.out.println("");
                }
                System.out.println("Elija el ID de un producto para eliminar");
                Long idProdAEliminar = Long.parseLong(scanner.nextLine());
                Optional<Producto> prodElegido = repositorioProducto.buscarPorId(idProdAEliminar);
                if (prodElegido.isPresent() && !prodElegido.get().isEliminado()) {
                    repositorioProducto.eliminarLogico(idProdAEliminar);
                    System.out.println("Producto Eliminado");
                } else {
                    System.out.println("ERROR - No se encontro el ID");
                }
                break;
            case 3:
                System.out.println("Ingrese el ID del producto a modificar");
                System.out.println("");
                for (Producto prod : repositorioProducto.listarActivos()) {
                    System.out.println("ID: " + prod.getId());
                    System.out.println("Nombre: " + prod.getNombre());
                    System.out.println("Precio: " + prod.getPrecio());
                    System.out.println("Stock: " + prod.getStock());
                    System.out.println("");
                }
                System.out.println("Ingrese el ID del producto a modificar");
                System.out.println("");
                Long idElegido = Long.parseLong(scanner.nextLine());
                Optional<Producto> prodEncontrado = repositorioProducto.buscarPorId(idElegido);
                if (prodEncontrado.isPresent() && !prodEncontrado.get().isEliminado()) {
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
            case 4:
                for (Producto prod : repositorioProducto.listarActivos()) {
                    System.out.println("ID: " + prod.getId());
                    System.out.println("Nombre: " + prod.getNombre());
                    System.out.println("Precio: " + prod.getPrecio());
                    System.out.println("Stock: " + prod.getStock());
                    System.out.println("Categoria: " + prod.getCategoria().getNombre());
                }
                break;
            case 5:

                for (Categoria cat : repositorioCategoria.listarActivos()) {
                    System.out.println("ID: " + cat.getId());
                    System.out.println("Nombre: " + cat.getNombre());
                    System.out.println("");
                }

                System.out.println("");
                System.out.println("Elija un ID de la lista");
                Long idCategoriaElegida = Long.parseLong(scanner.nextLine());
                Optional<Categoria> categoriaElegida = repositorioCategoria.buscarPorId(idCategoriaElegida);

                if (categoriaElegida.isPresent()) {
                    List<Producto> productos = repositorioProducto.buscarPorCategoria(categoriaElegida.get().getId());
                    if (productos.size() < 1) {
                        System.out.println("La categoria esta vacia");
                        break;
                    }
                    for (Producto prod : productos) {
                        System.out.println("ID: " + prod.getId());
                        System.out.println("Nombre: " + prod.getNombre());
                        System.out.println("Precio: $" + prod.getPrecio());
                        System.out.println("Stock: " + prod.getStock());
                    }
                }
                break;
        }
        handleProducto();
    }

    public static void handleMenu() {
        System.out.println("-- Menu Principal --");
        System.out.println("");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Elija una opcion para comenzar");
        System.out.println("");
        System.out.println("1 Manejar Categorias");
        System.out.println("2 Manejar Productos");
        int opcion1 = scanner.nextInt();
        switch (opcion1) {
            case 1:
                handleCategoria();
            case 2:
                handleProducto();
        }
    }
}