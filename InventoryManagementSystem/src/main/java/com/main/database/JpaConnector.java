package com.main.database;

public class JpaConnector {
    private static JpaAddressDao address;
    private static JpaArrivalDao arrival;
    private static JpaCategoryDao category;
    private static JpaConsumptionDao consumption;
    private static JpaCustomerDao customer;
    private static JpaFactDao fact;
    private static JpaOrderDao order;
    private static JpaProductDao product;
    private static JpaSaleDao sale;

    static {
        initialize();
    }

    public static void initialize() {
        try {
            address = new JpaAddressDao();
            arrival = new JpaArrivalDao();
            category = new JpaCategoryDao();
            consumption = new JpaConsumptionDao();
            customer = new JpaCustomerDao();
            fact = new JpaFactDao();
            order = new JpaOrderDao();
            product = new JpaProductDao();
            sale = new JpaSaleDao();
        } catch (NullPointerException ex) {
            System.err.print("\nError::Connector::initialize::Some JPA class(es) is null pointer.");
            throw ex;
        }
    }


    public static JpaAddressDao getAddress() {
        return address;
    }

    public static JpaArrivalDao getArrival() {
        return arrival;
    }

    public static JpaCategoryDao getCategory() {
        return category;
    }

    public static JpaConsumptionDao getConsumption() {
        return consumption;
    }

    public static JpaCustomerDao getCustomer() {
        return customer;
    }

    public static JpaFactDao getFact() {
        return fact;
    }

    public static JpaOrderDao getOrder() {
        return order;
    }

    public static JpaProductDao getProduct() {
        return product;
    }

    public static JpaSaleDao getSale() {
        return sale;
    }
}
