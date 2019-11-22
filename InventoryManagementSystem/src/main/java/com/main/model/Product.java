package com.main.model;

import java.text.DecimalFormat;

public class Product {
    private int idType;
    private int idProduct;
    private String name;
    private Double price;
    private String description;


    public Product(int idType, int idProduct, String name, Double price, String description) {
        this.idType = idType;
        this.idProduct = idProduct;
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
