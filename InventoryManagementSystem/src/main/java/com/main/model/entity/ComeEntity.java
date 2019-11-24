package com.main.model.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "come")
public class ComeEntity extends InventoryBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idBegin;

    public ComeEntity(int idProduct, int amount, Double price, Date date) {
        super (idProduct, amount, price, date);
    }

    public int getIdBegin() {
        return idBegin;
    }
}

