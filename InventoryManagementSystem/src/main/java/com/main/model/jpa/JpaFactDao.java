package com.main.model.jpa;

import com.main.model.entity.*;

import java.util.List;
import java.util.Optional;

public class JpaFactDao extends JpaDataAccessObject<FactEntity> {
    @Override
    public Optional<FactEntity> get(int id) {
        return Optional.ofNullable(entityManager.find(FactEntity.class, id));
    }

    @Override
    public List<FactEntity> getAll() {
        return entityManager.createQuery("SELECT e FROM FactEntity e", FactEntity.class).getResultList();
    }

    public List<FactEntity> getByIdProduct (int id) {
        return entityManager.createQuery("SELECT e FROM FactEntity e WHERE e.idProduct = :id", FactEntity.class).setParameter("id", id).getResultList();
    }

    public FactEntity getFirstByIdProduct (int id) {
        return getByIdProduct(id).get(0);
    }

    public void assignEntity(InventoryBaseEntity entity, String[] params) {
        entity.assignEntity(params);
    }
}

