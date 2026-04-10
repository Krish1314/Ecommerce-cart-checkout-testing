# 🛒 E-Commerce BDD Testing Project

![Java](https://img.shields.io/badge/Java-17-orange?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3-green?logo=springboot)
![Selenium](https://img.shields.io/badge/Selenium-4.15-43B02A?logo=selenium)
![Cucumber](https://img.shields.io/badge/Cucumber-7.14-23D96C?logo=cucumber)
![React](https://img.shields.io/badge/React-18-61DAFB?logo=react)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-42.7-4169E1?logo=postgresql)

A beginner-friendly, end-to-end testing project for an e-commerce checkout flow on [SauceDemo](https://www.saucedemo.com). Built with **Java**, **Cucumber BDD**, **Selenium**, **Spring Boot**, and a **React** dashboard.

---

## 🎯 What You Will Learn

If you're a beginner, this project teaches you:

| Concept | Where to Look |
|---|---|
| **BDD with Cucumber** — writing tests in plain English | `src/test/resources/features/` |
| **Page Object Model** — organizing Selenium code cleanly | `src/main/java/pages/` |
| **Selenium WebDriver** — automating browser actions | Page classes + Step Definitions |
| **Spring Boot REST API** — building endpoints with Java | `src/main/java/com/krishpatel/api/` |
| **JPA + PostgreSQL** — connecting Java to a database | `Product.java`, `ProductRepository.java` |
| **React** — building a frontend dashboard | `frontend/src/` |

> Every file has detailed inline comments explaining **what** it does and **why**.

---

## 🏗️ Project Structure

```
ecommerce-bdd-testing/
├── src/
│   ├── main/
│   │   ├── java/pages/                  # Selenium Page Object classes
│   │   │   ├── LoginPage.java
│   │   │   ├── ProductPage.java
│   │   │   ├── CartPage.java
│   │   │   └── CheckoutPage.java
│   │   ├── java/com/krishpatel/api/     # Spring Boot REST API
│   │   │   ├── EcommerceApiApplication.java
│   │   │   ├── product/
│   │   │   │   ├── Product.java         # JPA Entity (database model)
│   │   │   │   ├── ProductController.java
│   │   │   │   ├── ProductService.java
│   │   │   │   └── ProductRepository.java
│   │   │   └── common/
│   │   │       └── ApiExceptionHandler.java
│   │   └── resources/
│   │       └── application.properties   # Database config
│   └── test/
│       ├── java/
│       │   ├── hooks/
│       │   │   ├── Hooks.java           # Browser setup/teardown
│       │   │   └── TestContext.java      # Shares data between steps
│       │   ├── runner/
│       │   │   └── TestRunner.java       # Cucumber entry point
│       │   └── stepDefinitions/
│       │       ├── LoginSteps.java
│       │       ├── CartSteps.java
│       │       ├── CheckoutSteps.java
│       │       └── CommonSteps.java
│       └── resources/features/
│           ├── login.feature            # Login test scenarios
│           ├── cart.feature             # Cart test scenarios
│           └── checkout.feature         # Checkout test scenarios
├── frontend/                            # React results dashboard
│   ├── src/
│   │   ├── App.jsx
│   │   └── components/
│   │       ├── TestSummary.jsx
│   │       ├── ResultChart.jsx
│   │       └── ScenarioCard.jsx
│   └── public/
│       └── cucumber-report.json         # Sample test report data
├── pom.xml                              # Maven dependencies
└── README.md
```

---

## ✅ What Is Tested

### Login (`login.feature`)
- ✅ Successful login with valid credentials
- ❌ Login fails with wrong credentials
- ❌ Login fails with empty credentials
- 🔒 Locked-out user cannot login

### Cart (`cart.feature`)
- 🛒 Add a product to the cart
- 🗑️ Remove a product from the cart
- 🛒 Add multiple products

### Checkout (`checkout.feature`)
- 💳 Successful checkout (happy path)
- ❌ Checkout fails with missing first name
- 💰 Verify order total matches product price

### REST API
- `POST /api/products` — create a product
- `GET /api/products` — get all products
- `GET /api/products/{id}` — get product by id

---

## 📋 Prerequisites

Before you start, make sure you have these installed:

- **Java 17+** — [Download](https://adoptium.net/)
- **Maven 3+** — [Download](https://maven.apache.org/download.cgi)
- **Node.js 18+** — [Download](https://nodejs.org/)
- **Google Chrome** — [Download](https://www.google.com/chrome/)
- **PostgreSQL** — [Download](https://www.postgresql.org/download/)

---

## 🚀 How To Run

### 1. Run the BDD Selenium Tests

```bash
# Run all Cucumber test scenarios
mvn test

# Run in headless mode (no visible browser — useful for CI)
mvn test -Dheadless=true
```

After tests complete, check the reports:
- **JSON report**: `target/cucumber-report.json`
- **HTML report**: `target/cucumber-html-report.html`

### 2. Run the Spring Boot API

```bash
# Set your PostgreSQL password
export DB_PASSWORD='your_postgres_password'

# Start the API server
mvn spring-boot:run
```

API is now live at: `http://localhost:8080`

### 3. Run the React Dashboard

```bash
cd frontend
npm install
npm start
```

Dashboard opens at: `http://localhost:3000`

---

## 🧪 Testing the API with Postman

### Create a Product
- **Method**: `POST`
- **URL**: `http://localhost:8080/api/products`
- **Body** (JSON):
```json
{
  "name": "Sauce Labs Backpack",
  "price": 29.99
}
```

### Get All Products
- **Method**: `GET`
- **URL**: `http://localhost:8080/api/products`

### Get Product By ID
- **Method**: `GET`
- **URL**: `http://localhost:8080/api/products/1`

---

## ⚙️ Database Configuration

The API connects to PostgreSQL using settings in `src/main/resources/application.properties`:

| Setting | Default Value |
|---|---|
| Host | `localhost` |
| Port | `5432` |
| Database | `postgres` |
| Username | `krishpatel` |
| Password | from `DB_PASSWORD` env var |

---

## 📌 Quick Example

```gherkin
Feature: Checkout Flow
  Scenario: Successful checkout with valid details
    Given the user has "Sauce Labs Backpack" in the cart
    When the user proceeds to checkout
    And the user enters first name "Krish", last name "Patel", zip "411001"
    And the user clicks Continue and then Finish
    Then the order confirmation message "Thank you for your order!" should be displayed
```

---

## 🤝 Contributing

Contributions are welcome! See [CONTRIBUTING.md](CONTRIBUTING.md) for guidelines.

## 📄 License

This project is licensed under the MIT License — see the [LICENSE](LICENSE) file for details.
