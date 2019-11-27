package com.main.model.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "fact")
public class FactEntity extends InventoryBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idFact;

    public FactEntity(int idProduct, int amount, Double price, Date date) {
        super(idProduct, amount, price, date);
    }

    public FactEntity() {
        super();
    }

    public int getIdFact() {
        return idFact;
    }

    public void setIdFact(int idBegin) {
        this.idFact = idBegin;
    }
}

