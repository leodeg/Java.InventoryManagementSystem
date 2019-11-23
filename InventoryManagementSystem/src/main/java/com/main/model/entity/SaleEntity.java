package com.main.model.entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "sales")
public class SaleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idSale;
    @Column(name = "idCustomer")
    private int idCustomer;
    @Column(name = "idProduct")
    private int idProduct;
    @Column(name = "amount")
    private int amount;
    @Column(name = "date")
    private Date date;
    @Column(name = "totalPrice")
    private Double totalPrice;

    public SaleEntity(int idSale, int idProduct, int idCustomer, int amount, Date date, Double totalPrice) {
        this.idSale = idSale;
        this.idCustomer = idCustomer;
        this.idProduct = idProduct;
        this.amount = amount;
        this.date = date;
        this.totalPrice = totalPrice;
    }

    public SaleEntity() {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getTotalPrice() { return totalPrice; }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
