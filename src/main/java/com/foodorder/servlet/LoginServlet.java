
package com.foodorder.servlet;

import com.foodorder.dao.UserDAO;
import com.foodorder.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/login.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (email == null || password == null
                || email.isBlank() || password.isEmpty()) {

            request.setAttribute(
                    "error", "Email and password are required."
            );

            request.getRequestDispatcher("/login.jsp")
                    .forward(request, response);
            return;
        }

        email = email.trim().toLowerCase();

        try {
            User user = userDAO.findByEmail(email);

            if (user == null
                    || !userDAO.authenticate(email, password)) {

                request.setAttribute(
                        "error", "Invalid email or password."
                );

                request.getRequestDispatcher("/login.jsp")
                        .forward(request, response);
                return;
            }

            // Prevent session fixation after successful authentication.
            HttpSession oldSession = request.getSession(false);

            if (oldSession != null) {
                oldSession.invalidate();
            }

            HttpSession session = request.getSession(true);
            session.setAttribute("userId", user.getId());
            session.setAttribute("userName", user.getName());
            session.setAttribute("userRole", user.getRole());
            session.setMaxInactiveInterval(30 * 60);

            if ("ADMIN".equals(user.getRole())) {
                response.sendRedirect(
                        request.getContextPath() + "/admin/orders"
                );
            } else {
                response.sendRedirect(
                        request.getContextPath() + "/menu"
                );
            }

        } catch (SQLException e) {
            getServletContext().log("Login failed", e);

            request.setAttribute(
                    "error", "Unable to log in right now. Please try again."
            );

            request.getRequestDispatcher("/login.jsp")
                    .forward(request, response);
        }
    }
}
