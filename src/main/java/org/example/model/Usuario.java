package org.example.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.example.enums.Rol;

import java.util.HashSet;
import java.util.Set;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name="USUARIOS")
public class Usuario extends Base {
    private String nombre;
    private String apellido;
    private String mail;
    private String celular;
    @ToString.Exclude
    private String contrasenia;
    private Rol rol;
    @OneToMany(mappedBy = "usuario")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    private Set<Pedido> pedidos = new HashSet<>();

    public void addPedido(Pedido pedido) {
        pedidos.add(pedido);
        pedido.setUsuario(this);
    }
    public void removePedido(Pedido pedido) {
        pedidos.remove(pedido);
        pedido.setUsuario(null);
    }
}
