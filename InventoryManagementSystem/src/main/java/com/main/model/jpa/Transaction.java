package com.main.model.jpa;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.function.Consumer;

public class Transaction {
    public static void executeInsideTransaction(Consumer<EntityManager> action, EntityManager entityManager) throws NullPointerException {
        if (entityManager == null) {
            System.err.print("\nError::Transaction::executeInsideTransaction::Entity manager is null pointer.");
            return;
        }

        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            action.accept(entityManager);
            transaction.commit();
        }
        catch (RuntimeException e) {
            transaction.rollback();
            throw e;
        }
    }
}
