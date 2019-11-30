package com.main.database;

import com.main.model.entity.SaleEntity;

import java.util.List;
import java.util.Optional;

public class JpaSaleDao extends JpaDataAccessObject<SaleEntity> {
    @Override
    public Optional<SaleEntity> get(int id) {
        return Optional.ofNullable(EntityManagerConnector.entityManager.find(SaleEntity.class, id));
    }

    @Override
    public List<SaleEntity> getAll() {
        return EntityManagerConnector.entityManager.createQuery("SELECT e FROM SaleEntity e", SaleEntity.class).getResultList();
    }

    @Override
    public void assignEntity(SaleEntity entity, String[] params) {
        entity.assignEntity(params);
    }
}
