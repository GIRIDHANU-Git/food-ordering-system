
package com.foodorder.dao;

import com.foodorder.model.Order;
import com.foodorder.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class OrderDAO {

    private static final Set<String> ALLOWED_STATUSES = Set.of(
            "PENDING",
            "PREPARING",
            "DELIVERED",
            "CANCELLED"
    );

    // Retrieve all orders for the admin.
    public List<Order> getAllOrders() throws SQLException {

        List<Order> orders = new ArrayList<>();

        String sql = """
                SELECT id, total_amount, status, created_at,
                       customer_name, phone, delivery_address
                FROM orders
                ORDER BY created_at DESC
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Order order = new Order(
                        resultSet.getLong("id"),
                        resultSet.getBigDecimal("total_amount"),
                        resultSet.getString("status"),
                        resultSet.getTimestamp("created_at"),
                        resultSet.getString("customer_name"),
                        resultSet.getString("phone"),
                        resultSet.getString("delivery_address")
                );

                orders.add(order);
            }
        }

        return orders;
    }

    // Retrieve only orders belonging to a specific customer.
    public List<Order> getOrdersByUserId(int userId)
            throws SQLException {

        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }

        List<Order> orders = new ArrayList<>();

        String sql = """
                SELECT id, total_amount, status, created_at,
                       customer_name, phone, delivery_address
                FROM orders
                WHERE user_id = ?
                ORDER BY created_at DESC
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {

                    Order order = new Order(
                            resultSet.getLong("id"),
                            resultSet.getBigDecimal("total_amount"),
                            resultSet.getString("status"),
                            resultSet.getTimestamp("created_at"),
                            resultSet.getString("customer_name"),
                            resultSet.getString("phone"),
                            resultSet.getString("delivery_address")
                    );

                    orders.add(order);
                }
            }
        }

        return orders;
    }

    // Update order status for the admin.
    public boolean updateOrderStatus(
            long orderId,
            String status
    ) throws SQLException {

        if (orderId <= 0) {
            throw new IllegalArgumentException(
                    "Invalid order ID"
            );
        }

        if (status == null || !ALLOWED_STATUSES.contains(status)) {
            throw new IllegalArgumentException(
                    "Invalid order status"
            );
        }

        String sql = """
                UPDATE orders
                SET status = ?
                WHERE id = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, status);
            statement.setLong(2, orderId);

            return statement.executeUpdate() > 0;
        }
    }
    public Order getOrderById(long orderId) throws SQLException {

        if (orderId <= 0) {
            throw new IllegalArgumentException("Invalid order ID");
        }

        String sql = """
            SELECT id, total_amount, status, created_at,
                   customer_name, phone, delivery_address
            FROM orders
            WHERE id = ?
            """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setLong(1, orderId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return new Order(
                            resultSet.getLong("id"),
                            resultSet.getBigDecimal("total_amount"),
                            resultSet.getString("status"),
                            resultSet.getTimestamp("created_at"),
                            resultSet.getString("customer_name"),
                            resultSet.getString("phone"),
                            resultSet.getString("delivery_address")
                    );
                }
            }
        }

        return null;
    }
}
