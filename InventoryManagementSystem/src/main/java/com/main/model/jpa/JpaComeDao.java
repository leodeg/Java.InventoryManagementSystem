package com.main.model.jpa;

import com.main.model.entity.ComeEntity;
import com.main.model.entity.InventoryBaseEntity;

import java.sql.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class JpaComeDao extends JpaDataAccessObject<ComeEntity> {
    @Override
    public Optional<ComeEntity> get(int id) {
        return Optional.ofNullable(entityManager.find(ComeEntity.class, id));
    }

    @Override
    public List<ComeEntity> getAll() {
        return entityManager.createQuery("SELECT e FROM ComeEntity e").getResultList();
    }

    public void assignEntity(InventoryBaseEntity entity, String[] params) {
        entity.assignEntity(params);
    }
}
