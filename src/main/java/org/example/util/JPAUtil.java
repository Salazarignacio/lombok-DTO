package org.example.util;


import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.Getter;

public class JPAUtil {
    @Getter
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidad");

    public static void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
