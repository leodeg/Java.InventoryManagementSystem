package com.main.database.jpa;

import com.main.model.entity.OrderEntity;

import java.util.List;
import java.util.Optional;

public class JpaOrderDao extends JpaDataAccessObject<OrderEntity> {
    @Override
    public Optional<OrderEntity> get(int id) {
        return Optional.ofNullable(EntityManagerConnector.entityManager.find(OrderEntity.class, id));
    }

    @Override
    public List<OrderEntity> getAll() {
        return EntityManagerConnector.entityManager.createQuery("SELECT e FROM OrderEntity e", OrderEntity.class).getResultList();
    }

    @Override
    public void assignEntity(OrderEntity entity, String[] params) {
        entity.assignEntity(params);
    }
}