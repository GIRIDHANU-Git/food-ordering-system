
package com.foodorder.servlet;

import com.foodorder.dao.OrderDAO;
import com.foodorder.model.Order;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/admin/orders")
public class AdminOrdersServlet extends HttpServlet {

    private final OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        try {
            List<Order> orders = orderDAO.getAllOrders();

            request.setAttribute("orders", orders);

            request.getRequestDispatcher("/admin-orders.jsp")
                    .forward(request, response);

        } catch (SQLException e) {
            throw new ServletException(
                    "Unable to retrieve orders", e
            );
        }
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try {
            long orderId = Long.parseLong(
                    request.getParameter("orderId")
            );

            String status = request.getParameter("status");

            boolean updated = orderDAO.updateOrderStatus(
                    orderId, status
            );

            String result = updated ? "updated" : "notFound";

            response.sendRedirect(
                    request.getContextPath()
                            + "/admin/orders?result=" + result
            );

        } catch (NumberFormatException e) {
            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Invalid order ID"
            );

        } catch (IllegalArgumentException e) {
            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Invalid order status"
            );

        } catch (SQLException e) {
            throw new ServletException(
                    "Unable to update order status", e
            );
        }
    }
}
