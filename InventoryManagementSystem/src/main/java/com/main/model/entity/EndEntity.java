package com.main.model.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "end")
public class EndEntity extends InventoryBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idEnd;

    public EndEntity(int idProduct, int amount, Double price, Date date) {
        super(idProduct, amount, price, date);
    }

    public int getIdEnd() {
        return idEnd;
    }

    public void setIdEnd(int idBegin) {
        this.idEnd = idBegin;
    }
}

