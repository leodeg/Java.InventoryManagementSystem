package com.main.model.jpa;

import com.main.model.entity.ProductEntity;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class JpaProductDao extends JpaDataAccessObject<ProductEntity> {
    @Override
    public Optional<ProductEntity> get(int id) {
        return Optional.ofNullable(entityManager.find(ProductEntity.class, id));
    }

    @Override
    public List<ProductEntity> getAll() {
        return entityManager.createQuery("SELECT e FROM ProductEntity e").getResultList();
    }

    @Override
    public void assignEntity(ProductEntity entity, String[] params) {
        entity.assignEntity(params);
    }
}
