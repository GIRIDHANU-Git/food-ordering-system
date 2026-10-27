package com.foodorder.servlet;

import com.foodorder.dao.OrderDAO;
import com.foodorder.dao.OrderItemDAO;
import com.foodorder.model.Order;
import com.foodorder.model.OrderItem;
import com.foodorder.util.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/order-details")
public class OrderDetailsServlet extends HttpServlet {

    private final OrderDAO orderDAO = new OrderDAO();
    private final OrderItemDAO orderItemDAO = new OrderItemDAO();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null
                || !(session.getAttribute("userId") instanceof Number)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        int userId = ((Number) session.getAttribute("userId")).intValue();

        if (userId <= 0) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        boolean isAdmin =
                "ADMIN".equals(session.getAttribute("userRole"));

        String orderIdParam = request.getParameter("id");

        if (orderIdParam == null || orderIdParam.isBlank()) {
            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Order ID is required."
            );
            return;
        }

        long orderId;

        try {
            orderId = Long.parseLong(orderIdParam);

            if (orderId <= 0) {
                response.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "Invalid order ID."
                );
                return;
            }
        } catch (NumberFormatException e) {
            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Invalid order ID."
            );
            return;
        }

        try {
            // Verify that the order exists and check ownership.
            String sql = "SELECT user_id FROM orders WHERE id = ?";

            try (Connection connection = DBConnection.getConnection();
                 PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setLong(1, orderId);

                try (ResultSet rs = statement.executeQuery()) {
                    if (!rs.next()) {
                        response.sendError(
                                HttpServletResponse.SC_NOT_FOUND,
                                "Order not found."
                        );
                        return;
                    }

                    Object orderUserId = rs.getObject("user_id");

                    if (!isAdmin
                            && (orderUserId == null
                            || ((Number) orderUserId).intValue() != userId)) {
                        response.sendError(
                                HttpServletResponse.SC_FORBIDDEN,
                                "You are not allowed to view this order."
                        );
                        return;
                    }
                }
            }

            // Retrieve the order summary and its items.
            Order order = orderDAO.getOrderById(orderId);

            if (order == null) {
                response.sendError(
                        HttpServletResponse.SC_NOT_FOUND,
                        "Order not found."
                );
                return;
            }

            List<OrderItem> items =
                    orderItemDAO.getItemsByOrderId(orderId);

            // These names match the redesigned order-details.jsp.
            request.setAttribute("order", order);
            request.setAttribute("items", items);

            request.getRequestDispatcher("/order-details.jsp")
                    .forward(request, response);

        } catch (SQLException e) {
            throw new ServletException(
                    "Unable to retrieve order details.", e
            );
        }
    }
}
