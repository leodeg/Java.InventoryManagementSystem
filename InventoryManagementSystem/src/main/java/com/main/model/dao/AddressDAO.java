package com.main.model.dao;

import com.main.model.entity.AddressEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class AddressDAO implements DataAccessObject<AddressEntity> {

    private List<AddressEntity> list = new ArrayList<>();

    @Override
    public Optional<AddressEntity> get(int id) {
        return Optional.ofNullable(list.get(id));
    }

    @Override
    public List<AddressEntity> getAll() {
        return list;
    }

    @Override
    public void save(AddressEntity addressEntity) {
        list.add(addressEntity);
    }

    @Override
    public void update(AddressEntity addressEntity, String[] params) {
        addressEntity.setAddress(Objects.requireNonNull(params[0], "Address cannot be null"));
        addressEntity.setAddress2(Objects.requireNonNull(params[1], "Address2 cannot be null"));
        addressEntity.setCity(Objects.requireNonNull(params[2], "City cannot be null"));
        addressEntity.setRegion(Objects.requireNonNull(params[3], "Region cannot be null"));
        list.add(addressEntity);
    }

    @Override
    public void delete(AddressEntity addressEntity) {
        list.remove(addressEntity);
    }
}
