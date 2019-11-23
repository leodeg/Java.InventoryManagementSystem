package com.main.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "types")
public class TypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idType;
    @Column (name = "title")
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
}
