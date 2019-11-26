package com.main.model.dao;

import com.main.model.DataAccessObject;
import com.main.model.entity.CategoryEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class CategoryDao implements DataAccessObject<CategoryEntity> {

    private List<CategoryEntity> list = new ArrayList<>();

    @Override
    public Optional<CategoryEntity> get(int id) {
        return Optional.ofNullable(list.get(id));
    }

    @Override
    public List<CategoryEntity> getAll() {
        return list;
    }

    @Override
    public void save(CategoryEntity categoryEntity) {
        list.add(categoryEntity);
    }

    @Override
    public void update(CategoryEntity categoryEntity, String[] params) {
        assignEntity(categoryEntity, params);
        list.add(categoryEntity);
    }

    @Override
    public void assignEntity(CategoryEntity entity, String[] params) {
        entity.setTitle(Objects.requireNonNull(params[0], "Title cannot be null"));
    }

    @Override
    public void delete(CategoryEntity categoryEntity) {
        list.remove(categoryEntity);
    }
}
