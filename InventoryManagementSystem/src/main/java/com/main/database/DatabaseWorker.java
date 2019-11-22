package com.main.database;

import java.sql.*;

public class DatabaseWorker {
    private final String URL = "jdbc:mysql://localhost:3306/inventory_management_system";
    private final String USERNAME = "root";
    private final String PASSWORD = "root";

    private Connection connection;

    public DatabaseWorker () {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
