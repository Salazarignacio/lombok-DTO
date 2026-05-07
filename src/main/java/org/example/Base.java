package org.example;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public abstract class Base {
    private int id;
    private boolean eliminado;
    private LocalDateTime createdAt;



}
