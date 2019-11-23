package com.main.model.dao;

import com.main.model.entity.TypeEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class TypeDAO implements DataAccessObject<TypeEntity> {

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
        typeEntity.setTitle(Objects.requireNonNull(params[0], "Title cannot be null"));
        list.add(typeEntity);
    }

    @Override
    public void delete(TypeEntity typeEntity) {
        list.remove(typeEntity);
    }
}
