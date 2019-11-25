package com.main.model.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "sales")
public class SaleEntity extends OrderBaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idSale;

    public SaleEntity(int idOrder, int idProduct, int idCustomer, int amount, Date date, Double totalPrice) {
        super(idOrder, idProduct, idCustomer, amount, date, totalPrice);
    }

    public SaleEntity() {
    }

    public int getIdSale() {
        return idSale;
    }

    public void setIdSale(int idSale) {
        this.idSale = idSale;
    }
}
