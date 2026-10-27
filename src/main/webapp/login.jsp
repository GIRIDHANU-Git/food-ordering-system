<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Customer Login | foodie.</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">
</head>
<body class="auth-page">

<header class="site-header">
    <div class="nav-container">
        <a href="${pageContext.request.contextPath}/login"
           class="logo">foodie<span>.</span></a>
        <a href="${pageContext.request.contextPath}/register"
           class="nav-link">Create account</a>
    </div>
</header>

<main class="auth-container">
    <section class="auth-card">
        <div class="auth-brand">foodie.</div>

        <h1>Welcome back</h1>
        <p class="auth-subtitle">
            Log in to continue your food journey.
        </p>

        <% if ("true".equals(request.getParameter("registered"))) { %>
            <div class="auth-message auth-success">
                Registration successful. Please log in.
            </div>
        <% } %>

        <% String error = (String) request.getAttribute("error");
           if (error != null) { %>
            <div class="auth-message auth-error">
                <%= error %>
            </div>
        <% } %>

        <form action="${pageContext.request.contextPath}/login"
              method="post" class="auth-form">

            <div class="form-group">
                <label for="email">Email address</label>
                <input type="email" id="email" name="email"
                       placeholder="you@example.com"
                       maxlength="150" autocomplete="email" required>
            </div>

            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password"
                       placeholder="Enter your password"
                       autocomplete="current-password" required>
            </div>

            <button type="submit" class="auth-submit">
                Log in
            </button>
        </form>

        <p class="auth-footer">
            Don't have an account?
            <a href="${pageContext.request.contextPath}/register">
                Sign up
            </a>
        </p>
    </section>
</main>

</body>
</html>