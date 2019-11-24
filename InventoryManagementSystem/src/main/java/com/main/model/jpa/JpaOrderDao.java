package com.main.model.jpa;

import com.main.model.DataAccessObject;
import com.main.model.entity.OrderEntity;
import com.main.model.entity.SaleEntity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JpaOrderDao extends JpaDataAccessObject<OrderEntity> {
    @Override
    public Optional<OrderEntity> get(int id) {
        return Optional.ofNullable(entityManager.find(OrderEntity.class, id));
    }

    @Override
    public List<OrderEntity> getAll() {
        return entityManager.createQuery("SELECT e FROM OrderEntity e").getResultList();
    }

    @Override
    public void assignEntity(OrderEntity entity, String[] params) {
        entity.setIdCustomer(Integer.parseInt(params[0]));
        entity.setIdProduct(Integer.parseInt(params[1]));
        entity.setAmount(Integer.parseInt(params[2]));
        entity.setDate(Date.valueOf(params[3]));
        entity.setTotalPrice(Double.parseDouble(params[4]));
    }
}