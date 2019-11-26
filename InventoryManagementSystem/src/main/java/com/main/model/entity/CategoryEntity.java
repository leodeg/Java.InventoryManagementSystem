package com.main.model.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "categories")
public class CategoryEntity implements ParentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idCategory;
    @Column(name = "title")
    private String title;

    public CategoryEntity(String title) {
        this.title = title;
    }

    public CategoryEntity() {
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idType) {
        this.idCategory = idType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void assignEntity(String[] params) {
        setTitle(Objects.requireNonNull(params[0], "Title cannot be null"));
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Id: ").append(idCategory);
        builder.append("; Title: ").append(title);
        return builder.toString();
    }
}

