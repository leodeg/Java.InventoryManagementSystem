package com.main.model.dao;

import com.main.DataAccessObject;
import com.main.model.entity.ParentEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DaoEntityContainer<TInventory extends ParentEntity> implements DataAccessObject<TInventory> {
    private List<TInventory> list = new ArrayList<>();

    @Override
    public Optional get(int id) {
        return Optional.ofNullable(list.get(id));
    }

    @Override
    public List getAll() {
        return list;
    }

    @Override
    public void save(TInventory entity) {
        list.add(entity);
    }

    @Override
    public void delete(TInventory entity) {
        list.remove(entity);
    }

    @Override
    public void update(TInventory entity) {
        list.add(entity);
    }

    @Override
    public void update(TInventory entity, String[] params) {
        assignEntity(entity, params);
        list.add(entity);
    }

    @Override
    public void assignEntity(TInventory entity, String[] params) {
        entity.assignEntity(params);
    }
}
