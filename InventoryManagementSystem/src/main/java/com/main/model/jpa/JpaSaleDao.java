package com.main.model.jpa;

import com.main.model.DataAccessObject;
import com.main.model.entity.SaleEntity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JpaSaleDao implements DataAccessObject<SaleEntity> {

    private  List<SaleEntity> list = new ArrayList<>();

    @Override
    public Optional<SaleEntity> get(int id) {
        return Optional.ofNullable(list.get(id));
    }

    @Override
    public List<SaleEntity> getAll() {
        return list;
    }

    @Override
    public void save(SaleEntity saleEntity) {
        list.add(saleEntity);
    }

    @Override
    public void update(SaleEntity saleEntity, String[] params) {
        assignEntity(saleEntity, params);
        list.add(saleEntity);
    }

    @Override
    public void assignEntity(SaleEntity entity, String[] params) {
        entity.setIdCustomer(Integer.parseInt(params[0]));
        entity.setIdProduct(Integer.parseInt(params[1]));
        entity.setAmount(Integer.parseInt(params[2]));
        entity.setDate(Date.valueOf(params[3]));
        entity.setTotalPrice(Double.parseDouble(params[4]));
    }

    @Override
    public void delete(SaleEntity saleEntity) {
        list.remove(saleEntity);
    }
}
