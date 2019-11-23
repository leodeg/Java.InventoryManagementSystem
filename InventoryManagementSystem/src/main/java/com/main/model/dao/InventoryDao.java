package com.main.model.dao;

import com.main.model.DataAccessObject;
import com.main.model.entity.InventoryEntity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class InventoryDao implements DataAccessObject<InventoryEntity> {

    private List<InventoryEntity> list = new ArrayList<>();

    @Override
    public Optional<InventoryEntity> get(int id) {
        return Optional.ofNullable(list.get(id));
    }

    @Override
    public List<InventoryEntity> getAll() {
        return list;
    }

    @Override
    public void save(InventoryEntity inventoryEntity) {
        list.add(inventoryEntity);
    }

    @Override
    public void update(InventoryEntity inventoryEntity, String[] params) {
        assignEntity(inventoryEntity, params);
        list.add(inventoryEntity);
    }

    @Override
    public void assignEntity(InventoryEntity entity, String[] params) {
        entity.setIdProduct(Objects.requireNonNull(Integer.parseInt(params[0]), "idProduct cannot be null"));
        entity.setAmount(Objects.requireNonNull(Integer.parseInt(params[1]), "id cannot be null"));
        entity.setPrice(Objects.requireNonNull(Double.parseDouble(params[2]), "Price cannot be null"));
        entity.setDate(Objects.requireNonNull(Date.valueOf(params[3]), "Date Date be null"));
    }

    @Override
    public void delete(InventoryEntity inventoryEntity) {
        list.remove(inventoryEntity);
    }
}
