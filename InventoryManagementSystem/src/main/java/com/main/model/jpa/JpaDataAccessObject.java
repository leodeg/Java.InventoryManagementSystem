package com.main.model.jpa;

import com.main.model.DataAccessObject;
import com.main.model.entity.ParentEntity;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class JpaDataAccessObject<T extends ParentEntity> implements DataAccessObject<T>{
    @PersistenceContext
    protected EntityManager entityManager;
    EntityManagerFactory managerFactory;

    public JpaDataAccessObject() {
        managerFactory = Persistence.createEntityManagerFactory("jpa");
        entityManager = managerFactory.createEntityManager();
    }

    public void close() {
        entityManager.close();
        managerFactory.close();
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
    public void update (T t) {
        executeInsideTransaction(entityManager -> entityManager.merge(t));
    }

    @Override
    public void assignEntity(T entity, String[] params) {

    }

    public void executeInsideTransaction(Consumer<EntityManager> action ) throws NullPointerException {
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
