package com.main.model.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "gone")
public class GoneEntity extends InventoryBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idGone;

    public GoneEntity(int idProduct, int amount, Double price, Date date) {
        super (idProduct, amount, price, date);
    }

    public int getIdGone() {
        return idGone;
    }

    public void setIdGone(int idGone) {
        this.idGone = idGone;
    }
}
