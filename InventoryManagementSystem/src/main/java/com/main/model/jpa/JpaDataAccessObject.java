package com.main.model.jpa;

import com.main.model.DataAccessObject;
import com.main.model.entity.ParentEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class JpaDataAccessObject<T extends ParentEntity> implements DataAccessObject<T> {
    public JpaDataAccessObject() {
    }

    @Override
    public Optional<T> get(int id) {
        return Optional.empty();
    }

    @Override
    public List<T> getAll() {
        return null;
    }

    @Override
    public void save(T t) {
        executeInsideTransaction(entityManager -> entityManager.persist(t));
    }

    @Override
    public void delete(T t) {
        executeInsideTransaction(entityManager -> entityManager.remove(t));
    }

    @Override
    public void update(T t, String[] params) {
        assignEntity(t, params);
        executeInsideTransaction(entityManager -> entityManager.merge(t));
    }

    @Override
    public void update(T t) {
        executeInsideTransaction(entityManager -> entityManager.merge(t));
    }

    @Override
    public void assignEntity(T entity, String[] params) {

    }

    public void executeInsideTransaction(Consumer<EntityManager> action) throws NullPointerException {
        if (DatabaseConnector.entityManager == null) {
            System.err.print("\nError::Transaction::executeInsideTransaction::Entity manager is null pointer.");
            return;
        }

        EntityTransaction transaction = DatabaseConnector.entityManager.getTransaction();
        try {
            transaction.begin();
            action.accept(DatabaseConnector.entityManager);
            transaction.commit();
        } catch (RuntimeException e) {
            transaction.rollback();
            throw e;
        }
    }
}
