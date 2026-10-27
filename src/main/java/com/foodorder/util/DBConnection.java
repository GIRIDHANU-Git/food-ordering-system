
package com.foodorder.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/food_ordering_db";
    private static final String USER = "root";

    // Replace this with your actual MySQL root password.
    private static final String PASSWORD = "Q2h8dge4km@123";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC driver not found", e);
        }

        return DriverManager.getConnection("jdbc:mysql://localhost:3306/food_ordering_db", "root","Q2h8dge4km@123");
    }
}