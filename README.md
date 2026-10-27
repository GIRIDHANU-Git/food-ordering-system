# Food Ordering System

A web-based food ordering application built using Java Servlets, JSP, JDBC, and MySQL. Customers can register, log in, browse food items, manage their cart, place orders, and track order status. Administrators can view customer orders and update their status.

## Features

### Customer
- Customer registration and login
- Password hashing using BCrypt
- Browse food items and categories
- Add items to the cart
- Update quantities and remove items
- Checkout with delivery details
- Place orders and view order confirmation
- View order history and individual order details

### Admin
- View all customer orders
- View order details and selected food items
- Update order status
- Restrict administrative pages to authorized users

## Technology Stack

- **Language:** Java 21
- **Web:** Jakarta Servlets and JSP
- **Database access:** JDBC
- **Database:** MySQL
- **Build tool:** Maven
- **Application server:** Apache Tomcat
- **Password hashing:** jBCrypt
- **Frontend:** HTML and CSS

## Project Structure

```text
src/main/
├── java/com/foodorder/
│   ├── dao/          # Database access objects
│   ├── filter/       # Authentication and access control
│   ├── model/        # Application model classes
│   ├── servlet/      # HTTP request handling
│   └── util/         # Database and password utilities
└── webapp/
    ├── css/          # Stylesheets
    ├── WEB-INF/
    ├── menu.jsp
    ├── login.jsp
    ├── register.jsp
    ├── cart.jsp
    ├── checkout.jsp
    ├── my-orders.jsp
    ├── order-details.jsp
    ├── order-success.jsp
    └── admin-orders.jsp
```

## Database

The application uses a MySQL database named `food_ordering_db`.

Main tables:

- `users` — customer and administrator accounts
- `categories` — food categories
- `food_items` — available food items and prices
- `orders` — customer orders, delivery information, totals, and status
- `order_items` — food items and quantities associated with each order

The cart is maintained in the user's HTTP session. Orders and order items are stored in MySQL when checkout succeeds.

## Prerequisites

Install the following before running the application:

- JDK 21
- Apache Maven
- MySQL Server and MySQL Workbench
- Apache Tomcat compatible with your Jakarta Servlet version
- IntelliJ IDEA or another Java IDE

## Setup and Installation

### 1. Clone the repository

```bash
git clone https://github.com/GIRIDHANU-Git/food-ordering-system.git
cd food-ordering-system
```

### 2. Create the database

Open MySQL Workbench and create the database:

```sql
CREATE DATABASE food_ordering_db;
```

Create the required tables and populate the food categories and food items using your database setup SQL.

### 3. Configure the database connection

Update the database connection configuration in `DBConnection.java` to use your local MySQL host, database name, username, and password.

Do not commit real database passwords or other credentials to GitHub.

### 4. Build the application

Run:

```bash
mvn clean package
```

### 5. Deploy to Tomcat

Deploy the generated WAR file from the `target/` directory to Apache Tomcat.

Start Tomcat and open:

```text
http://localhost:8080/food-ordering-system/login
```

The context path may differ depending on how the WAR is deployed.

## Testing

The following application flows have been tested manually:

- Customer registration and login
- Rejection of invalid login credentials
- Customer access restrictions for admin pages
- Cart quantity updates and total calculations
- Checkout and order placement
- Order history and order details
- Admin order viewing and status updates
- Database persistence and order-total calculations

## Future Improvements

- Food search and filtering
- Food item management for administrators
- Online payment integration
- Order status notifications
- Automated integration tests
- Improved deployment configuration

## Author

**GIRIDHANU-Git**

GitHub: https://github.com/GIRIDHANU-Git
