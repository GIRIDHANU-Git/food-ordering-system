
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Cart | Foodie</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<header class="site-header">
    <div class="nav-container">
        <a class="brand" href="${pageContext.request.contextPath}/menu">
            <span class="brand-icon">F</span>
            <span>foodie<span class="brand-dot">.</span></span>
        </a>

        <nav class="nav-links">
            <a class="nav-link"
               href="${pageContext.request.contextPath}/menu">Menu</a>

            <a class="nav-link"
               href="${pageContext.request.contextPath}/my-orders">My Orders</a>

            <a class="cart-link active"
               href="${pageContext.request.contextPath}/cart">
                <span aria-hidden="true">🛒</span> My Cart
            </a>
        </nav>
    </div>
</header>

<main class="page-container cart-page">

    <section class="page-intro">
        <span class="eyebrow">YOUR FOOD, YOUR WAY</span>
        <h1>My <span class="accent-text">Cart</span></h1>
        <p>Review your favourites before checking out.</p>
    </section>

    <c:choose>
        <c:when test="${empty cartItems}">
            <section class="empty-state cart-empty">
                <div class="empty-icon">🛒</div>
                <h2>Your cart is waiting for something delicious</h2>
                <p>You haven't added any food to your cart yet.</p>

                <a class="primary-button"
                   href="${pageContext.request.contextPath}/menu">
                    Browse Food Menu →
                </a>
            </section>
        </c:when>

        <c:otherwise>
            <div class="cart-layout">

                <section class="cart-items-panel">
                    <div class="cart-panel-heading">
                        <div>
                            <h2>Your items</h2>
                            <p>Check quantities and update your order.</p>
                        </div>
                        <a class="text-link"
                           href="${pageContext.request.contextPath}/menu">
                            + Add more
                        </a>
                    </div>

                    <c:forEach var="item" items="${cartItems}">
                        <article class="cart-item">

                            <div class="cart-item-icon" aria-hidden="true">
                                🍽️
                            </div>

                            <div class="cart-item-details">
                                <p class="food-category">
                                    <c:out value="${item.food.category}"/>
                                </p>

                                <h3>
                                    <c:out value="${item.food.name}"/>
                                </h3>

                                <p class="cart-unit-price">
                                    ₹<c:out value="${item.food.price}"/> each
                                </p>

                                <div class="cart-item-actions">

                                    <form
                                        action="${pageContext.request.contextPath}/cart"
                                        method="post"
                                        class="quantity-form">

                                        <input type="hidden"
                                               name="action"
                                               value="update">

                                        <input type="hidden"
                                               name="foodId"
                                               value="${item.food.id}">

                                        <label class="visually-hidden"
                                               for="quantity-${item.food.id}">
                                            Quantity for
                                            <c:out value="${item.food.name}"/>
                                        </label>

                                        <input class="quantity-input"
                                               id="quantity-${item.food.id}"
                                               type="number"
                                               name="quantity"
                                               value="${item.quantity}"
                                               min="1"
                                               max="99"
                                               required>

                                        <button class="small-button"
                                                type="submit">
                                            Update
                                        </button>
                                    </form>

                                    <form
                                        action="${pageContext.request.contextPath}/cart"
                                        method="post">

                                        <input type="hidden"
                                               name="action"
                                               value="remove">

                                        <input type="hidden"
                                               name="foodId"
                                               value="${item.food.id}">

                                        <button class="remove-button"
                                                type="submit">
                                            Remove
                                        </button>
                                    </form>

                                </div>
                            </div>

                            <div class="cart-item-subtotal">
                                <span>Subtotal</span>
                                <strong>
                                    ₹<c:out value="${item.subtotal}"/>
                                </strong>
                            </div>

                        </article>
                    </c:forEach>
                </section>

                <aside class="order-summary">
                    <span class="eyebrow">ORDER SUMMARY</span>
                    <h2>Order total</h2>

                    <div class="summary-row">
                        <span>Items subtotal</span>
                        <strong>₹<c:out value="${grandTotal}"/></strong>
                    </div>

                    <div class="summary-row">
                        <span>Delivery fee</span>
                        <strong>₹0.00</strong>
                    </div>

                    <div class="summary-divider"></div>

                    <div class="summary-total">
                        <span>Grand Total</span>
                        <strong>₹<c:out value="${grandTotal}"/></strong>
                    </div>

                    <p class="summary-note">
                        Delivery charges are not included in this demo.
                    </p>

                    <a class="primary-button checkout-button"
                       href="${pageContext.request.contextPath}/checkout">
                        Proceed to Checkout →
                    </a>

                    <a class="continue-link"
                       href="${pageContext.request.contextPath}/menu">
                        ← Continue Shopping
                    </a>
                </aside>

            </div>
        </c:otherwise>
    </c:choose>

</main>

<footer class="site-footer">
    <p>© 2026 Foodie. Made for people who love good food.</p>
</footer>

</body>
</html>
