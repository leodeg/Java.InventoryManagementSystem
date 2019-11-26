package com.main.model.jpa;

import com.main.model.entity.CategoryEntity;

import java.util.List;
import java.util.Optional;

public class JpaCategoryDao extends JpaDataAccessObject<CategoryEntity> {
    @Override
    public Optional<CategoryEntity> get(int id) {
        return Optional.ofNullable(entityManager.find(CategoryEntity.class, id));
    }

    @Override
    public List<CategoryEntity> getAll() {
        return entityManager.createQuery("SELECT e FROM CategoryEntity e").getResultList();
    }

    @Override
    public void assignEntity(CategoryEntity entity, String[] params) {
        entity.assignEntity(params);
    }
}
