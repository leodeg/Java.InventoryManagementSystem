package com.main.model.jpa;

import com.main.model.entity.AddressEntity;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class JpaAddressDao extends JpaBaseEntityDao<AddressEntity> {
    public JpaAddressDao() {
        super();
    }

    @Override
    public Optional<AddressEntity> get(int id) {
        return Optional.ofNullable(entityManager.find(AddressEntity.class, id));
    }

    @Override
    public List<AddressEntity> getAll() {
        return entityManager.createQuery("SELECT e FROM AddressEntity e").getResultList();
    }

    public void assignEntity(AddressEntity entity, String[] params) {
        entity.setAddress(Objects.requireNonNull(params[0], "Address cannot be null"));
        entity.setAddress2(Objects.requireNonNull(params[1], "Address2 cannot be null"));
        entity.setCity(Objects.requireNonNull(params[2], "City cannot be null"));
        entity.setRegion(Objects.requireNonNull(params[3], "Region cannot be null"));
    }
}
