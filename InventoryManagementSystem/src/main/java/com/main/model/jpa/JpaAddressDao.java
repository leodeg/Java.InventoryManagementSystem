package com.main.model.jpa;

import com.main.model.DataAccessObject;
import com.main.model.entity.AddressEntity;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class JpaAddressDao implements DataAccessObject<AddressEntity> {

    private EntityManager entityManager;

    @Override
    public Optional<AddressEntity> get(int id) {
        return Optional.ofNullable(entityManager.find(AddressEntity.class, id));
    }

    @Override
    public List<AddressEntity> getAll() {
        return entityManager.createQuery("SELECT e FROM AddressEntity e").getResultList();
    }

    @Override
    public void save(AddressEntity addressEntity) {
        Transaction.executeInsideTransaction(entityManager -> entityManager.persist(addressEntity), entityManager);
    }

    @Override
    public void update(AddressEntity addressEntity, String[] params) {
        assignEntity(addressEntity, params);
        Transaction.executeInsideTransaction(entityManager -> entityManager.merge(addressEntity), entityManager);
    }

    public void assignEntity(AddressEntity entity, String[] params) {
        entity.setAddress(Objects.requireNonNull(params[0], "Address cannot be null"));
        entity.setAddress2(Objects.requireNonNull(params[1], "Address2 cannot be null"));
        entity.setCity(Objects.requireNonNull(params[2], "City cannot be null"));
        entity.setRegion(Objects.requireNonNull(params[3], "Region cannot be null"));
    }

    @Override
    public void delete(AddressEntity addressEntity) {
        Transaction.executeInsideTransaction(entityManager -> entityManager.remove(addressEntity), entityManager);
    }
}
