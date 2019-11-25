package com.main.model.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "orders")
public class OrderEntity extends OrderBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idOrder;

    public OrderEntity(int idOrder, int idProduct, int idCustomer, int amount, Date date, Double totalPrice) {
        super(idOrder, idProduct, idCustomer, amount, date, totalPrice);
    }

    public OrderEntity() {
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }
}