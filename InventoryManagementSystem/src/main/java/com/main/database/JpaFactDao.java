package com.main.database;

import com.main.model.entity.FactEntity;
import com.main.model.entity.InventoryBaseEntity;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public class JpaFactDao extends JpaDataAccessObject<FactEntity> {
    @Override
    public Optional<FactEntity> get(int id) {
        return Optional.ofNullable(EntityManagerConnector.getEntityManager().find(FactEntity.class, id));
    }

    @Override
    public List<FactEntity> getAll() {
        return EntityManagerConnector.getEntityManager().createQuery("SELECT e FROM FactEntity e", FactEntity.class).getResultList();
    }

    public List<FactEntity> getAllByDate(Date date) {
        String query = "SELECT e FROM FactEntity e WHERE e.date = :date";
        return EntityManagerConnector.getEntityManager().createQuery(query, FactEntity.class).setParameter("date", date).getResultList();
    }

    public List<FactEntity> getAllByPrice(double price) {
        String query = "SELECT e FROM FactEntity e WHERE e.price = :price";
        return EntityManagerConnector.getEntityManager().createQuery(query, FactEntity.class).setParameter("price", price).getResultList();
    }

    public List<FactEntity> getAllByIdProduct(int idProduct) {
        String query = "SELECT e FROM FactEntity e WHERE e.idProduct = :idProduct";
        return EntityManagerConnector.getEntityManager().createQuery(query, FactEntity.class).setParameter("idProduct", idProduct).getResultList();
    }

    public List<FactEntity> getByIdProduct(int id) {
        return EntityManagerConnector.getEntityManager().createQuery("SELECT e FROM FactEntity e WHERE e.idProduct = :id", FactEntity.class).setParameter("id", id).getResultList();
    }

    public FactEntity getSingleByIdProduct(int id) {
        return EntityManagerConnector.getEntityManager().createQuery("SELECT e FROM FactEntity e WHERE e.idProduct = :id", FactEntity.class).setParameter("id", id).getSingleResult();
    }

    public Long getCount(int idProduct) {
        String query = "SELECT COUNT (e) FROM FactEntity e WHERE e.idProduct = :idProduct";
        return EntityManagerConnector.getEntityManager().createQuery(query, Long.class).setParameter("idProduct", idProduct).getSingleResult();
    }

    public Integer getAmountBy(int idProduct) {
        String query = "SELECT e.amount FROM FactEntity e WHERE e.idProduct = :idProduct";
        return EntityManagerConnector.getEntityManager().createQuery(query, Integer.class).setParameter("idProduct", idProduct).getSingleResult();
    }

    public Double getPriceByProduct(int idProduct) {
        String query = "SELECT DISTINCT e.price FROM FactEntity e WHERE e.idProduct = :idProduct";
        return EntityManagerConnector.getEntityManager().createQuery(query, Double.class).setParameter("idProduct", idProduct).getSingleResult();
    }

    public Double getTotalPriceByProduct(int idProduct) {
        String query = "SELECT DISTINCT e.totalPrice FROM FactEntity e WHERE e.idProduct = :idProduct";
        return EntityManagerConnector.getEntityManager().createQuery(query, Double.class).setParameter("idProduct", idProduct).getSingleResult();
    }

    public Double getTotalPriceByDate(Date date) {
        String query = "SELECT COUNT (e.totalPrice) FROM FactEntity e WHERE e.date = :date";
        return EntityManagerConnector.getEntityManager().createQuery(query, Double.class).setParameter("date", date).getSingleResult();
    }

    public Double getSumTotalPriceByDate(java.util.Date date) {
        String query = "SELECT SUM (e.totalPrice) FROM FactEntity e WHERE e.date = :date";
        return EntityManagerConnector.getEntityManager().createQuery(query, Double.class).setParameter("date", date).getSingleResult();
    }

    public FactEntity getFirstByIdProduct(int id) {
        return getByIdProduct(id).get(0);
    }

    public boolean isExists(int idProduct) {
        return !getByIdProduct(idProduct).isEmpty();
    }

    public void assignEntity(InventoryBaseEntity entity, String[] params) {
        entity.assignEntity(params);
    }
}

