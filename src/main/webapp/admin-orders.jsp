<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Orders | Foodie</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<header class="site-header">
    <div class="nav-container">
        <a href="${pageContext.request.contextPath}/admin/orders"
           class="logo">
            foodie<span>.</span>
        </a>

        <nav class="nav-links">
            <a href="${pageContext.request.contextPath}/admin/orders"
               class="active">Orders</a>
            <a href="${pageContext.request.contextPath}/menu">View Menu</a>
            <a href="${pageContext.request.contextPath}/my-orders">My Orders</a>
        </nav>
    </div>
</header>

<main class="admin-orders-page">

    <!-- Dashboard heading -->
    <section class="admin-orders-intro">
        <div>
            <p class="admin-eyebrow">FOODIE MANAGEMENT</p>
            <h1>Order <span>Dashboard</span></h1>
            <p>Manage incoming orders and keep every meal moving.</p>
        </div>

        <div class="admin-orders-count">
            <span>TOTAL ORDERS</span>
            <strong><c:out value="${orders.size()}"/></strong>
        </div>
    </section>

    <!-- Status update feedback -->
    <c:if test="${param.result eq 'updated'}">
        <div class="admin-alert admin-alert-success" role="status">
            Order status updated successfully.
        </div>
    </c:if>

    <c:if test="${param.result eq 'notFound'}">
        <div class="admin-alert admin-alert-error" role="alert">
            The order was not found or its status was not changed.
        </div>
    </c:if>

    <!-- Orders panel -->
    <section class="admin-orders-panel">

        <div class="admin-panel-heading">
            <div>
                <h2>All orders</h2>
                <p>Click an order number to view its items and delivery details.</p>
            </div>
        </div>

        <c:choose>
            <c:when test="${empty orders}">
                <div class="admin-orders-empty">
                    <div class="admin-empty-icon" aria-hidden="true">
                        &#128230;
                    </div>
                    <h3>No orders yet</h3>
                    <p>New customer orders will appear here.</p>
                </div>
            </c:when>

            <c:otherwise>
                <div class="admin-table-wrapper">
                    <table class="admin-orders-table">

                        <thead>
                        <tr>
                            <th scope="col">Order</th>
                            <th scope="col">Customer</th>
                            <th scope="col">Order date</th>
                            <th scope="col">Total</th>
                            <th scope="col">Status</th>
                            <th scope="col">Update status</th>
                        </tr>
                        </thead>

                        <tbody>
                        <c:forEach var="order" items="${orders}">
                            <tr>
                                <!-- Clickable order ID -->
                                <td>
                                    <a class="admin-order-number admin-order-link"
                                       href="${pageContext.request.contextPath}/order-details?id=${order.id}"
                                       title="View details for order ${order.id}">
                                        #<c:out value="${order.id}"/>
                                    </a>
                                </td>

                                <!-- Customer details -->
                                <td>
                                    <div class="admin-customer-name">
                                        <c:out value="${order.customerName}"/>
                                    </div>
                                    <div class="admin-customer-phone">
                                        <c:out value="${order.phone}"/>
                                    </div>
                                </td>

                                <!-- Order date -->
                                <td class="admin-order-date">
                                    <fmt:formatDate
                                            value="${order.createdAt}"
                                            pattern="dd MMM yyyy, hh:mm a"/>
                                </td>

                                <!-- Order total -->
                                <td class="admin-order-total">
                                    ₹<fmt:formatNumber
                                            value="${order.totalAmount}"
                                            minFractionDigits="2"
                                            maxFractionDigits="2"/>
                                </td>

                                <!-- Current status -->
                                <td>
                                    <span class="admin-status <c:choose><c:when test='${order.status eq "DELIVERED"}'>admin-status-delivered</c:when><c:when test='${order.status eq "CANCELLED"}'>admin-status-cancelled</c:when><c:when test='${order.status eq "PREPARING"}'>admin-status-preparing</c:when><c:otherwise>admin-status-pending</c:otherwise></c:choose>">
                                        <c:out value="${order.status}"/>
                                    </span>
                                </td>

                                <!-- Update status form -->
                                <td>
                                    <form method="post"
                                          action="${pageContext.request.contextPath}/admin/orders"
                                          class="admin-status-form">

                                        <input type="hidden"
                                               name="orderId"
                                               value="${order.id}">

                                        <label class="visually-hidden"
                                               for="status-${order.id}">
                                            Status for order ${order.id}
                                        </label>

                                        <select name="status"
                                                id="status-${order.id}"
                                                class="admin-status-select">

                                            <option value="PENDING"
                                                ${order.status eq 'PENDING' ? 'selected' : ''}>
                                                Pending
                                            </option>

                                            <option value="PREPARING"
                                                ${order.status eq 'PREPARING' ? 'selected' : ''}>
                                                Preparing
                                            </option>

                                            <option value="DELIVERED"
                                                ${order.status eq 'DELIVERED' ? 'selected' : ''}>
                                                Delivered
                                            </option>

                                            <option value="CANCELLED"
                                                ${order.status eq 'CANCELLED' ? 'selected' : ''}>
                                                Cancelled
                                            </option>

                                        </select>

                                        <button type="submit"
                                                class="admin-update-button">
                                            Update
                                        </button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>

                    </table>
                </div>
            </c:otherwise>
        </c:choose>

    </section>

    <p class="admin-dashboard-footer">
        Foodie Admin Dashboard · Manage orders responsibly
    </p>

</main>

</body>
</html>
