package com.main.model.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "types")
public class TypeEntity implements ParentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idType;
    @Column(name = "title")
    private String title;

    public TypeEntity(int idType, String title) {
        this.idType = idType;
        this.title = title;
    }

    public TypeEntity() {
    }

    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
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
}
