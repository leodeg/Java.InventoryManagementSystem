package com.main.model.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "sales")
public class SaleEntity implements ParentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idSale;
    @Column(name = "customerName")
    private String customerName;
    @Column(name = "productName")
    private String productName;
    @Column(name = "price")
    private double price;
    @Column(name = "amount")
    private int amount;
    @Column(name = "totalPrice")
    private double totalPrice;
    @Column(name = "date")
    private Date date;

    public SaleEntity(String productName, String customerName, double price, int amount, Date date) {
        this.productName = productName;
        this.customerName = customerName;
        this.price = price;
        this.amount = amount;
        this.totalPrice = price * amount;
        this.date = date;
    }

    public SaleEntity() {
    }

    @Override
    public void assignEntity(String[] params) {
        setCustomerName(Objects.requireNonNull(params[0], "Customer name cannot be null"));
        setProductName(Objects.requireNonNull(params[1], "Product name cannot be null"));
        setPrice(Integer.parseInt(params[2]));
        setAmount(Integer.parseInt(params[3]));
        setDate(java.sql.Date.valueOf(params[4]));
        setTotalPrice(getPrice() * getAmount());
    }

    public int getIdSale() {
        return idSale;
    }

    public void setIdSale(int idSale) {
        this.idSale = idSale;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
