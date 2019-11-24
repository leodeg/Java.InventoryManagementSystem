package com.main.model.jpa;

import com.main.model.DataAccessObject;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

public class JpaBaseEntityDao<T> implements DataAccessObject<T>{

    @PersistenceContext
    protected EntityManager entityManager;
    EntityManagerFactory managerFactory;

    public JpaBaseEntityDao () {
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
        Transaction.executeInsideTransaction(entityManager -> entityManager.persist(t), entityManager);
    }

    @Override
    public void delete(T t) {
        Transaction.executeInsideTransaction(entityManager -> entityManager.remove(t), entityManager);
    }

    @Override
    public void update(T t, String[] params) {
        assignEntity(t, params);
        Transaction.executeInsideTransaction(entityManager -> entityManager.merge(t), entityManager);
    }

    @Override
    public void assignEntity(T entity, String[] params) {

    }
}
