package com.main.model.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

public class EntityManagerConnector {
    @PersistenceContext
    static protected EntityManager entityManager;
    static EntityManagerFactory managerFactory;

    static {
        managerFactory = Persistence.createEntityManagerFactory("jpa");
        entityManager = managerFactory.createEntityManager();
    }

    public static void close() {
        entityManager.close();
        managerFactory.close();
    }
}
