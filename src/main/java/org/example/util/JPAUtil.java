package org.example.util;


import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.Getter;

public class JPAUtil {
    @Getter
    private static final EntityManagerFactory entityManager = Persistence.createEntityManagerFactory("miUnidad");

    public static void close() {
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close();
        }
    }
}
