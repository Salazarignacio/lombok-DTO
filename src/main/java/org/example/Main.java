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
                .createdAt(LocalDateTime.now())
                .eliminado(false)
                .build();
        repositorioCategoria.guardar(categoria1);
        List<Categoria> categorias = repositorioCategoria.listarActivos();
        Categoria ultimaCategoria = categorias.get(categorias.size() - 1);
        categoria1.setId(ultimaCategoria.getId());
        System.out.println(categoria1.getId());
        /*7. Baja logica: solicitar el ID, marcar eliminado = true. Si no existe, mostrar mensaje de error.*/
        System.out.print("Ingrese el ID que desea eliminar: ");
        Long eliminarID = Long.parseLong(scanner.nextLine());
        Boolean categoriaeliminada = repositorioCategoria.eliminarLogico(eliminarID);
        if (categoriaeliminada) {
            System.out.println("Producto eliminado");
        } else {
            System.out.println("ERROR - No se encontro el ID");
        }
/*8. Modificacion: solicitar el ID, mostrar valores actuales, permitir editar nombre y/o descripcion,
persistir.*/
        for (Categoria cat : categorias) {
            System.out.println("ID: " + cat.getId());
        }
        System.out.print("Ingrese ID de la categoria a modificar: ");
        Long idAModificar = Long.parseLong(scanner.nextLine());
        Optional<Categoria> categoriaEncontrada = repositorioCategoria.buscarPorId(idAModificar);
        if (categoriaEncontrada.isPresent()) {
            System.out.println(categoriaEncontrada);
            System.out.print("Ingrese Opcion a modificar: ");
            System.out.print("1 para modificar Nombre ");
            System.out.print("2 para modificar Descripcion ");
            System.out.print("0 Descartar ");
            Categoria catAModificar = categoriaEncontrada.get();
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
        }
        /*9. Listado: mostrar todas las categorias activas con ID, nombre y descripcion.*/
        System.out.println("################ IDs Categorias Activas ################");
        for (Categoria cat : repositorioCategoria.listarActivos()) {
            System.out.println("ID: " + cat.getId());
            System.out.println("Nombre: " + cat.getNombre());
            System.out.println("Categoria: " + cat.getDescripcion());
        }
        
    }
}