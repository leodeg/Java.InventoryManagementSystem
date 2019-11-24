package com.main.model.jpa;

import com.main.model.entity.SaleEntity;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public class JpaSaleDao extends JpaDataAccessObject<SaleEntity> {
    @Override
    public Optional<SaleEntity> get(int id) {
        return Optional.ofNullable(entityManager.find(SaleEntity.class, id));
    }

    @Override
    public List<SaleEntity> getAll() {
        return entityManager.createQuery("SELECT e FROM SaleEntity e").getResultList();
    }

    @Override
    public void assignEntity(SaleEntity entity, String[] params) {
        entity.setIdCustomer(Integer.parseInt(params[0]));
        entity.setIdProduct(Integer.parseInt(params[1]));
        entity.setAmount(Integer.parseInt(params[2]));
        entity.setDate(Date.valueOf(params[3]));
        entity.setTotalPrice(Double.parseDouble(params[4]));
    }
}
