package com.main.model.jpa;

import com.main.model.entity.GoneEntity;
import com.main.model.entity.InventoryBaseEntity;

import java.sql.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class JpaGoneDao extends JpaDataAccessObject<GoneEntity> {
    @Override
    public Optional<GoneEntity> get(int id) {
        return Optional.ofNullable(entityManager.find(GoneEntity.class, id));
    }

    @Override
    public List<GoneEntity> getAll() {
        return entityManager.createQuery("SELECT e FROM GoneEntity e").getResultList();
    }

    public void assignEntity(InventoryBaseEntity entity, String[] params) {
        entity.assignEntity(params);
    }
}
