package com.main.database;

import com.main.model.entity.CustomerEntity;

import java.util.List;
import java.util.Optional;

public class JpaCustomerDao extends JpaDataAccessObject<CustomerEntity> {
    @Override
    public Optional<CustomerEntity> get(int id) {
        return Optional.ofNullable(EntityManagerConnector.getEntityManager().find(CustomerEntity.class, id));
    }

    @Override
    public List<CustomerEntity> getAll() {
        return EntityManagerConnector.getEntityManager().createQuery("SELECT e FROM CustomerEntity e", CustomerEntity.class).getResultList();
    }

    @Override
    public void assignEntity(CustomerEntity entity, String[] params) {
        entity.assignEntity(params);
    }
}
