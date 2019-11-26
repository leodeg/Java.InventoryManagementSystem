package com.main.model.jpa;

import com.main.model.entity.AddressEntity;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class JpaAddressDao extends JpaDataAccessObject<AddressEntity> {
    @Override
    public Optional<AddressEntity> get(int id) {
        return Optional.ofNullable(entityManager.find(AddressEntity.class, id));
    }

    @Override
    public List<AddressEntity> getAll() {
        return entityManager.createQuery("SELECT e FROM AddressEntity e", AddressEntity.class).getResultList();
    }

    public List<AddressEntity> getAll (String address) {
        return entityManager.createQuery("SELECT e from AddressEntity e WHERE e.address = :address", AddressEntity.class).setParameter("address", address).getResultList();
    }

    public AddressEntity getFirst (String address) {
        return getAll(address).get(0);
    }

    public void assignEntity(AddressEntity entity, String[] params) {
        entity.assignEntity(params);
    }
}
