package com.main.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnector {
    private DatabaseWorker databaseWorker;

    public DatabaseConnector() {
        databaseWorker = new DatabaseWorker();
    }

    /**
     * Select all information from database with table name and limit of rows.
     *
     * @param tableName name of a table from the database
     * @return return rows from databse or null
     */
    public ResultSet selectFrom(String tableName) throws SQLException {
        Statement statement = databaseWorker.getConnection().createStatement();
        return statement.executeQuery("select * from " + tableName);
    }

    /**
     * Select information from database with table name and limit of rows.
     *
     * @param tableName name of a table from the database
     * @param rowsLimit rows amount that will be selected from the database
     * @return return rows from databse or null
     */
    public ResultSet selectFrom(String tableName, int rowsLimit) throws SQLException {
        Statement statement = databaseWorker.getConnection().createStatement();
        return statement.executeQuery("select * from " + tableName + " limit " + rowsLimit);
    }
}
