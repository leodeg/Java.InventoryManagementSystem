package com.main.model.jpa;

import com.main.model.entity.TypeEntity;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class JpaTypeDao extends JpaDataAccessObject<TypeEntity> {
    @Override
    public Optional<TypeEntity> get(int id) {
        return Optional.ofNullable(entityManager.find(TypeEntity.class, id));
    }

    @Override
    public List<TypeEntity> getAll() {
        return entityManager.createQuery("SELECT e FROM TypeEntity e").getResultList();
    }

    @Override
    public void assignEntity(TypeEntity entity, String[] params) {
        entity.setTitle(Objects.requireNonNull(params[0], "Title cannot be null"));
    }
}
