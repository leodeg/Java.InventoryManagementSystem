package com.main.database;

import com.main.model.entity.SaleEntity;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class JpaSaleDao extends JpaDataAccessObject<SaleEntity> {
    @Override
    public Optional<SaleEntity> get(int id) {
        return Optional.ofNullable(EntityManagerConnector.entityManager.find(SaleEntity.class, id));
    }

    @Override
    public List<SaleEntity> getAll() {
        return EntityManagerConnector.entityManager.createQuery("SELECT e FROM SaleEntity e", SaleEntity.class).getResultList();
    }

    public List<SaleEntity> getAllByDate(java.sql.Date date) {
        String query = "SELECT e FROM SaleEntity e WHERE e.date = :date";
        return EntityManagerConnector.entityManager.createQuery(query, SaleEntity.class).setParameter("date", date).getResultList();
    }

    public List<SaleEntity> getAllByPrice(double price) {
        String query = "SELECT e FROM SaleEntity e WHERE e.price = :price";
        return EntityManagerConnector.entityManager.createQuery(query, SaleEntity.class).setParameter("price", price).getResultList();
    }

    public List<SaleEntity> getAllByProductName(String productName) {
        String query = "SELECT e FROM SaleEntity e WHERE e.productName = :productName";
        return EntityManagerConnector.entityManager.createQuery(query, SaleEntity.class).setParameter("productName", productName).getResultList();
    }

    public Double getSumTotalPriceByDate(Date date) {
        String query = "SELECT SUM (e.totalPrice) FROM SaleEntity e WHERE e.date = :date";
        return EntityManagerConnector.entityManager.createQuery(query, Double.class).setParameter("date", date).getSingleResult();
    }

    public Long getSumAmountByDate(Date date) {
        String query = "SELECT SUM (e.amount) FROM SaleEntity e WHERE e.date = :date";
        return EntityManagerConnector.entityManager.createQuery(query, Long.class).setParameter("date", date).getSingleResult();
    }

    @Override
    public void assignEntity(SaleEntity entity, String[] params) {
        entity.assignEntity(params);
    }
}
