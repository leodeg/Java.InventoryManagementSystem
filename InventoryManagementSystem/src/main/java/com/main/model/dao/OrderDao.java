package com.main.model.dao;

import com.main.model.DataAccessObject;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderDao implements DataAccessObject<OrderEntity> {

    private List<OrderEntity> list = new ArrayList<>();

    @Override
    public Optional<OrderEntity> get(int id) {
        return Optional.ofNullable(list.get(id));
    }

    @Override
    public List<OrderEntity> getAll() {
        return list;
    }

    @Override
    public void save(OrderEntity orderEntity) {
        list.add(orderEntity);
    }

    @Override
    public void update(OrderEntity orderEntity, String[] params) {
        assignEntity(orderEntity, params);
        list.add(orderEntity);
    }

    @Override
    public void assignEntity(OrderEntity entity, String[] params) {
        entity.setIdCustomer(Integer.parseInt(params[0]));
        entity.setIdProduct(Integer.parseInt(params[1]));
        entity.setAmount(Integer.parseInt(params[2]));
        entity.setDate(Date.valueOf(params[3]));
        entity.setTotalPrice(Double.parseDouble(params[4]));
    }

    @Override
    public void delete(OrderEntity orderEntity) {
        list.remove(orderEntity);
    }
}
