package com.main.model.entity;

import javax.persistence.*;
import java.text.DecimalFormat;
import java.util.Objects;

@Entity
@Table(name = "products")
public class ProductEntity implements ParentEntity{
    @Column (name = "idType")
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

    public ProductEntity(int idType, String name, Double price) {
        this.idType = idType;
        this.name = name;
        this.price = price;
        this.description = "Empty";
    }

    public ProductEntity(int idType, String name, Double price, String description) {
        this.idType = idType;
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

    @Override
    public String toString () {
        StringBuilder builder = new StringBuilder();
        builder.append("Id: ").append(idProduct);
        builder.append("; IdType: ").append(idType);
        builder.append("; Name: ").append(name);
        builder.append("; Price: ").append(price);
        builder.append("; Description: ").append(description);
        return builder.toString();
    }

    @Override
    public void assignEntity(String[] params) {
        setIdType(Integer.parseInt(params[0]));
        setName(Objects.requireNonNull(params[1], "Name cannot be null"));
        setPrice(Double.parseDouble(params[2]));
        setDescription(params[2] != null ? params[3] : "Empty");
    }
}
