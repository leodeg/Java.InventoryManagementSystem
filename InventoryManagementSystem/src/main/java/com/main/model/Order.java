package com.main.model;

import java.time.format.DateTimeFormatter;

public class Order {
    private int idOrder;
    private int idCustomer;
    private int idProduct;
    private int amount;
    private DateTimeFormatter date;
    private Double totalPrice;

    public Order(int idOrder, int idProduct, int idCustomer, int amount, DateTimeFormatter date, Double totalPrice) {
        this.idOrder = idOrder;
        this.idCustomer = idCustomer;
        this.idProduct = idProduct;
        this.amount = amount;
        this.date = date;
        this.totalPrice = totalPrice;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
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
