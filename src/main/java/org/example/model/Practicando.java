package org.example.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Testeando")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Practicando {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    @Column(name = "NOMBRE", length = 100)
    private String nombre;

    @Builder
    public Practicando(String nombre) {
        this.nombre = nombre;
    }
}
