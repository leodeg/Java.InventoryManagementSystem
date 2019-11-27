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
        return entityManager.createQuery("SELECT e FROM FactEntity e").getResultList();
    }

    public void assignEntity(InventoryBaseEntity entity, String[] params) {
        entity.assignEntity(params);
    }
}

