package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import jakarta.persistence.Persistence;
import org.example.model.*;
import org.example.model.enums.Estado;
import org.example.model.enums.FormaPago;
import org.example.model.enums.Rol;
import org.example.repository.CategoriaRepository;
import org.example.repository.PedidoRepository;
import org.example.repository.ProductoRepository;
import org.example.repository.UsuarioRepository;
import org.example.util.JPAUtil;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


public class Main {
    public static void main(String[] args) {
        handleMenu();
        return;
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
                System.out.print("Ingrese nombre de categoría... ");
                String nombreCategoria = scanner.nextLine();
                System.out.print("Ingrese descripcion de categoría... ");
                String descripcionCategoria = scanner.nextLine();
                Categoria categoria1 = Categoria.builder()
                        .nombre(nombreCategoria)
                        .descripcion(descripcionCategoria)
                        .build();
                Categoria catGuardada = repositorioCategoria.guardar(categoria1);

                System.out.println("########### Categoria creada con ID: " + catGuardada.getId() + " ###########");
                break;
            case 2:
                System.out.print("Ingrese el ID que desea eliminar... ");
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
                System.out.println("Ingrese ID de la categoria a modificar... ");
                Long idAModificar = Long.parseLong(scanner.nextLine());
                Optional<Categoria> categoriaEncontrada = repositorioCategoria.buscarPorId(idAModificar);
                if (categoriaEncontrada.isPresent() && !categoriaEncontrada.get().isEliminado()) {
                    Categoria catAModificar = categoriaEncontrada.get();
                    System.out.println("Valores actuales");
                    System.out.println("Nombre: " + catAModificar.getNombre());
                    System.out.println("Descripcion: " + catAModificar.getDescripcion());
                    System.out.println("Ingrese Opcion a modificar: ");
                    System.out.println("1: para modificar Nombre ");
                    System.out.println("2: para modificar Descripcion ");
                    System.out.println("0: Descartar ");

                    int opcion = Integer.parseInt(scanner.nextLine());
                    switch (opcion) {
                        case 1:
                            System.out.println("Ingrese nuevo nombre... ");
                            System.out.println("");
                            String nuevoNombre = scanner.nextLine();
                            catAModificar.setNombre(nuevoNombre);
                            break;
                        case 2:
                            System.out.println("Ingrese nueva descripcion... ");
                            System.out.println("");
                            String nuevaDescripcion = scanner.nextLine();
                            catAModificar.setDescripcion(nuevaDescripcion);
                            break;
                        case 0:
                            System.out.println("Descartado");
                            break;
                    }
                    EntityManagerFactory emf = JPAUtil.getEmf();
                    EntityManager em = emf.createEntityManager();
                    try {
                        em.getTransaction().begin();
                        em.merge(catAModificar);
                        em.getTransaction().commit();
                        System.out.println("Categoria modificada");
                    } finally {
                        em.close();
                    }
                } else {
                    System.out.println("No se encontro ID");
                }
                break;
            case 4:
                System.out.println("IDs Categorias Activas");
                System.out.println("");
                repositorioCategoria.listarActivos().forEach(cat -> System.out.println("ID: " + cat.getId() + " Nombre: " + cat.getNombre() + " Descripcion: " + cat.getDescripcion()));
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
        System.out.println("1: Crear Producto");
        System.out.println("2: Para eliminar un producto");
        System.out.println("3: Para modificar un productos");
        System.out.println("4: Para productos disponibles");
        System.out.println("5: Para ver productos por categoria");
        System.out.println("0: Para volver al menos principal");
        int opcionCategoria = Integer.parseInt(scanner.nextLine());
        switch (opcionCategoria) {
            case 0:
                System.out.println("Descartado");
                handleMenu();
                break;
            case 1:
                List<Categoria> categoriasCreadas = repositorioCategoria.listarActivos();
                System.out.println("Elige el ID de una categoria...");
                System.out.println("");
                categoriasCreadas.forEach(cat -> System.out.println("ID: " + cat.getId() + " Nombre: " + cat.getNombre()));
                System.out.println("");
                System.out.println("Elige el ID de una categoria... ");
                Long idSeleccionado = Long.parseLong(scanner.nextLine());
                Optional<Categoria> catSeleccionada = repositorioCategoria.buscarPorId(idSeleccionado);
                if (catSeleccionada.isEmpty()) {
                    System.out.println("ERROR. El id no corresponde a ninguna categoria");
                    handleProducto();
                    return;
                } else {
                    System.out.println("Ingrese nombre de nuevo producto... ");
                    String nombreProducto = scanner.nextLine();
                    if (nombreProducto.isEmpty()) {
                        System.out.println("El nombre es obligatorio");
                        handleProducto();
                        return;
                    }
                    System.out.println("Ingrese descripcion...");
                    String descripcionProducto = scanner.nextLine();
                    System.out.println("Ingrese el precio...");
                    Double precioProducto = Double.parseDouble(scanner.nextLine());
                    if (precioProducto <= 0) {
                        System.out.println("El precio no puede ser 0 o un numero negativo");
                        handleProducto();
                    }
                    System.out.println("Ingrese el stock... ");
                    int stockProducto = Integer.parseInt(scanner.nextLine());
                    if (stockProducto < 0) {
                        System.out.println("El stock no puede ser un numero negativo");
                        handleProducto();
                    }
                    System.out.println("Ingrese url de la imagen (Opcional)");
                    String imagenProducto = scanner.nextLine();
                    System.out.println("El producto esta disponible? S/N");
                    System.out.println("1: Si");
                    System.out.println("2: No");
                    int eliminadoProducto = Integer.parseInt(scanner.nextLine());
                    boolean eliminado = true;
                    if (eliminadoProducto == 1) {
                        eliminado = false;
                    } else if (eliminadoProducto != 2) {
                        System.out.println("La opcion elegida es incorrecta");
                        handleProducto();
                        return;
                    }
                    Producto nuevoProducto = Producto.builder()
                            .nombre(nombreProducto)
                            .precio(precioProducto)
                            .stock(stockProducto)
                            .descripcion(descripcionProducto)
                            .imagen(imagenProducto)
                            .eliminado(eliminado)
                            .build();
                    Producto prodGuardado = repositorioProducto.guardar(nuevoProducto);
                    repositorioCategoria.agregarProductoACategoria(idSeleccionado, prodGuardado);

                    System.out.println("");
                    System.out.println("Producto creado con ID: " + prodGuardado.getId());
                    System.out.println("Categoria: " + catSeleccionada.get().getNombre());
                    System.out.println("");
                }
                break;
            case 2:
                repositorioProducto.listarActivos().forEach(prod -> System.out.println("ID: " + prod.getId() + " Nombre: " + prod.getNombre()));
                System.out.println("Elija el ID de un producto para eliminar...");
                Long idProdAEliminar = Long.parseLong(scanner.nextLine());
                Optional<Producto> prodElegido = repositorioProducto.buscarPorId(idProdAEliminar);
                if (prodElegido.isPresent() && !prodElegido.get().isEliminado()) {
                    repositorioProducto.eliminarLogico(idProdAEliminar);
                    System.out.println("Producto Eliminado");
                } else {
                    System.out.println("ERROR - No se encontro el ID");
                    handleProducto();
                    return;
                }
                break;
            case 3:
                System.out.println("Ingrese el ID del producto a modificar... ");
                System.out.println("");
                for (Producto prod : repositorioProducto.listarActivos()) {
                    System.out.println("ID: " + prod.getId());
                    System.out.println("Nombre: " + prod.getNombre());
                    System.out.println("Precio: " + prod.getPrecio());
                    System.out.println("Stock: " + prod.getStock());
                    System.out.println("");
                }
                repositorioProducto.listarActivos().forEach(prod -> System.out.println("ID: " + prod.getId() + " Nombre: " + prod.getNombre() + " Precio: " + prod.getPrecio() + " Stock: " + prod.getStock()));
                System.out.println("Ingrese el ID del producto a modificar... ");
                System.out.println("");
                Long idElegido = Long.parseLong(scanner.nextLine());
                Optional<Producto> prodEncontrado = repositorioProducto.buscarPorId(idElegido);
                if (prodEncontrado.isPresent() && !prodEncontrado.get().isEliminado()) {
                    Producto prodAModificar = prodEncontrado.get();
                    System.out.println("1: Para modificar nombre");
                    System.out.println("2: Para modificar descripcion");
                    System.out.println("3: Para modificar precio");
                    System.out.println("4: Para modificar stock");
                    System.out.println("0: Para descartar 0");
                    int opcionElegida = Integer.parseInt(scanner.nextLine());
                    switch (opcionElegida) {
                        case 1:
                            System.out.print("Ingrese nuevo nombre... ");
                            String nuevoNombre = scanner.nextLine();
                            prodAModificar.setNombre(nuevoNombre);
                            break;
                        case 2:
                            System.out.print("Ingrese nueva descripcion... ");
                            String nuevaDescripcion = scanner.nextLine();
                            prodAModificar.setDescripcion(nuevaDescripcion);
                            break;
                        case 3:
                            System.out.println("Ingrese nuevo precio... ");
                            Double nuevoPrecio = Double.parseDouble(scanner.nextLine());
                            prodAModificar.setPrecio(nuevoPrecio);
                            break;
                        case 4:
                            System.out.println("Ingrese nuevo stock... ");
                            int nuevoStock = Integer.parseInt(scanner.nextLine());
                            prodAModificar.setStock(nuevoStock);
                            break;
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
                break;
            case 4:
                repositorioProducto.listarActivos()
                        .forEach(a -> System.out.println("ID: " + a.getId() + "\n" + "Nombre: " + a.getNombre() + "\n" + "Precio: " + a.getPrecio() + "\n" + "Stock: " + a.getStock()));
                break;
            case 5:
                repositorioCategoria.listarActivos()
                        .forEach(sout -> System.out.println("ID: " + sout.getId() + "\n" + "Nombre: " + sout.getNombre()));
                System.out.println("");
                System.out.println("Elija un ID de la lista... ");
                Long idCategoriaElegida = Long.parseLong(scanner.nextLine());
                Optional<Categoria> categoriaElegida = repositorioCategoria.buscarPorId(idCategoriaElegida);

                if (categoriaElegida.isPresent()) {
                    List<Producto> productos = repositorioProducto.buscarPorCategoria(categoriaElegida.get().getId());
                    if (productos.isEmpty()) {
                        System.out.println("La categoria esta vacia");
                        break;
                    }
                    productos.forEach(prod -> System.out.println("ID: " + prod.getId() + "Nombre: " + prod.getNombre() + "Stock: " + prod.getStock()));
                }
                break;
        }
        handleProducto();
    }

    public static void handleUsuario() {
        System.out.println("");
        System.out.println("-- Gestion Usuario --");
        System.out.println("");
        UsuarioRepository repositorioUsuaraio = new UsuarioRepository();

        Scanner scanner = new Scanner(System.in);
        System.out.println("1: Crear Usuario");
        System.out.println("2: Modificar Usuario");
        System.out.println("3: Listar Usuarios activos");
        System.out.println("4: Buscar usuario por mail");
        System.out.println("5: Eliminar usuario");
        System.out.println("0: Volver al Menu principal");

        int opcionUsuario = Integer.parseInt(scanner.nextLine());
        switch (opcionUsuario) {
            case 0:
                System.out.println("Descartado");
                handleMenu();
                break;
            case 1:
                System.out.println("Ingrese nombre...");
                String nombreUsuario = scanner.nextLine();
                if (nombreUsuario.isEmpty()) {
                    System.out.println("El nombre no puede estar vacio");
                    handleMenu();
                    break;
                }
                System.out.println("Ingrese apellido... ");
                String apellidoUsuario = scanner.nextLine();
                if (apellidoUsuario.isEmpty()) {
                    System.out.println("El apellido no puede estar vacio");
                    handleMenu();
                    break;
                }
                System.out.println("Ingrese mail...");
                String mailUsuario = scanner.nextLine().trim();
                Optional<Usuario> usuarioEncontrado = repositorioUsuaraio.buscarPorMail(mailUsuario);
                while (usuarioEncontrado.isPresent()) {
                    System.out.println("El mail ya fue asignado a un usuario, elija un mail nuevo");
                    mailUsuario = scanner.nextLine().trim();
                    usuarioEncontrado = repositorioUsuaraio.buscarPorMail(mailUsuario);
                }
                if (mailUsuario.isEmpty()) {
                    System.out.println("El mail no puede estar vacio");
                    handleMenu();
                    break;
                }
                System.out.println("Ingrese celular (Opcional)...");
                String celularUsuario = scanner.nextLine();
                System.out.println("Ingrese password...");
                String passwordUsuario = scanner.nextLine();
                if (passwordUsuario.isEmpty()) {
                    System.out.println("El password no puede estar vacio");
                    handleMenu();
                }
                System.out.println("Elija un rol");
                System.out.println("1: Para ADMIN");
                System.out.println("2: para USUARIO");
                int rolUsuario = Integer.parseInt(scanner.nextLine());
                Rol rolSeleccionado = Rol.USUARIO;
                if (rolUsuario == 1) {
                    rolSeleccionado = Rol.ADMIN;
                } else if (rolUsuario != 2) {
                    System.out.println("Opcion invalida");
                    handleMenu();
                    return;
                }
                Usuario usuarioCreado = Usuario.builder()
                        .nombre(nombreUsuario)
                        .apellido(apellidoUsuario)
                        .celular(celularUsuario)
                        .contrasenia(passwordUsuario)
                        .mail(mailUsuario)
                        .rol(rolSeleccionado)
                        .build();
                Usuario usuarioGestionado = repositorioUsuaraio.guardar(usuarioCreado);
                System.out.println("Usuario guardado con ID: " + usuarioGestionado.getId());
                break;
            case 2:
                repositorioUsuaraio.listarActivos().forEach(u -> System.out.println("ID " + u.getId() + " Nombre: " + u.getNombre() + " " + u.getApellido() + " Email: " + u.getMail()));
                System.out.println("");
                System.out.println("Elija el ID de la lista a modificar... ");
                Long idSeleccionado = Long.parseLong(scanner.nextLine());
                Optional<Usuario> usuarioPorId = repositorioUsuaraio.buscarPorId(idSeleccionado);
                if (usuarioPorId.isEmpty()) {
                    System.out.println("ID no encontrado");
                    handleUsuario();
                    return;
                }
                usuarioGestionado = usuarioPorId.get();
                System.out.println("Ingrese la opcion a modificar");
                System.out.println("");
                System.out.println("1: para modificar nombre");
                System.out.println("2: para modificar apellido");
                System.out.println("3: para modificar celular");
                System.out.println("4: para modificar password");
                System.out.println("5: para modificar mail");
                System.out.println("0: Volver al menu principal");
                int opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 0:
                        handleMenu();
                        break;
                    case 1:
                        System.out.println("Ingrese nombre...");
                        String nuevoNombre = scanner.nextLine();
                        if (nuevoNombre.isEmpty()) {
                            System.out.println("El nombre no puede estar vacio");
                            handleUsuario();
                            break;
                        }
                        usuarioGestionado.setNombre(nuevoNombre);
                        break;
                    case 2:
                        System.out.println("Ingrese apellido");
                        String nuevoApellido = scanner.nextLine();
                        if (nuevoApellido.isEmpty()) {
                            System.out.println("El apellido no puede estar vacio");
                            handleUsuario();
                            break;
                        }
                        usuarioGestionado.setApellido(nuevoApellido);
                        break;
                    case 3:
                        System.out.println("Ingrese celular...");
                        String nuevoCelular = scanner.nextLine();
                        usuarioGestionado.setCelular(nuevoCelular);
                        break;
                    case 4:
                        System.out.println("Ingrese password...");
                        String nuevoPassword = scanner.nextLine();
                        if (nuevoPassword.isEmpty()) {
                            System.out.println("El password no puede estar vacio");
                            handleUsuario();
                            break;
                        }
                        usuarioGestionado.setContrasenia(nuevoPassword);
                        break;
                    case 5:
                        System.out.println("Ingrese mail...");
                        String nuevoMail = scanner.nextLine().toLowerCase().trim();
                        Optional<Usuario> mailEncontrado = repositorioUsuaraio.buscarPorMail(nuevoMail);
                        if (mailEncontrado.isPresent() || nuevoMail.isEmpty()) {
                            System.out.println("El mail ya esta registrado");
                            handleUsuario();
                            break;
                        }
                        usuarioGestionado.setMail(nuevoMail);
                        break;
                }
                EntityManagerFactory emf = JPAUtil.getEmf();
                EntityManager em = emf.createEntityManager();
                try {
                    em.getTransaction().begin();
                    em.merge(usuarioGestionado);
                    em.getTransaction().commit();
                } finally {
                    em.close();
                }
                break;
            case 3:
                repositorioUsuaraio.listarActivos().forEach(u -> System.out.println("ID " + u.getId() + " Nombre: " + u.getNombre() + " " + u.getApellido() + " Email: " + u.getMail() + " Rol " + u.getRol()));
                System.out.println("");
                break;
            case 4:
                System.out.println("Ingrese el mail...");
                String mail = scanner.nextLine();
                Optional<Usuario> encontrado = repositorioUsuaraio.buscarPorMail(mail);
                if (encontrado.isEmpty()) {
                    System.out.println("No se encontro el usuario");
                    handleUsuario();
                    break;
                }
                System.out.println("Usuario encontrado");
                System.out.println(" Nombre: " + encontrado.get().getNombre() + " Apellido: " + encontrado.get().getApellido());
                break;
            case 5:
                System.out.println("Ingrese el ID del usuario que desea eliminar...");
                long idAEliminar = Integer.parseInt(scanner.nextLine());
                Optional<Usuario> usuarioAEliminar = repositorioUsuaraio.buscarPorId(idAEliminar);
                if (usuarioAEliminar.isEmpty()) {
                    System.out.println("No se encontro el ID del usuario");
                    handleUsuario();
                    break;
                }
                boolean elimnado = repositorioUsuaraio.eliminarLogico(idAEliminar);
                if (elimnado) {

                }
        }
        handleUsuario();
    }

    public static void handlePedido() {
        System.out.println("");
        System.out.println("-- Gestion Pedido --");
        System.out.println("");
        PedidoRepository repositorioPedido = new PedidoRepository();
        UsuarioRepository repositoryUsuario = new UsuarioRepository();
        ProductoRepository repositorioProducto = new ProductoRepository();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese la opcion deseada");
        System.out.println("1: Para crear un Pedido");
        System.out.println("2: Para Modificar estado");
        System.out.println("3: Para Eliminar Pedido");
        System.out.println("4: Para Ver Pedidos");
        System.out.println("5: Para Ver Pedidos de un usuario especifico");
        System.out.println("0: Volver al menu principal");
        FormaPago formaPago = null;
        int option = Integer.parseInt(scanner.nextLine());
        switch (option) {
            case 1:
                List<Usuario> usuarios = repositoryUsuario.listarActivos();
                usuarios.forEach(u -> System.out.println("ID " + u.getId() + " Nombre: " + u.getNombre() + " Email: " + u.getMail()));
                if (usuarios.isEmpty()) {
                    System.out.println("No existen usuarios creados");
                    handleUsuario();
                    break;
                }
                System.out.println("Elija el ID de un usuario...");
                Long id = Long.parseLong(scanner.nextLine());
                Optional<Usuario> usuarioEcontrado = repositoryUsuario.buscarPorId(id);
                if (usuarioEcontrado.isEmpty()) {
                    System.out.println("El ID ingresadao no existe");
                    handleUsuario();
                    break;
                }
                System.out.println("Elija una forma de pago...");
                System.out.println("1: Efectivo");
                System.out.println("2: Tranferencia");
                System.out.println("3: Tarjeta ");
                int option2 = Integer.parseInt(scanner.nextLine());

                switch (option2) {
                    case 1:
                        formaPago = FormaPago.EFECTIVO;
                        break;
                    case 2:
                        formaPago = FormaPago.TRANFERENCIA;
                        break;
                    case 3:
                        formaPago = FormaPago.TARJETA;
                        break;
                }

                Map<Long, Integer> productosAAgregar = new HashMap<>();
                boolean seguir = true;
                if (repositorioProducto.listarActivos().isEmpty()) {
                    System.out.println("No existen productos en el usuario");
                    handlePedido();
                }
                while (seguir) {
                    System.out.println("Seleccione el ID del producto... ");
                    repositorioProducto.listarActivos().forEach(u -> {
                        System.out.println("ID: " + u.getId() + " Nombre: " + u.getNombre() + " Precio: $" + u.getPrecio() + " Stock: " + u.getStock());
                    });
                    System.out.println("0 Para volver al menu ");
                    long idProducto = Long.parseLong(scanner.nextLine());
                    if (idProducto == 0) {
                        handlePedido();
                    }
                    Optional<Producto> productoEncontrado = repositorioProducto.buscarPorId(idProducto);
                    if (productoEncontrado.isEmpty() || productoEncontrado.get().isEliminado()) {
                        System.out.println("Producto no encontrado");
                        continue;
                    }
                    int stockDiponible = productoEncontrado.get().getStock();
                    System.out.println("Ingrese la cantidad, stock disponible: " + stockDiponible);
                    int cantidad = Integer.parseInt(scanner.nextLine());
                    if (cantidad < 1 || cantidad > stockDiponible) {
                        System.out.println("La cantidad debe ser mayor a 0 y no superar el stock disponible");
                        continue;
                    }
                    productosAAgregar.put(productoEncontrado.get().getId(), cantidad);
                    System.out.println(productosAAgregar.toString());
                    System.out.println("Desea agregar otro producto?");
                    System.out.println("1: Si");
                    System.out.println("2: No");
                    int opcion = Integer.parseInt(scanner.nextLine());
                    seguir = opcion == 1;
                }
                if (productosAAgregar.isEmpty()) {
                    System.out.println("No se han agregado productos en el pedido. El pedido debe tener al menos un producto");
                    handlePedido();
                }
                EntityManagerFactory emf = JPAUtil.getEmf();
                EntityManager em = emf.createEntityManager();
                try {
                    em.getTransaction().begin();
                    Pedido pedido = Pedido.builder()
                            .estado(Estado.PENDIENTE)
                            .formaPago(formaPago)
                            .usuario(usuarioEcontrado.get())
                            .build();
                    for (Map.Entry<Long, Integer> entry : productosAAgregar.entrySet()) {
                        Long idProd = entry.getKey();
                        int cantidad = entry.getValue();
                        Optional<Producto> prodEncontrado = Optional.ofNullable(em.find(Producto.class, idProd));
                        if (prodEncontrado.isPresent()) {
                            DetallePedido detallePedido = new DetallePedido(cantidad, prodEncontrado.get());
                            System.out.println("Subtotal: $" + detallePedido.getSubtotal());
                            pedido.addDetallePedido(cantidad, prodEncontrado.get());
                        }
                    }

                    pedido.calcularTotal();

                    for (Map.Entry<Long, Integer> entry : productosAAgregar.entrySet()) {
                        Long idProd = entry.getKey();
                        int cantidad = entry.getValue();
                        Optional<Producto> prod = repositorioProducto.buscarPorId(idProd);
                        if (prod.isPresent()) {
                            prod.get().setStock(prod.get().getStock() - cantidad);
                        }
                    }
                    em.persist(pedido);
                    em.getTransaction().commit();
                    pedido.calcularTotal();
                    Set<DetallePedido> detalles = pedido.getDetallePedidos();
                    System.out.println("Pedido generado ID: " + pedido.getId() + " fecha: " + pedido.getCreatedAt() + " Usuario " + pedido.getUsuario().getNombre() + " " + pedido.getUsuario().getApellido() + " Forma de pago: " + pedido.getFormaPago());
                    detalles.forEach(det -> System.out.println("Producto" + det.getProducto().getNombre() + " " + det.getCantidad() + " x  $" + det.getProducto().getPrecio() + " Subtotal: $" + det.getSubtotal()));
                } catch (Exception e) {
                    em.getTransaction().rollback();
                    throw new RuntimeException(e);
                } finally {
                    em.close();
                }
                break;
            case 2:
                System.out.println("Ingrese el ID del pedido");
                Long idPedido = Long.parseLong(scanner.nextLine());
                Optional<Pedido> pedidoBuscado = repositorioPedido.buscarPorId(idPedido);
                if (pedidoBuscado.isEmpty()) {
                    System.out.println("No se encontro el pedido buscado");
                    break;
                }
                System.out.println("Pedido con estado " + pedidoBuscado.get().getEstado());
                System.out.println("Ingrese el numero de estado que desea");
                System.out.println("1 PENDIENTE");
                System.out.println("2 CONFIRMADO");
                System.out.println("3 TERMINADO");
                System.out.println("4 CANCELADO");
                Estado estado = Estado.PENDIENTE;
                int opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 1:
                        estado = Estado.PENDIENTE;
                        break;
                    case 2:
                        estado = Estado.CONFRIMADO;
                        break;
                    case 3:
                        estado = Estado.TERMINADO;
                        break;
                    case 4:
                        estado = Estado.CANCELADO;
                        break;
                }
                pedidoBuscado.get().setEstado(estado);
                repositorioPedido.guardar(pedidoBuscado.get());
                break;
            case 3:
                System.out.println("Ingrese el ID del pedido a eliminar");
                Long idPedido2 = Long.parseLong(scanner.nextLine());
                Optional<Pedido> pedidoAEliminar = repositorioPedido.buscarPorId(idPedido2);
                if (pedidoAEliminar.isEmpty() || pedidoAEliminar.get().isEliminado()) {
                    System.out.println("No se encuentra pedido");
                    break;
                }
                repositorioPedido.eliminarLogico(idPedido2);
                System.out.println("Pedido eliminado");
                break;
            case 4:
                repositorioPedido.listarActivos().forEach(System.out::println);
                break;
            case 5:
                System.out.println("Ingrese el ID del usuario...");
                Long idUsuario = Long.parseLong(scanner.nextLine());
                List<Pedido> pedidosActivos = repositorioPedido.listarActivos();
                List<Pedido> usuariosConPedidos = pedidosActivos.stream().filter(u -> u.getUsuario().getId() == idUsuario).collect(Collectors.toList());
                if (usuariosConPedidos.isEmpty()) {
                    System.out.println("El usuario no tiene ningun pedido");
                    handlePedido();
                    return;
                }
                usuariosConPedidos.forEach(u -> {
                    System.out.println(u.getId() + " " + u.getCreatedAt() + " " + u.getEstado());
                });
                break;
        }
        handlePedido();
    }

    public static void handleMenu() {
        System.out.println("-- Menu Principal --");
        System.out.println("");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Elija una opcion para comenzar");
        System.out.println("");
        System.out.println("1: Manejar Categorias");
        System.out.println("2: Manejar Productos");
        System.out.println("3: Manejar Usuarios");
        System.out.println("4: Manejar Pedidos");
        System.out.println("0: Terminar");
        int opcion1 = scanner.nextInt();
        switch (opcion1) {
            case 0:
                JPAUtil.close();
                break;
            case 1:
                handleCategoria();
                break;
            case 2:
                handleProducto();
                break;
            case 3:
                handleUsuario();
                break;
            case 4:
                handlePedido();
                break;
        }
    }
}