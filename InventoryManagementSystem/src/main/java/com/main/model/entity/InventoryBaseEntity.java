package com.main.model.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.Objects;

@MappedSuperclass
public class InventoryBaseEntity implements ParentEntity {
    @Column(name = "idProduct")
    private int idProduct;
    @Column(name = "amount")
    private int amount;
    @Column(name = "price")
    private Double price;
    @Column(name = "totalPrice")
    private Double totalPrice;
    @Temporal(TemporalType.DATE)
    private Date date;

    public InventoryBaseEntity(int idProduct, int amount, Double price, Date date) {
        this.idProduct = idProduct;
        this.amount = amount;
        this.price = price;
        this.date = date;
        this.totalPrice = amount * price;
    }

    public InventoryBaseEntity() {

    }


    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public void assignEntity(String[] params) {
        setIdProduct(Objects.requireNonNull(Integer.parseInt(params[0]), "idProduct cannot be null"));
        setAmount(Objects.requireNonNull(Integer.parseInt(params[1]), "id cannot be null"));
        setPrice(Objects.requireNonNull(Double.parseDouble(params[2]), "Price cannot be null"));
        setDate(Objects.requireNonNull(java.sql.Date.valueOf(params[3]), "Date Date be null"));
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}

