package com.main.database;

import com.main.model.entity.ArrivalEntity;
import com.main.model.entity.InventoryBaseEntity;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class JpaArrivalDao extends JpaDataAccessObject<ArrivalEntity> {
    @Override
    public Optional<ArrivalEntity> get(int id) {
        return Optional.ofNullable(EntityManagerConnector.entityManager.find(ArrivalEntity.class, id));
    }

    @Override
    public List<ArrivalEntity> getAll() {
        return EntityManagerConnector.entityManager.createQuery("SELECT e FROM ArrivalEntity e", ArrivalEntity.class).getResultList();
    }

    public Double getSumTotalPriceByDate(Date date) {
        String query = "SELECT SUM (e.totalPrice) FROM ArrivalEntity e WHERE e.date = :date";
        return EntityManagerConnector.entityManager.createQuery(query, Double.class).setParameter("date", date).getSingleResult();
    }

    public Long getSumAmountByDate(Date date) {
        String query = "SELECT SUM (e.amount) FROM ArrivalEntity e WHERE e.date = :date";
        return EntityManagerConnector.entityManager.createQuery(query, Long.class).setParameter("date", date).getSingleResult();
    }

    public void assignEntity(InventoryBaseEntity entity, String[] params) {
        entity.assignEntity(params);
    }
}
