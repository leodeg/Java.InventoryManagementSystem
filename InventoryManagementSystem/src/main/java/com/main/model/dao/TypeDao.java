package com.main.model.dao;

import com.main.model.DataAccessObject;
import com.main.model.entity.TypeEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class TypeDao implements DataAccessObject<TypeEntity> {

    private List<TypeEntity> list = new ArrayList<>();

    @Override
    public Optional<TypeEntity> get(int id) {
        return Optional.ofNullable(list.get(id));
    }

    @Override
    public List<TypeEntity> getAll() {
        return list;
    }

    @Override
    public void save(TypeEntity typeEntity) {
        list.add(typeEntity);
    }

    @Override
    public void update(TypeEntity typeEntity, String[] params) {
        assignEntity(typeEntity, params);
        list.add(typeEntity);
    }

    @Override
    public void assignEntity(TypeEntity entity, String[] params) {
        entity.setTitle(Objects.requireNonNull(params[0], "Title cannot be null"));
    }

    @Override
    public void delete(TypeEntity typeEntity) {
        list.remove(typeEntity);
    }
}
