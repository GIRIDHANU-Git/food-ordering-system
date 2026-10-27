
package com.foodorder.servlet;

import com.foodorder.dao.OrderDAO;
import com.foodorder.model.Order;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/my-orders")
public class MyOrdersServlet extends HttpServlet {

    private final OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // Only logged-in users can access order history.
        if (session == null
                || !(session.getAttribute("userId") instanceof Number)) {

            response.sendRedirect(
                    request.getContextPath() + "/login"
            );
            return;
        }

        int userId = ((Number) session.getAttribute("userId"))
                .intValue();

        if (userId <= 0) {
            response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED
            );
            return;
        }

        try {
            List<Order> orders;

            String userRole = (String) session.getAttribute("userRole");

            if ("ADMIN".equals(userRole)) {
                // Admin can see all orders.
                orders = orderDAO.getAllOrders();
            } else {
                // Customers can see only their own orders.
                orders = orderDAO.getOrdersByUserId(userId);
            }

            request.setAttribute("orders", orders);

            request.getRequestDispatcher("/my-orders.jsp")
                    .forward(request, response);

        } catch (SQLException e) {
            throw new ServletException(
                    "Unable to retrieve order history", e
            );
        }
    }
}
