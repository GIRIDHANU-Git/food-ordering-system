
package com.foodorder.dao;

import com.foodorder.model.User;
import com.foodorder.util.DBConnection;
import com.foodorder.util.PasswordUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public boolean registerUser(User user) throws SQLException {

        String sql = """
            INSERT INTO users (name, email, password_hash, role)
            VALUES (?, ?, ?, 'CUSTOMER')
            """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(
                    3, PasswordUtil.hashPassword(user.getPasswordHash()));

            return statement.executeUpdate() == 1;
        }
    }

    public User findByEmail(String email) throws SQLException {

        String sql = """
            SELECT id, name, email, password_hash, role
            FROM users
            WHERE email = ?
            """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, email);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    User user = new User();

                    user.setId(resultSet.getLong("id"));
                    user.setName(resultSet.getString("name"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPasswordHash(
                            resultSet.getString("password_hash"));
                    user.setRole(resultSet.getString("role"));

                    return user;
                }
            }
        }

        return null;
    }

    public boolean authenticate(String email, String password)
            throws SQLException {

        User user = findByEmail(email);

        return user != null
                && PasswordUtil.verifyPassword(
                password, user.getPasswordHash());
    }
}
