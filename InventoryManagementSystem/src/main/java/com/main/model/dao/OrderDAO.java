package com.main.model.dao;

import com.main.model.entity.OrderEntity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class OrderDAO implements DataAccessObject<OrderEntity> {

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
        orderEntity.setIdCustomer(Integer.parseInt(params[0]));
        orderEntity.setIdProduct(Integer.parseInt(params[1]));
        orderEntity.setAmount(Integer.parseInt(params[2]));
        orderEntity.setDate(Date.valueOf(params[3]));
        orderEntity.setTotalPrice(Double.parseDouble(params[4]));
        list.add(orderEntity);
    }

    @Override
    public void delete(OrderEntity orderEntity) {
        list.remove(orderEntity);
    }
}
