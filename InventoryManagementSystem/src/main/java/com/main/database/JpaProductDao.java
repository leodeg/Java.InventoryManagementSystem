package com.main.database;

import com.main.model.entity.ProductEntity;

import java.util.List;
import java.util.Optional;

public class JpaProductDao extends JpaDataAccessObject<ProductEntity> {
    @Override
    public Optional<ProductEntity> get(int id) {
        return Optional.ofNullable(EntityManagerConnector.entityManager.find(ProductEntity.class, id));
    }

    @Override
    public List<ProductEntity> getAll() {
        return EntityManagerConnector.entityManager.createQuery("SELECT e FROM ProductEntity e", ProductEntity.class).getResultList();
    }

    public List<ProductEntity> getAllByName(String name) {
        String query = "SELECT e FROM ProductEntity e WHERE e.name = :name";
        return EntityManagerConnector.entityManager.createQuery(query, ProductEntity.class).setParameter("name", name).getResultList();
    }

    public List<ProductEntity> getAllByPrice(double price) {
        String query = "SELECT e FROM ProductEntity e WHERE e.price = :price";
        return EntityManagerConnector.entityManager.createQuery(query, ProductEntity.class).setParameter("price", price).getResultList();
    }

    public Long getCount(int idCategory) {
        String query = "SELECT COUNT (e) FROM ProductEntity e WHERE e.idCategory = :idCategory";
        return EntityManagerConnector.entityManager.createQuery(query, Long.class).setParameter("idCategory", idCategory).getSingleResult();
    }

    public List<Double> getAllPrices() {
        String query = "SELECT DISTINCT e.price FROM ProductEntity e";
        return EntityManagerConnector.entityManager.createQuery(query, Double.class).getResultList();
    }

    public Long getCountByPrice(Double price) {
        String query = "SELECT COUNT(e) FROM ProductEntity e WHERE e.price = :price";
        return EntityManagerConnector.entityManager.createQuery(query, Long.class).setParameter("price", price).getSingleResult();
    }

    public List<String> getAllNames() {
        String query = "SELECT DISTINCT e.name FROM ProductEntity e";
        return EntityManagerConnector.entityManager.createQuery(query, String.class).getResultList();
    }

    public Long getCountByName(String name) {
        String query = "SELECT COUNT (e.name) FROM ProductEntity e WHERE e.name = :name";
        return EntityManagerConnector.entityManager.createQuery(query, Long.class).setParameter("name", name).getSingleResult();
    }

    public Double getPriceByName(String name) {
        String query = "SELECT DISTINCT e.price FROM ProductEntity e WHERE e.name = :name";
        return EntityManagerConnector.entityManager.createQuery(query, Double.class).setParameter("name", name).getSingleResult();
    }

    @Override
    public void assignEntity(ProductEntity entity, String[] params) {
        entity.assignEntity(params);
    }
}
