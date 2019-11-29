package com.main.model.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "orders")
public class OrderEntity implements ParentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idOrder;
    @Column(name = "idCustomer")
    private int idCustomer;
    @Column(name = "idProduct")
    private int idProduct;
    @Column(name = "price")
    private double price;
    @Column(name = "amount")
    private int amount;
    @Column(name = "totalPrice")
    private Double totalPrice;
    @Column(name = "date")
    private Date date;

    public OrderEntity(int idProduct, int idCustomer, double price, int amount, Date date) {
        this.idCustomer = idCustomer;
        this.idProduct = idProduct;
        this.price = price;
        this.amount = amount;
        this.totalPrice = price * amount;
        this.date = date;
    }

    public OrderEntity() {
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public void assignEntity(String[] params) {
        setIdCustomer(Integer.parseInt(params[0]));
        setIdProduct(Integer.parseInt(params[1]));
        setPrice(Integer.parseInt(params[2]));
        setAmount(Integer.parseInt(params[3]));
        setDate(java.sql.Date.valueOf(params[4]));
        setTotalPrice(getPrice() * getAmount());
    }
}

