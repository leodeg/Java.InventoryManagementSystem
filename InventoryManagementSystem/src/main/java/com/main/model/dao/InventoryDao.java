package com.main.model.dao;

import com.main.model.DataAccessObject;
import com.main.model.entity.InventoryBaseEntity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class InventoryDao implements DataAccessObject<InventoryBaseEntity> {

    private List<InventoryBaseEntity> list = new ArrayList<>();

    @Override
    public Optional<InventoryBaseEntity> get(int id) {
        return Optional.ofNullable(list.get(id));
    }

    @Override
    public List<InventoryBaseEntity> getAll() {
        return list;
    }

    @Override
    public void save(InventoryBaseEntity inventoryBaseEntity) {
        list.add(inventoryBaseEntity);
    }

    @Override
    public void update(InventoryBaseEntity inventoryBaseEntity, String[] params) {
        assignEntity(inventoryBaseEntity, params);
        list.add(inventoryBaseEntity);
    }

    @Override
    public void assignEntity(InventoryBaseEntity entity, String[] params) {
        entity.setIdProduct(Objects.requireNonNull(Integer.parseInt(params[0]), "idProduct cannot be null"));
        entity.setAmount(Objects.requireNonNull(Integer.parseInt(params[1]), "id cannot be null"));
        entity.setPrice(Objects.requireNonNull(Double.parseDouble(params[2]), "Price cannot be null"));
        entity.setDate(Objects.requireNonNull(Date.valueOf(params[3]), "Date Date be null"));
    }

    @Override
    public void delete(InventoryBaseEntity inventoryBaseEntity) {
        list.remove(inventoryBaseEntity);
    }
}
