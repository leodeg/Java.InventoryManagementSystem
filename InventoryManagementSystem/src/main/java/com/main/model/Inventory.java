package com.main.model;

import java.time.format.DateTimeFormatter;

public class Inventory {
    private int idProduct;
    private int id;
    private int amount;
    private Double price;
    private DateTimeFormatter date;

    public Inventory(int idProduct, int id, int amount, DateTimeFormatter date) {
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

    public DateTimeFormatter getDate() {
        return date;
    }

    public void setDate(DateTimeFormatter date) {
        this.date = date;
    }
}
