package com.main.model.jpa;

import com.main.model.entity.CategoryEntity;

import java.util.List;
import java.util.Optional;

public class JpaCategoryDao extends JpaDataAccessObject<CategoryEntity> {
    @Override
    public Optional<CategoryEntity> get(int id) {
        return Optional.ofNullable(entityManager.find(CategoryEntity.class, id));
    }

    public CategoryEntity get (String title) {
        return entityManager.createQuery("SELECT e FROM CategoryEntity e WHERE e.title = :title", CategoryEntity.class).setParameter("title", title).getSingleResult();
    }

    @Override
    public List<CategoryEntity> getAll() {
        return entityManager.createQuery("SELECT e FROM CategoryEntity e", CategoryEntity.class).getResultList();
    }

    public List<CategoryEntity> getByTitle (String title) {
        return entityManager.createQuery("SELECT e FROM CategoryEntity e WHERE e.title = :title", CategoryEntity.class).setParameter("title", title).getResultList();
    }

    public void delete (String title) {
        entityManager.createQuery("DELETE FROM CategoryEntity e WHERE e.title = :title").setParameter("title", title);
    }

    public int getIdByTitle (String title) {
        return getByTitle(title).get(0).getIdCategory();
    }

    @Override
    public void assignEntity(CategoryEntity entity, String[] params) {
        entity.assignEntity(params);
    }
}
