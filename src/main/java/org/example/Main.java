package org.example;

import static org.example.Usuario.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
            Usuario usuario = builder()
                    .id(1)
                    .nombre("Ignacio")
                    .apellido("Salazar")
                    .email("ignaciosalazar986@gmail.com")
                    .celular("341444555")
                    .contrasenia("123");
        System.out.printf("--------------");
        System.out.printf("Usuario %s\n", usuario);
        }
    }
