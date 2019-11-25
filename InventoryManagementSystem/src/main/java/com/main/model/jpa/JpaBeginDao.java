package com.main.model.jpa;

import com.main.model.entity.*;

import java.sql.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class JpaBeginDao extends JpaDataAccessObject<BeginEntity> {
    @Override
    public Optional<BeginEntity> get(int id) {
        return Optional.ofNullable(entityManager.find(BeginEntity.class, id));
    }

    @Override
    public List<BeginEntity> getAll() {
        return entityManager.createQuery("SELECT e FROM BeginEntity e").getResultList();
    }

    public void assignEntity(InventoryBaseEntity entity, String[] params) {
        entity.assignEntity(params);
    }
}

