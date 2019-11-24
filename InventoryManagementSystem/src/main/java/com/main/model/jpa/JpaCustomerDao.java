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
        entity.setName(Objects.requireNonNull(params[0], "Name cannot be null"));
        entity.setPhone(Objects.requireNonNull(params[1], "Phone cannot be null"));
        entity.setPhone2(params[2] != null ? params[2] : "Empty");
        entity.setDescription(params[3] != null ? params[3] : "Empty");
    }
}
