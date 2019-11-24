package com.main.model.entity;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public class InventoryBaseEntity {
    @Column (name = "idProduct")
    private int idProduct;
    @Column (name = "amount")
    private int amount;
    @Column (name = "price")
    private Double price;
    @Temporal(TemporalType.DATE)
    private Date date;

    public InventoryBaseEntity(int idProduct, int amount, Double price, Date date) {
        this.idProduct = idProduct;
        this.amount = amount;
        this.price = price;
        this.date = date;
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
}

