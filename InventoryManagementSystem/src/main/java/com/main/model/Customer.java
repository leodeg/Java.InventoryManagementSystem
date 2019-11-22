package com.main.model;

public class Customer {
    private int idAddress;
    private int idCustomer;
    private  String name;
    private  String phone;
    private  String phone2;
    private  String description;

    public Customer(int idAddress, int idCustomer, String name, String phone, String phone2, String description) {
        this.idAddress = idAddress;
        this.idCustomer = idCustomer;
        this.name = name;
        this.phone = phone;
        this.phone2 = phone2;
        this.description = description;
    }

    public int getIdAddress() {
        return idAddress;
    }

    public void setIdAddress(int idAddress) {
        this.idAddress = idAddress;
    }

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
