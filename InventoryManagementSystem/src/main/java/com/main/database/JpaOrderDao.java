package com.main.database;

import com.main.model.entity.OrderEntity;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class JpaOrderDao extends JpaDataAccessObject<OrderEntity> {
    @Override
    public Optional<OrderEntity> get(int id) {
        return Optional.ofNullable(EntityManagerConnector.getEntityManager().find(OrderEntity.class, id));
    }

    @Override
    public List<OrderEntity> getAll() {
        return EntityManagerConnector.getEntityManager().createQuery("SELECT e FROM OrderEntity e", OrderEntity.class).getResultList();
    }

    public List<OrderEntity> getAllByDate(java.sql.Date date) {
        String query = "SELECT e FROM OrderEntity e WHERE e.date = :date";
        return EntityManagerConnector.getEntityManager().createQuery(query, OrderEntity.class).setParameter("date", date).getResultList();
    }

    public List<OrderEntity> getAllByPrice(double price) {
        String query = "SELECT e FROM OrderEntity e WHERE e.price = :price";
        return EntityManagerConnector.getEntityManager().createQuery(query, OrderEntity.class).setParameter("price", price).getResultList();
    }

    public List<OrderEntity> getAllByIdProduct(int idProduct) {
        String query = "SELECT e FROM OrderEntity e WHERE e.idProduct = :idProduct";
        return EntityManagerConnector.getEntityManager().createQuery(query, OrderEntity.class).setParameter("idProduct", idProduct).getResultList();
    }

    public Double getSumTotalPriceByDate(Date date) {
        String query = "SELECT SUM (e.totalPrice) FROM OrderEntity e WHERE e.date = :date";
        return EntityManagerConnector.getEntityManager().createQuery(query, Double.class).setParameter("date", date).getSingleResult();
    }

    public Long getSumAmountByDate(Date date) {
        String query = "SELECT SUM (e.amount) FROM OrderEntity e WHERE e.date = :date";
        return EntityManagerConnector.getEntityManager().createQuery(query, Long.class).setParameter("date", date).getSingleResult();
    }

    @Override
    public void assignEntity(OrderEntity entity, String[] params) {
        entity.assignEntity(params);
    }
}