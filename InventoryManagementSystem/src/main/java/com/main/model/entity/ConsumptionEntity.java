package com.main.model.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "consumption")
public class ConsumptionEntity implements ParentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idConsumption;
    @Column(name = "productName")
    private String productName;
    @Column (name = "price")
    private Double price;
    @Column (name = "amount")
    private int amount;
    @Column (name = "totalPrice")
    private Double totalPrice;
    @Temporal(TemporalType.DATE)
    private Date date;

    public ConsumptionEntity(String productName, double price, int amount, Date date) {
        this.productName = productName;
        this.price = price;
        this.amount = amount;
        this.date = date;
        this.totalPrice = price * amount;
    }

    public ConsumptionEntity() {
    }

    @Override
    public void assignEntity(String[] params) {
        setProductName(Objects.requireNonNull(params[0], "Product name cannot be null"));
        setPrice(Objects.requireNonNull(Double.parseDouble(params[2]), "Price cannot be null"));
        setAmount(Objects.requireNonNull(Integer.parseInt(params[1]), "id cannot be null"));
        setDate(Objects.requireNonNull(java.sql.Date.valueOf(params[3]), "Date Date be null"));
        setTotalPrice(getPrice() * getAmount());
    }

    public int getIdConsumption() {
        return idConsumption;
    }

    public void setIdConsumption(int idGone) {
        this.idConsumption = idGone;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
