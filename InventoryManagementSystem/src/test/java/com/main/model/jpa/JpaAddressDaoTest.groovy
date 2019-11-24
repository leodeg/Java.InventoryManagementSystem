package com.main.model.jpa

import com.main.model.entity.AddressEntity
import groovy.test.GroovyAssert

class JpaAddressDaoTest extends groovy.util.GroovyTestCase {
    void testGet() {
        JpaAddressDao addressDao = new JpaAddressDao ();

        AddressEntity addressEntity = new AddressEntity("some address", "some address", "city", "region");
        addressDao.save(addressEntity);

        AddressEntity entity = addressDao.get(0);
        GroovyAssert.assertThat(entity != null);
    }

    void testGetAll() {
    }

    void testSave() {
        JpaAddressDao addressDao = new JpaAddressDao ();
        AddressEntity addressEntity = new AddressEntity("some address", "some address", "city", "region");
        addressDao.save(addressEntity);

        AddressEntity entity = addressDao.get(0) as AddressEntity;
        GroovyAssert.assertThat (entity != null);
    }

    void testUpdate() {
    }

    void testDelete() {
    }
}
