package com.main.model.jpa;

import com.main.model.entity.EndEntity;
import com.main.model.entity.InventoryBaseEntity;

import java.sql.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class JpaEndDao extends JpaDataAccessObject<EndEntity> {
    @Override
    public Optional<EndEntity> get(int id) {
        return Optional.ofNullable(entityManager.find(EndEntity.class, id));
    }

    @Override
    public List<EndEntity> getAll() {
        return entityManager.createQuery("SELECT e FROM EndEntity e").getResultList();
    }

    public void assignEntity(InventoryBaseEntity entity, String[] params) {
        entity.setIdProduct(Objects.requireNonNull(Integer.parseInt(params[0]), "idProduct cannot be null"));
        entity.setAmount(Objects.requireNonNull(Integer.parseInt(params[1]), "id cannot be null"));
        entity.setPrice(Objects.requireNonNull(Double.parseDouble(params[2]), "Price cannot be null"));
        entity.setDate(Objects.requireNonNull(Date.valueOf(params[3]), "Date Date be null"));
    }
}
