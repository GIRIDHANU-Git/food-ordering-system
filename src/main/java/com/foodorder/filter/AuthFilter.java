        package com.foodorder.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(urlPatterns = {
        "/admin/*",
        "/my-orders",
        "/order-details",
        "/checkout",
        "/place-order"
})
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest =
                (HttpServletRequest) request;

        HttpServletResponse httpResponse =
                (HttpServletResponse) response;

        HttpSession session = httpRequest.getSession(false);

        if (session == null
                || !(session.getAttribute("userId") instanceof Number)) {

            httpResponse.sendRedirect(
                    httpRequest.getContextPath() + "/login"
            );
            return;
        }

        Number userIdValue = (Number) session.getAttribute("userId");

        if (userIdValue.longValue() <= 0) {
            httpResponse.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Invalid user session. Please log in again."
            );
            return;
        }

        String path = httpRequest.getServletPath();

        if (path.startsWith("/admin/")
                && !"ADMIN".equals(session.getAttribute("userRole"))) {

            httpResponse.sendError(
                    HttpServletResponse.SC_FORBIDDEN,
                    "You are not authorized to access this page."
            );
            return;
        }

        chain.doFilter(request, response);
    }
}