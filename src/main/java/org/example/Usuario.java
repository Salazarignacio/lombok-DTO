package org.example;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class Usuario extends Base{
    private String nombre;
    private String apellido;
    private String email;
    private String celular;
    private String constrasenia;
}
