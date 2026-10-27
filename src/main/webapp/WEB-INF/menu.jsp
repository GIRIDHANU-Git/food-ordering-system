
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Our Menu | Foodie</title>
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
            <a class="nav-link active"
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

<main class="page-container">

    <section class="hero">
        <div class="hero-content">
            <span class="eyebrow">FRESHLY PREPARED • MADE WITH CARE</span>
            <h1>Good food.<br><span>Good mood.</span></h1>
            <p>
                Discover your favourites, choose your meal,
                and let the good food moments begin.
            </p>
            <a class="primary-button hero-button" href="#food-menu">
                Explore the menu <span aria-hidden="true">↓</span>
            </a>
        </div>

        <div class="hero-art" aria-hidden="true">
            <div class="hero-circle">
                <span class="hero-food">🍔</span>
                <span class="floating-food food-one">🍟</span>
                <span class="floating-food food-two">🍕</span>
                <span class="floating-food food-three">🥤</span>
            </div>
        </div>
    </section>

    <section class="menu-section" id="food-menu">
        <div class="section-heading">
            <div>
                <span class="eyebrow">MADE FOR YOUR CRAVINGS</span>
                <h2>Explore our menu</h2>
                <p>Find something delicious for your next meal.</p>
            </div>
            <span class="menu-label">Our selection</span>
        </div>

        <c:choose>
            <c:when test="${empty foods}">
                <div class="empty-state">
                    <div class="empty-icon">🍽️</div>
                    <h3>No food items available yet</h3>
                    <p>Please check back later for today's menu.</p>
                </div>
            </c:when>

            <c:otherwise>
                <div class="food-grid">
                    <c:forEach var="food" items="${foods}">
                        <article class="food-card">
                            <div class="food-image">

                                <c:set var="categoryName"
                                       value="${fn:toLowerCase(fn:trim(food.category))}"/>

                                <c:choose>
                                    <%-- Pizza --%>
                                    <c:when test="${fn:contains(categoryName, 'pizza')}">
                                        <img
                                            src="https://images.unsplash.com/photo-1579751626657-72bc17010498?auto=format&fit=crop&w=700&q=80"
                                            alt="Pizza"
                                            loading="lazy">
                                    </c:when>

                                    <%-- Burgers --%>
                                    <c:when test="${fn:contains(categoryName, 'burger')}">
                                        <img
                                            src="https://images.unsplash.com/photo-1568901346375-23c9450c58cd?auto=format&fit=crop&w=700&q=80"
                                            alt="Burger"
                                            loading="lazy">
                                    </c:when>

                                    <%-- Desserts --%>
                                    <c:when test="${fn:contains(categoryName, 'dessert') or fn:contains(categoryName, 'sweet')}">
                                        <img
                                            src="https://images.unsplash.com/photo-1606313564200-e75d5e30476c?auto=format&fit=crop&w=700&q=80"
                                            alt="Dessert"
                                            loading="lazy">
                                    </c:when>

                                    <%-- Drinks --%>
                                    <c:when test="${fn:contains(categoryName, 'drink') or fn:contains(categoryName, 'beverage')}">
                                        <img
                                            src="https://images.unsplash.com/photo-1517701604599-bb29b565090c?auto=format&fit=crop&w=700&q=80"
                                            alt="Drink"
                                            loading="lazy">
                                    </c:when>

                                    <%-- Indian food --%>
                                    <c:when test="${fn:contains(categoryName, 'indian')}">
                                        <img
                                            src="https://images.unsplash.com/photo-1546833999-b9f581a1996d?auto=format&fit=crop&w=700&q=80"
                                            alt="Indian food"
                                            loading="lazy">
                                    </c:when>

                                    <%-- Default --%>
                                    <c:otherwise>
                                        <img
                                            src="https://images.unsplash.com/photo-1546069901-ba9599a7e63c?auto=format&fit=crop&w=700&q=80"
                                            alt="Prepared food"
                                            loading="lazy">
                                    </c:otherwise>
                                </c:choose>

                                <span class="food-tag">Fresh pick</span>
                            </div>

                            <div class="food-info">
                                <p class="food-category">
                                    <c:out value="${food.category}"/>
                                </p>

                                <h3 class="food-name">
                                    <c:out value="${food.name}"/>
                                </h3>

                                <div class="food-bottom">
                                    <p class="food-price">
                                        ₹<c:out value="${food.price}"/>
                                    </p>

                                    <form action="${pageContext.request.contextPath}/cart"
                                          method="post">
                                        <input type="hidden"
                                               name="action"
                                               value="add">
                                        <input type="hidden"
                                               name="foodId"
                                               value="${food.id}">

                                        <button class="add-button" type="submit">
                                            <span aria-hidden="true">+</span>
                                            Add
                                        </button>
                                    </form>
                                </div>
                            </div>
                        </article>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>
    </section>

    <section class="bottom-banner">
        <div>
            <h2>Your next favourite meal is waiting.</h2>
            <p>Pick your favourites and add them to your cart.</p>
        </div>
        <a class="banner-button"
           href="${pageContext.request.contextPath}/cart">
            View my cart →
        </a>
    </section>

</main>

<footer class="site-footer">
    <p>© 2026 Foodie. Made for people who love good food.</p>
</footer>

</body>
</html>
