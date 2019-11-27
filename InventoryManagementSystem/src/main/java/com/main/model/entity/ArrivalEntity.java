package com.main.model.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "arrival")
public class ArrivalEntity extends InventoryBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idArrival;

    public ArrivalEntity(int idProduct, int amount, Double price, Date date) {
        super (idProduct, amount, price, date);
    }

    public ArrivalEntity() {
    }

    public int getIdArrival() {
        return idArrival;
    }

    public void setIdArrival(int idCome) {
        this.idArrival = idCome;
    }
}

