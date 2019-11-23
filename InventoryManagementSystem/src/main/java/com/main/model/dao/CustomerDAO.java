package com.main.model.dao;

import com.main.model.entity.CustomerEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class CustomerDAO implements DataAccessObject<CustomerEntity> {

    private List<CustomerEntity> list = new ArrayList<>();

    @Override
    public Optional<CustomerEntity> get(int id) {
        return Optional.ofNullable(list.get(id));
    }

    @Override
    public List<CustomerEntity> getAll() {
        return list;
    }

    @Override
    public void save(CustomerEntity customerEntity) {
        list.add(customerEntity);
    }

    @Override
    public void update(CustomerEntity customerEntity, String[] params) {
        customerEntity.setName(Objects.requireNonNull(params[0], "Name cannot be null"));
        customerEntity.setPhone(Objects.requireNonNull(params[1], "Phone cannot be null"));
        customerEntity.setPhone2(params[2] != null ? params[2] : "Empty");
        customerEntity.setDescription(params[3] != null ? params[3] : "Empty");
        list.add(customerEntity);
    }

    @Override
    public void delete(CustomerEntity customerEntity) {
        list.remove(customerEntity);
    }
}
