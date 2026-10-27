
package com.foodorder.servlet;

import com.foodorder.dao.UserDAO;
import com.foodorder.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/register.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (name == null || email == null || password == null
                || name.isBlank() || email.isBlank()
                || password.isBlank()) {

            request.setAttribute("error", "All fields are required.");
            request.getRequestDispatcher("/register.jsp")
                    .forward(request, response);
            return;
        }

        name = name.trim();
        email = email.trim().toLowerCase();

        if (name.length() > 100 || email.length() > 150
                || password.length() < 8) {

            request.setAttribute(
                    "error",
                    "Name or email is too long, or password has fewer than 8 characters."
            );

            request.getRequestDispatcher("/register.jsp")
                    .forward(request, response);
            return;
        }

        try {
            if (userDAO.findByEmail(email) != null) {
                request.setAttribute(
                        "error", "This email is already registered."
                );

                request.getRequestDispatcher("/register.jsp")
                        .forward(request, response);
                return;
            }

            User user = new User(name, email, password);

            if (userDAO.registerUser(user)) {
                response.sendRedirect(
                        request.getContextPath() + "/login?registered=true"
                );
            } else {
                request.setAttribute(
                        "error", "Registration failed. Please try again."
                );

                request.getRequestDispatcher("/register.jsp")
                        .forward(request, response);
            }

        } catch (SQLException e) {
            getServletContext().log("Registration failed", e);

            request.setAttribute(
                    "error", "Unable to register right now. Please try again."
            );

            request.getRequestDispatcher("/register.jsp")
                    .forward(request, response);
        }
    }
}
