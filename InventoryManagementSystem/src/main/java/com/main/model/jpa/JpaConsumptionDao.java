package com.main.model.jpa;

import com.main.model.entity.ConsumptionEntity;
import com.main.model.entity.InventoryBaseEntity;

import java.util.List;
import java.util.Optional;

public class JpaConsumptionDao extends JpaDataAccessObject<ConsumptionEntity> {
    @Override
    public Optional<ConsumptionEntity> get(int id) {
        return Optional.ofNullable(entityManager.find(ConsumptionEntity.class, id));
    }

    @Override
    public List<ConsumptionEntity> getAll() {
        return entityManager.createQuery("SELECT e FROM ConsumptionEntity e").getResultList();
    }

    public void assignEntity(InventoryBaseEntity entity, String[] params) {
        entity.assignEntity(params);
    }
}
