package com.main.model.jpa;

import com.main.model.DataAccessObject;
import com.main.model.entity.ProductEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class JpaProductDao implements DataAccessObject<ProductEntity> {

    private List<ProductEntity> list = new ArrayList<>();

    @Override
    public Optional<ProductEntity> get(int id) {
        return Optional.ofNullable(list.get(id));
    }

    @Override
    public List<ProductEntity> getAll() {
        return list;
    }

    @Override
    public void save(ProductEntity productEntity) {
        list.add(productEntity);
    }

    @Override
    public void update(ProductEntity productEntity, String[] params) {
        assignEntity(productEntity, params);
        list.add(productEntity);
    }

    @Override
    public void assignEntity(ProductEntity entity, String[] params) {
        entity.setName(Objects.requireNonNull(params[0], "idProduct cannot be null"));
        entity.setPrice(Double.parseDouble(params[1]));
        entity.setDescription(params[2] != null ? params[2] : "Empty");
    }

    @Override
    public void delete(ProductEntity productEntity) {
        list.remove(productEntity);
    }
}
