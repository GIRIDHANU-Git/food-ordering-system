
package com.foodorder.dao;

import com.foodorder.model.Food;
import com.foodorder.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FoodDAO {

    public List<Food> getAllAvailableFoods() throws SQLException {
        List<Food> foods = new ArrayList<>();

        String sql = """
                SELECT f.id, f.name, f.description,
                       c.name AS category, f.price, f.available
                FROM food_items f
                JOIN categories c ON f.category_id = c.id
                WHERE f.available = TRUE
                ORDER BY c.name, f.name
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Food food = new Food(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getString("category"),
                        resultSet.getBigDecimal("price"),
                        resultSet.getBoolean("available")
                );

                foods.add(food);
            }
        }

        return foods;
    }

    public Food getFoodById(int id) throws SQLException {
        String sql = """
                SELECT f.id, f.name, f.description,
                       c.name AS category, f.price, f.available
                FROM food_items f
                JOIN categories c ON f.category_id = c.id
                WHERE f.id = ? AND f.available = TRUE
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Food(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getString("description"),
                            resultSet.getString("category"),
                            resultSet.getBigDecimal("price"),
                            resultSet.getBoolean("available")
                    );
                }
            }
        }

        return null;
    }
}
