package com.main.model.jpa;

import com.main.model.entity.CustomerEntity;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class JpaCustomerDao extends JpaDataAccessObject<CustomerEntity> {
    @Override
    public Optional<CustomerEntity> get(int id) {
        return Optional.ofNullable(entityManager.find(CustomerEntity.class, id));
    }

    @Override
    public List<CustomerEntity> getAll() {
        return entityManager.createQuery("SELECT e FROM CustomerEntity e").getResultList();
    }

    @Override
    public void assignEntity(CustomerEntity entity, String[] params) {
        entity.assignEntity(params);
    }
}
