package com.main.database;

import com.main.model.entity.AddressEntity;

import java.util.List;
import java.util.Optional;

public class JpaAddressDao extends JpaDataAccessObject<AddressEntity> {
    @Override
    public Optional<AddressEntity> get(int id) {
        return Optional.ofNullable(EntityManagerConnector.entityManager.find(AddressEntity.class, id));
    }

    @Override
    public List<AddressEntity> getAll() {
        return EntityManagerConnector.entityManager.createQuery("SELECT e FROM AddressEntity e", AddressEntity.class).getResultList();
    }

    public List<AddressEntity> getAll(String address) {
        return EntityManagerConnector.entityManager.createQuery("SELECT e from AddressEntity e WHERE e.address = :address", AddressEntity.class).setParameter("address", address).getResultList();
    }

    public AddressEntity getFirst(String address) {
        AddressEntity addressEntity = getAll(address).get(0);
        return addressEntity;
    }

    public void assignEntity(AddressEntity entity, String[] params) {
        entity.assignEntity(params);
    }
}
