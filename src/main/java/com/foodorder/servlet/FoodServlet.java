
package com.foodorder.servlet;

import com.foodorder.dao.FoodDAO;
import com.foodorder.model.Food;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/menu")
public class FoodServlet extends HttpServlet {

    private final FoodDAO foodDAO = new FoodDAO();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        try {
            List<Food> foods = foodDAO.getAllAvailableFoods();

            request.setAttribute("foods", foods);

            request.getRequestDispatcher("/WEB-INF/menu.jsp")
                    .forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Unable to load the food menu", e);
        }
    }
}
