
package com.foodorder.servlet;

import com.foodorder.model.CartItem;
import com.foodorder.util.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

@WebServlet("/place-order")
public class PlaceOrderServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // Validate checkout details.
        String name = request.getParameter("fullName");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");

        if (name == null || name.isBlank()
                || phone == null || phone.isBlank()
                || address == null || address.isBlank()
                || name.length() > 100
                || phone.length() > 15
                || address.length() > 500) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Please provide valid customer details."
            );
            return;
        }

        // Require a logged-in customer.
        HttpSession session = request.getSession(false);

        if (session == null
                || session.getAttribute("userId") == null) {

            response.sendRedirect(
                    request.getContextPath() + "/login"
            );
            return;
        }

        int userId;

        try {
            Object userIdObject = session.getAttribute("userId");

            if (!(userIdObject instanceof Number)) {
                response.sendError(
                        HttpServletResponse.SC_UNAUTHORIZED,
                        "Please log in again."
                );
                return;
            }

            userId = ((Number) userIdObject).intValue();

            if (userId <= 0) {
                response.sendError(
                        HttpServletResponse.SC_UNAUTHORIZED,
                        "Invalid user session."
                );
                return;
            }

        } catch (ClassCastException e) {
            response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Invalid user session."
            );
            return;
        }

        // Validate the shopping cart.
        Object cartObject = session.getAttribute("cart");

        if (!(cartObject instanceof Map<?, ?>)) {
            response.sendRedirect(
                    request.getContextPath() + "/menu"
            );
            return;
        }

        Map<?, ?> cart = (Map<?, ?>) cartObject;

        if (cart.isEmpty()) {
            response.sendRedirect(
                    request.getContextPath() + "/menu"
            );
            return;
        }

        Connection connection = null;
        boolean committed = false;

        try {
            connection = DBConnection.getConnection();
            connection.setAutoCommit(false);

            BigDecimal total = BigDecimal.ZERO;

            // Calculate the total using current database prices.
            String priceSql =
                    "SELECT price FROM food_items "
                            + "WHERE id = ? AND available = TRUE";

            try (PreparedStatement priceStatement =
                         connection.prepareStatement(priceSql)) {

                for (Map.Entry<?, ?> entry : cart.entrySet()) {

                    if (!(entry.getKey() instanceof Integer)
                            || !(entry.getValue() instanceof CartItem)) {
                        throw new SQLException(
                                "Invalid cart contents."
                        );
                    }

                    int foodId = (Integer) entry.getKey();
                    CartItem item = (CartItem) entry.getValue();
                    int quantity = item.getQuantity();

                    if (quantity < 1 || quantity > 99) {
                        throw new SQLException(
                                "Invalid item quantity."
                        );
                    }

                    priceStatement.setInt(1, foodId);

                    try (ResultSet rs =
                                 priceStatement.executeQuery()) {

                        if (!rs.next()) {
                            throw new SQLException(
                                    "A food item is unavailable: "
                                            + foodId
                            );
                        }

                        BigDecimal price =
                                rs.getBigDecimal("price");

                        total = total.add(
                                price.multiply(
                                        BigDecimal.valueOf(quantity)
                                )
                        );
                    }
                }
            }

            // Create the order linked to the logged-in customer.
            String orderSql =
                    "INSERT INTO orders "
                            + "(user_id, total_amount, status, "
                            + "customer_name, phone, delivery_address) "
                            + "VALUES (?, ?, 'PENDING', ?, ?, ?)";

            long orderId;

            try (PreparedStatement orderStatement =
                         connection.prepareStatement(
                                 orderSql,
                                 Statement.RETURN_GENERATED_KEYS
                         )) {

                orderStatement.setInt(1, userId);
                orderStatement.setBigDecimal(2, total);
                orderStatement.setString(3, name.trim());
                orderStatement.setString(4, phone.trim());
                orderStatement.setString(5, address.trim());

                orderStatement.executeUpdate();

                try (ResultSet keys =
                             orderStatement.getGeneratedKeys()) {

                    if (!keys.next()) {
                        throw new SQLException(
                                "Could not generate an order ID."
                        );
                    }

                    orderId = keys.getLong(1);
                }
            }

            // Insert order items in the same transaction.
            String itemSql =
                    "INSERT INTO order_items "
                            + "(order_id, food_item_id, quantity, unit_price) "
                            + "VALUES (?, ?, ?, ?)";

            try (PreparedStatement itemStatement =
                         connection.prepareStatement(itemSql)) {

                for (Map.Entry<?, ?> entry : cart.entrySet()) {

                    int foodId = (Integer) entry.getKey();
                    CartItem item = (CartItem) entry.getValue();

                    BigDecimal unitPrice;

                    String currentPriceSql =
                            "SELECT price FROM food_items "
                                    + "WHERE id = ? AND available = TRUE";

                    try (PreparedStatement currentPriceStatement =
                                 connection.prepareStatement(
                                         currentPriceSql
                                 )) {

                        currentPriceStatement.setInt(1, foodId);

                        try (ResultSet rs =
                                     currentPriceStatement.executeQuery()) {

                            if (!rs.next()) {
                                throw new SQLException(
                                        "A food item is no longer available."
                                );
                            }

                            unitPrice = rs.getBigDecimal("price");
                        }
                    }

                    itemStatement.setLong(1, orderId);
                    itemStatement.setInt(2, foodId);
                    itemStatement.setInt(3, item.getQuantity());
                    itemStatement.setBigDecimal(4, unitPrice);

                    itemStatement.addBatch();
                }

                itemStatement.executeBatch();
            }

            // Commit the order before clearing the cart.
            connection.commit();
            committed = true;

            session.removeAttribute("cart");
            session.setAttribute("lastOrderId", orderId);
            session.setAttribute("lastOrderTotal", total);

            response.sendRedirect(
                    request.getContextPath() + "/order-success"
            );

        } catch (SQLException e) {

            if (connection != null && !committed) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackError) {
                    e.addSuppressed(rollbackError);
                }
            }

            getServletContext().log(
                    "Order placement failed.",
                    e
            );

            response.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Unable to place the order. Please try again."
            );

        } finally {

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    getServletContext().log(
                            "Failed to close database connection.",
                            e
                    );
                }
            }
        }
    }
}
