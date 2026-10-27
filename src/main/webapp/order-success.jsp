
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Order Confirmed | Foodie</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<header class="site-header">
    <div class="nav-container">
        <a href="${pageContext.request.contextPath}/menu" class="logo">
            foodie<span>.</span>
        </a>

        <nav class="nav-links">
            <a href="${pageContext.request.contextPath}/menu">Menu</a>
            <a href="${pageContext.request.contextPath}/my-orders">My Orders</a>
        </nav>
    </div>
</header>

<main class="success-page">
    <section class="success-card">

        <div class="success-icon">
            &#10003;
        </div>

        <p class="success-eyebrow">ORDER PLACED SUCCESSFULLY</p>

        <h1>Good food is on its way!</h1>

        <p class="success-description">
            Thank you for ordering with Foodie. Your order has been
            received and is being processed.
        </p>

        <c:if test="${not empty sessionScope.lastOrderId}">
            <div class="success-order-number">
                <span>ORDER NUMBER</span>
                <strong>#${sessionScope.lastOrderId}</strong>
            </div>
        </c:if>

        <c:if test="${not empty sessionScope.lastOrderTotal}">
            <div class="success-total">
                <span>Total paid</span>
                <strong>₹${sessionScope.lastOrderTotal}</strong>
            </div>
        </c:if>

        <div class="success-actions">
            <a href="${pageContext.request.contextPath}/my-orders"
               class="primary-button">
                View My Orders
            </a>

            <a href="${pageContext.request.contextPath}/menu"
               class="secondary-button">
                Continue Shopping
            </a>
        </div>

        <p class="success-footer">
            Hungry again? There's always something delicious waiting.
        </p>

    </section>
</main>

</body>
</html>
