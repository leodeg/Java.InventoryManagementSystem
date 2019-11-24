package com.main.model.entity;

import javax.persistence.*;

@Entity
@Table (name = "addresses")
public class AddressEntity {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private int idAddress;
    @Column (name = "address")
    private String address;
    @Column (name = "address2")
    private String address2;
    @Column (name = "city")
    private String city;
    @Column (name = "region")
    private String region;

    public AddressEntity(String address, String address2, String city, String region)
    {
        this.address = address;
        this.address2 = address2;
        this.city = city;
        this.region = region;
    }

    public AddressEntity() {
    }

    public int getIdAddress() {
        return idAddress;
    }

    public void setIdAddress(int idAddress) {
        this.idAddress = idAddress;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Override
    public String toString ()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Id: ").append(idAddress);
        builder.append("; Address: ").append(address);
        builder.append("; Address2: ").append(address2);
        builder.append("; City: ").append(city);
        builder.append("; Region: ").append(region);
        return builder.toString();
    }
}
