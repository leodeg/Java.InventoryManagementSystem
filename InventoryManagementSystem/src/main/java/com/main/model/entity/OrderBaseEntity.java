package com.main.model.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
public class OrderBaseEntity implements ParentEntity {
    @Column(name = "idCustomer")
    private int idCustomer;
    @Column (name = "idProduct")
    private int idProduct;
    @Column (name = "amount")
    private int amount;
    @Column (name = "date")
    private Date date;
    @Column (name = "totalPrice")
    private Double totalPrice;

    public OrderBaseEntity(int idOrder, int idProduct, int idCustomer, int amount, Date date, Double totalPrice) {
        this.idCustomer = idCustomer;
        this.idProduct = idProduct;
        this.amount = amount;
        this.date = date;
        this.totalPrice = totalPrice;
    }

    public OrderBaseEntity() {
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
        setAmount(Integer.parseInt(params[2]));
        setTotalPrice(Double.parseDouble(params[3]));
        setDate(java.sql.Date.valueOf(params[4]));
    }
}
