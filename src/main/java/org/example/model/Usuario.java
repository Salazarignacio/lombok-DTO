package org.example.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.example.enums.Rol;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Usuario extends Base {
    private String nombre;
    private String apellido;
    private String mail;
    private String celular;
    @ToString.Exclude
    private String contrasenia;
    private Rol rol;
}
