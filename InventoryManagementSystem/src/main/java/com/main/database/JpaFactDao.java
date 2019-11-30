package com.main.database;

import com.main.model.entity.FactEntity;
import com.main.model.entity.InventoryBaseEntity;

import java.util.List;
import java.util.Optional;

public class JpaFactDao extends JpaDataAccessObject<FactEntity> {
    @Override
    public Optional<FactEntity> get(int id) {
        return Optional.ofNullable(EntityManagerConnector.entityManager.find(FactEntity.class, id));
    }

    @Override
    public List<FactEntity> getAll() {
        return EntityManagerConnector.entityManager.createQuery("SELECT e FROM FactEntity e", FactEntity.class).getResultList();
    }

    public List<FactEntity> getByIdProduct(int id) {
        return EntityManagerConnector.entityManager.createQuery("SELECT e FROM FactEntity e WHERE e.idProduct = :id", FactEntity.class).setParameter("id", id).getResultList();
    }

    public FactEntity getFirstByIdProduct(int id) {
        return getByIdProduct(id).get(0);
    }

    public boolean isExists(int idProduct) {
        return !getByIdProduct(idProduct).isEmpty();
    }

    public void assignEntity(InventoryBaseEntity entity, String[] params) {
        entity.assignEntity(params);
    }
}

