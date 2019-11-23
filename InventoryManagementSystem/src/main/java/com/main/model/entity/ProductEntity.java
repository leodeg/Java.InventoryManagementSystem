package com.main.model.entity;

import javax.persistence.*;
import java.text.DecimalFormat;

@Entity
@Table(name = "products")
public class ProductEntity {
    private int idType;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idProduct;
    @Column (name = "name")
    private String name;
    @Column (name = "price")
    private Double price;
    @Column (name = "description")
    private String description;


    public ProductEntity(int idType, int idProduct, String name, Double price, String description) {
        this.idType = idType;
        this.idProduct = idProduct;
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public ProductEntity() {
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
