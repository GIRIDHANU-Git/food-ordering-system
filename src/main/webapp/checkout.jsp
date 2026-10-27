
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Checkout | Foodie</title>

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

            <a class="cart-link"
               href="${pageContext.request.contextPath}/cart">
                <span aria-hidden="true">🛒</span> My Cart
            </a>
        </nav>
    </div>
</header>

<main class="page-container checkout-page">

    <section class="page-intro">
        <span class="eyebrow">JUST ONE MORE STEP</span>
        <h1>Complete your <span class="accent-text">order</span></h1>
        <p>Enter your delivery details and get your favourites on their way.</p>
    </section>

    <div class="checkout-layout">

        <section class="checkout-form-panel">
            <div class="checkout-heading">
                <div class="checkout-step">1</div>
                <div>
                    <h2>Delivery details</h2>
                    <p>Where should we deliver your order?</p>
                </div>
            </div>

            <form action="${pageContext.request.contextPath}/place-order"
                  method="post"
                  class="checkout-form">

                <div class="form-field">
                    <label for="fullName">Full name</label>
                    <input type="text"
                           id="fullName"
                           name="fullName"
                           placeholder="Enter your full name"
                           maxlength="100"
                           autocomplete="name"
                           required>
                </div>

                <div class="form-field">
                    <label for="phone">Phone number</label>
                    <input type="tel"
                           id="phone"
                           name="phone"
                           placeholder="Enter your phone number"
                           maxlength="15"
                           autocomplete="tel"
                           required>
                </div>

                <div class="form-field">
                    <label for="address">Delivery address</label>
                    <textarea id="address"
                              name="address"
                              placeholder="House number, street, area, city and PIN code"
                              maxlength="500"
                              rows="4"
                              autocomplete="street-address"
                              required></textarea>
                </div>

                <button class="primary-button place-order-button"
                        type="submit">
                    Place Order →
                </button>

                <p class="secure-note">
                    Your order will be saved once it is successfully placed.
                </p>
            </form>
        </section>

        <aside class="order-summary checkout-summary">
            <span class="eyebrow">YOUR ORDER</span>
            <h2>Order summary</h2>

            <div class="checkout-summary-art" aria-hidden="true">
                <span>🍔</span>
                <span>🍕</span>
                <span>🥤</span>
            </div>

            <p class="summary-description">
                Your selected food is ready to be ordered.
            </p>

            <div class="summary-divider"></div>

            <div class="summary-row">
                <span>Items total</span>
                <strong>₹<c:out value="${grandTotal}"/></strong>
            </div>

            <div class="summary-row">
                <span>Delivery fee</span>
                <strong>₹0.00</strong>
            </div>

            <div class="summary-divider"></div>

            <div class="summary-total">
                <span>Grand total</span>
                <strong>₹<c:out value="${grandTotal}"/></strong>
            </div>

            <p class="summary-note">
                Delivery fee is set to ₹0 for this demo.
            </p>

            <a class="continue-link"
               href="${pageContext.request.contextPath}/cart">
                ← Back to Cart
            </a>
        </aside>

    </div>
</main>

<footer class="site-footer">
    <p>© 2026 Foodie. Made for people who love good food.</p>
</footer>

</body>
</html>
