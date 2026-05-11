package org.example;

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


        System.out.println("Usuario: " + user1);
    }
}
