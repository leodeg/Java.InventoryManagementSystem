package com.main.model;

import java.time.format.DateTimeFormatter;

public class Sale {
    private int idSale;
    private int idCustomer;
    private int idProduct;
    private int amount;
    private DateTimeFormatter date;
    private Double totalPrice;

    public Sale(int idSale, int idProduct, int idCustomer, int amount, DateTimeFormatter date, Double totalPrice) {
        this.idSale = idSale;
        this.idCustomer = idCustomer;
        this.idProduct = idProduct;
        this.amount = amount;
        this.date = date;
        this.totalPrice = totalPrice;
    }

    public int getIdSale() {
        return idSale;
    }

    public void setIdSale(int idSale) {
        this.idSale = idSale;
    }

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
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

    public DateTimeFormatter getDate() {
        return date;
    }

    public void setDate(DateTimeFormatter date) {
        this.date = date;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
