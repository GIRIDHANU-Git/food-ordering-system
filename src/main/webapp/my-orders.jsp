
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Orders | Foodie</title>
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
            <a href="${pageContext.request.contextPath}/my-orders"
               class="active">My Orders</a>
            <a href="${pageContext.request.contextPath}/cart">My Cart</a>
        </nav>
    </div>
</header>

<main class="orders-page">

    <section class="orders-intro">
        <p class="orders-eyebrow">YOUR FOOD JOURNEY</p>
        <h1>My <span>Orders</span></h1>
        <p>Every order has a story. Find yours here.</p>
    </section>

    <c:choose>
        <c:when test="${empty orders}">
            <section class="orders-empty">
                <div class="orders-empty-icon">&#127829;</div>
                <h2>No orders yet</h2>
                <p>Your next favourite meal is just a few clicks away.</p>
                <a href="${pageContext.request.contextPath}/menu"
                   class="orders-primary-button">
                    Explore the Menu
                </a>
            </section>
        </c:when>

        <c:otherwise>
            <section class="orders-list">
                <div class="orders-list-heading">
                    <h2>Your order history</h2>
                    <span>
                        <c:out value="${orders.size()}"/> order(s)
                    </span>
                </div>

                <c:forEach var="order" items="${orders}">
                    <article class="order-card">

                        <div class="order-card-top">
                            <div class="order-restaurant">
                                <div class="order-food-icon">&#127828;</div>
                                <div>
                                    <h3>Foodie Order</h3>
                                    <p>
                                        Order #<c:out value="${order.id}"/>
                                    </p>
                                </div>
                            </div>

                            <span class="order-status
                                <c:choose>
                                    <c:when test="${order.status eq 'DELIVERED'}">status-delivered</c:when>
                                    <c:when test="${order.status eq 'CANCELLED'}">status-cancelled</c:when>
                                    <c:when test="${order.status eq 'PREPARING'}">status-preparing</c:when>
                                    <c:otherwise>status-pending</c:otherwise>
                                </c:choose>">
                                <c:out value="${order.status}"/>
                            </span>
                        </div>

                        <div class="order-card-divider"></div>

                        <div class="order-card-details">
                            <div class="order-detail">
                                <span>ORDER DATE</span>
                                <strong>
                                    <fmt:formatDate value="${order.createdAt}"
                                                    pattern="dd MMM yyyy, hh:mm a"/>
                                </strong>
                            </div>

                            <div class="order-detail">
                                <span>ORDER TOTAL</span>
                                <strong class="order-price">
                                    ₹<fmt:formatNumber value="${order.totalAmount}"
                                                       minFractionDigits="2"
                                                       maxFractionDigits="2"/>
                                </strong>
                            </div>
                        </div>

                        <div class="order-card-bottom">
                            <span class="order-tracking-note">
                                <c:choose>
                                    <c:when test="${order.status eq 'DELIVERED'}">
                                        Your order has been delivered.
                                    </c:when>
                                    <c:when test="${order.status eq 'CANCELLED'}">
                                        This order was cancelled.
                                    </c:when>
                                    <c:when test="${order.status eq 'PREPARING'}">
                                        Your food is being prepared.
                                    </c:when>
                                    <c:otherwise>
                                        Your order is awaiting confirmation.
                                    </c:otherwise>
                                </c:choose>
                            </span>

                            <a class="order-details-button"
                               href="${pageContext.request.contextPath}/order-details?id=${order.id}">
                                View Details
                                <span aria-hidden="true">&rarr;</span>
                            </a>
                        </div>

                    </article>
                </c:forEach>
            </section>
        </c:otherwise>
    </c:choose>

</main>

</body>
</html>
