
package com.foodorder.servlet;

import com.foodorder.dao.FoodDAO;
import com.foodorder.model.CartItem;
import com.foodorder.model.Food;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    private final FoodDAO foodDAO = new FoodDAO();

    @SuppressWarnings("unchecked")
    private Map<Integer, CartItem> getCart(HttpSession session) {
        Map<Integer, CartItem> cart =
                (Map<Integer, CartItem>) session.getAttribute("cart");

        if (cart == null) {
            cart = new LinkedHashMap<>();
            session.setAttribute("cart", cart);
        }

        return cart;
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        Map<Integer, CartItem> cart = getCart(session);

        try {
            if ("add".equals(action)) {

                int foodId = Integer.parseInt(
                        request.getParameter("foodId"));

                Food food = foodDAO.getFoodById(foodId);

                if (food == null) {
                    response.sendError(
                            HttpServletResponse.SC_NOT_FOUND,
                            "Food item not found");
                    return;
                }

                CartItem item = cart.get(foodId);

                if (item == null) {
                    cart.put(foodId, new CartItem(food, 1));
                } else {
                    if (item.getQuantity() >= 99) {
                        response.sendError(
                                HttpServletResponse.SC_BAD_REQUEST,
                                "Maximum quantity is 99");
                        return;
                    }

                    item.setQuantity(item.getQuantity() + 1);
                }

            } else if ("update".equals(action)) {

                int foodId = Integer.parseInt(
                        request.getParameter("foodId"));

                int quantity = Integer.parseInt(
                        request.getParameter("quantity"));

                if (quantity < 1 || quantity > 99) {
                    response.sendError(
                            HttpServletResponse.SC_BAD_REQUEST,
                            "Quantity must be between 1 and 99");
                    return;
                }

                CartItem item = cart.get(foodId);

                if (item != null) {
                    item.setQuantity(quantity);
                }

            } else if ("remove".equals(action)) {

                int foodId = Integer.parseInt(
                        request.getParameter("foodId"));

                cart.remove(foodId);

            } else {
                response.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "Invalid cart action");
                return;
            }

            response.sendRedirect(
                    request.getContextPath() + "/cart");

        } catch (NumberFormatException e) {
            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Invalid food ID or quantity");

        } catch (SQLException e) {
            throw new ServletException(
                    "Unable to access food data", e);
        }
    }

    @Override

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Map<Integer, CartItem> cart = getCart(session);

        // Calculate the grand total using BigDecimal.
        java.math.BigDecimal grandTotal = java.math.BigDecimal.ZERO;

        for (CartItem item : cart.values()) {
            grandTotal = grandTotal.add(item.getSubtotal());
        }

        request.setAttribute("cartItems", cart.values());
        request.setAttribute("grandTotal", grandTotal);

        request.getRequestDispatcher("/cart.jsp")
                .forward(request, response);
    }

}
