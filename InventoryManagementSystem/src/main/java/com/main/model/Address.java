package com.main.model;

public class Address {
    private int idAddress;
    private String address;
    private String address2;
    private String city;
    private String region;

    public Address (int idAddress, String address, String address2, String city, String region)
    {
        this.idAddress = idAddress;
        this.address = address;
        this.address2 = address2;
        this.city = city;
        this.region = region;
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
}
