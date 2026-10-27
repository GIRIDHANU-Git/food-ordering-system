
package com.foodorder.servlet;

import com.foodorder.model.CartItem;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {

    @SuppressWarnings("unchecked")
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null) {
            response.sendRedirect(
                    request.getContextPath() + "/menu");
            return;
        }

        Map<Integer, CartItem> cart =
                (Map<Integer, CartItem>) session.getAttribute("cart");

        if (cart == null || cart.isEmpty()) {
            response.sendRedirect(
                    request.getContextPath() + "/cart");
            return;
        }

        BigDecimal total = BigDecimal.ZERO;

        for (CartItem item : cart.values()) {
            total = total.add(item.getSubtotal());
        }

        request.setAttribute("grandTotal", total);

        request.getRequestDispatcher("/checkout.jsp")
                .forward(request, response);
    }
}
