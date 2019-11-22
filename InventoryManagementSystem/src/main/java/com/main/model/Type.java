package com.main.model;

public class Type {
    private int idType;
    private String title;

    public Type(int idType, String title) {
        this.idType = idType;
        this.title = title;
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
