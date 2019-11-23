package com.main.model.entity;

import javax.persistence.*;
import java.sql.Date;
import java.time.format.DateTimeFormatter;

@Entity
public class InventoryEntity {
    @Column (name = "idProduct")
    private int idProduct;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column (name = "amount")
    private int amount;
    @Column (name = "price")
    private Double price;
    @Temporal(TemporalType.DATE)
    private Date date;

    public InventoryEntity(int idProduct, int id, int amount, Date date) {
        this.idProduct = idProduct;
        this.id = id;
        this.amount = amount;
        this.date = date;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
