package com.main.database;

import com.main.model.entity.ConsumptionEntity;
import com.main.model.entity.InventoryBaseEntity;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class JpaConsumptionDao extends JpaDataAccessObject<ConsumptionEntity> {
    @Override
    public Optional<ConsumptionEntity> get(int id) {
        return Optional.ofNullable(EntityManagerConnector.getEntityManager().find(ConsumptionEntity.class, id));
    }

    @Override
    public List<ConsumptionEntity> getAll() {
        return EntityManagerConnector.getEntityManager().createQuery("SELECT e FROM ConsumptionEntity e", ConsumptionEntity.class).getResultList();
    }

    public List<ConsumptionEntity> getAllByDate(java.sql.Date date) {
        String query = "SELECT e FROM ConsumptionEntity e WHERE e.date = :date";
        return EntityManagerConnector.getEntityManager().createQuery(query, ConsumptionEntity.class).setParameter("date", date).getResultList();
    }

    public List<ConsumptionEntity> getAllByPrice(double price) {
        String query = "SELECT e FROM ConsumptionEntity e WHERE e.price = :price";
        return EntityManagerConnector.getEntityManager().createQuery(query, ConsumptionEntity.class).setParameter("price", price).getResultList();
    }

    public List<ConsumptionEntity> getAllByProductName(String productName) {
        String query = "SELECT e FROM ConsumptionEntity e WHERE e.productName = :productName";
        return EntityManagerConnector.getEntityManager().createQuery(query, ConsumptionEntity.class).setParameter("productName", productName).getResultList();
    }

    public Double getSumTotalPriceByDate(Date date) {
        String query = "SELECT SUM (e.totalPrice) FROM ConsumptionEntity e WHERE e.date = :date";
        return EntityManagerConnector.getEntityManager().createQuery(query, Double.class).setParameter("date", date).getSingleResult();
    }

    public Long getSumAmountByDate(Date date) {
        String query = "SELECT SUM (e.amount) FROM ConsumptionEntity e WHERE e.date = :date";
        return EntityManagerConnector.getEntityManager().createQuery(query, Long.class).setParameter("date", date).getSingleResult();
    }

    public void assignEntity(InventoryBaseEntity entity, String[] params) {
        entity.assignEntity(params);
    }
}
