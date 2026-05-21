package org.example.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Base {
    private Long id;
    private boolean eliminado;
    @EqualsAndHashCode.Exclude
    private LocalDateTime createdAt;
}
