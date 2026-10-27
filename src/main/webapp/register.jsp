<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create Account | Foodie</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">
</head>
<body class="auth-page">

<header class="site-header">
    <div class="nav-container">
        <a href="${pageContext.request.contextPath}/login"
           class="logo">foodie<span>.</span></a>

        <a href="${pageContext.request.contextPath}/login"
           class="nav-link">Log in</a>
    </div>
</header>

<main class="auth-container">
    <section class="auth-card">

        <div class="auth-brand">foodie.</div>

        <h1>Create account</h1>
        <p class="auth-subtitle">
            Sign up to discover your next favourite meal.
        </p>

        <%
            String error = (String) request.getAttribute("error");
            if (error != null) {
        %>
            <div class="auth-message auth-error" role="alert">
                <%= error %>
            </div>
        <%
            }
        %>

        <form action="${pageContext.request.contextPath}/register"
              method="post"
              class="auth-form">

            <div class="form-group">
                <label for="name">Full name</label>
                <input type="text"
                       id="name"
                       name="name"
                       placeholder="Enter your full name"
                       maxlength="100"
                       autocomplete="name"
                       required>
            </div>

            <div class="form-group">
                <label for="email">Email address</label>
                <input type="email"
                       id="email"
                       name="email"
                       placeholder="you@example.com"
                       maxlength="150"
                       autocomplete="email"
                       required>
            </div>

            <div class="form-group">
                <label for="password">Password</label>
                <input type="password"
                       id="password"
                       name="password"
                       placeholder="Create a password"
                       minlength="8"
                       autocomplete="new-password"
                       required>
            </div>

            <button type="submit" class="auth-submit">
                Create account
            </button>
        </form>

        <p class="auth-footer">
            Already have an account?
            <a href="${pageContext.request.contextPath}/login">
                Log in
            </a>
        </p>

    </section>
</main>

</body>
</html>
