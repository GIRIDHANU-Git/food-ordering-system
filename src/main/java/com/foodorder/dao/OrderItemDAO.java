
package com.foodorder.dao;

import com.foodorder.model.OrderItem;
import com.foodorder.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderItemDAO {

    public List<OrderItem> getItemsByOrderId(long orderId)
            throws SQLException {

        List<OrderItem> items = new ArrayList<>();

        String sql = """
            SELECT
                oi.food_item_id,
                f.name AS food_name,
                oi.quantity,
                oi.unit_price,
                (oi.quantity * oi.unit_price) AS subtotal
            FROM order_items oi
            JOIN food_items f ON oi.food_item_id = f.id
            WHERE oi.order_id = ?
            ORDER BY oi.id
            """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setLong(1, orderId);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {

                    OrderItem item = new OrderItem(
                            resultSet.getLong("food_item_id"),
                            resultSet.getString("food_name"),
                            resultSet.getInt("quantity"),
                            resultSet.getBigDecimal("unit_price"),
                            resultSet.getBigDecimal("subtotal")
                    );

                    items.add(item);
                }
            }
        }

        return items;
    }
}
