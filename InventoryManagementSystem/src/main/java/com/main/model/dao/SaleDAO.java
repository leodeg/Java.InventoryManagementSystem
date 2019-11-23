package com.main.model.dao;

import com.main.model.entity.SaleEntity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SaleDAO implements DataAccessObject<SaleEntity> {

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
        saleEntity.setIdCustomer(Integer.parseInt(params[0]));
        saleEntity.setIdProduct(Integer.parseInt(params[1]));
        saleEntity.setAmount(Integer.parseInt(params[2]));
        saleEntity.setDate(Date.valueOf(params[3]));
        saleEntity.setTotalPrice(Double.parseDouble(params[4]));
        list.add(saleEntity);
    }

    @Override
    public void delete(SaleEntity saleEntity) {
        list.remove(saleEntity);
    }
}
