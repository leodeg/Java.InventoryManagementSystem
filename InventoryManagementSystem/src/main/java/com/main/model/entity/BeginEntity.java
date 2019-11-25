package com.main.model.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "begin")
public class BeginEntity extends InventoryBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idBegin;

    public BeginEntity(int idProduct, int amount, Double price, Date date) {
        super (idProduct, amount, price, date);
    }

    public int getIdBegin() {
        return idBegin;
    }
}
