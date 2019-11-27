package com.main.model.jpa;

import java.util.List;
import java.util.Optional;

public class JpaOrderDao extends JpaDataAccessObject<OrderEntity> {
    @Override
    public Optional<OrderEntity> get(int id) {
        return Optional.ofNullable(entityManager.find(OrderEntity.class, id));
    }

    @Override
    public List<OrderEntity> getAll() {
        return entityManager.createQuery("SELECT e FROM OrderEntity e").getResultList();
    }

    @Override
    public void assignEntity(OrderEntity entity, String[] params) {
        entity.assignEntity(params);
    }
}