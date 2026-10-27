
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Order Details | Foodie</title>
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
            <a href="${pageContext.request.contextPath}/cart">My Cart</a>
        </nav>
    </div>
</header>

<main class="order-details-page">

    <a class="back-to-orders"
       href="${pageContext.request.contextPath}/my-orders">
        &larr; Back to My Orders
    </a>

    <section class="order-details-heading">
        <p class="details-eyebrow">YOUR ORDER</p>
        <h1>Order <span>#<c:out value="${order.id}"/></span></h1>
        <p>Here's a breakdown of your order.</p>
    </section>

    <section class="details-summary-card">
        <div class="details-summary-top">
            <div>
                <span class="details-label">ORDER STATUS</span>
                <p class="details-status">
                    <c:out value="${order.status}"/>
                </p>
            </div>
            <div class="details-summary-total">
                <span class="details-label">ORDER TOTAL</span>
                <strong>
                    ₹<fmt:formatNumber value="${order.totalAmount}"
                                       minFractionDigits="2"
                                       maxFractionDigits="2"/>
                </strong>
            </div>
        </div>

        <div class="details-summary-divider"></div>

        <div class="details-meta">
            <div>
                <span class="details-label">ORDER DATE</span>
                <strong>
                    <fmt:formatDate value="${order.createdAt}"
                                    pattern="dd MMM yyyy, hh:mm a"/>
                </strong>
            </div>
            <div>
                <span class="details-label">CUSTOMER</span>
                <strong><c:out value="${order.customerName}"/></strong>
            </div>
        </div>
    </section>

    <section class="ordered-items-section">
        <div class="ordered-items-heading">
            <h2>Items in your order</h2>
            <span><c:out value="${items.size()}"/> item(s)</span>
        </div>

        <c:choose>
            <c:when test="${empty items}">
                <div class="details-empty-items">
                    No food items were found for this order.
                </div>
            </c:when>

            <c:otherwise>
                <div class="ordered-items-list">
                    <c:forEach var="item" items="${items}">
                        <article class="ordered-item-card">
                            <div class="ordered-item-icon">&#127828;</div>

                            <div class="ordered-item-info">
                                <h3><c:out value="${item.foodName}"/></h3>
                                <p>
                                    ₹<fmt:formatNumber value="${item.unitPrice}"
                                                       minFractionDigits="2"
                                                       maxFractionDigits="2"/>
                                    &times;
                                    <c:out value="${item.quantity}"/>
                                </p>
                            </div>

                            <strong class="ordered-item-subtotal">
                                ₹<fmt:formatNumber
                                    value="${item.subtotal}"
                                    minFractionDigits="2"
                                    maxFractionDigits="2"/>
                            </strong>
                        </article>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>
    </section>

    <div class="details-bottom-actions">
        <a href="${pageContext.request.contextPath}/menu"
           class="details-primary-button">
            Order Again
        </a>
        <a href="${pageContext.request.contextPath}/my-orders"
           class="details-secondary-button">
            Back to Order History
        </a>
    </div>

</main>

</body>
</html>
