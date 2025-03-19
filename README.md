# 🚀 Project: Client, Product, and Shopping Cart Management System (Microservices Architecture)

## 📌 Project Specifications

### 🏗 Architecture
- **Application Type:** Distributed system based on microservices.
- **Microservices:**
  - **Products Service:** exposed on port `8080`.
  - **Carts Service:** exposed on port `8081`.
  - **Clients Service:** exposed on port `8082`.
- **Communication:** REST-API between services via HttpClient.
- **Database:** Embedded H2 database for each microservice.
- **API Documentation:** Integrated Swagger UI in each service.
- **Testing:** Unit tests implemented across services.

### 🛠 Technologies Used
- **Language:** Java 21.
- **Framework:** Spring Boot 3.4.2.
- **Persistence:** Spring Data JPA with Hibernate.
- **Dependency Management:** Maven.
- **Database:** H2 embedded for simplicity.
- **API Documentation:** Swagger OpenAPI 3.

### ⚙️ Functional Overview
- **Products Service:** Handles the creation and retrieval of available products.
- **Clients Service:** Allows creating and managing clients; when a client is created, a shopping cart is automatically generated and assigned.
- **Carts Service:** Adds products to shopping carts by consuming data from the Products Service.

### 🚪 Microservice Ports
| Microservice         | Port  |
|----------------------|-------|
| Products Service     | 8080  |
| Carts Service        | 8081  |
| Clients Service      | 8082  |

### 🧪 Tests
- Each microservice contains unit tests to ensure code quality.

### 🔥 Requirements
- **JDK 21** installed.
- **Maven 3.8+** for project build.
- No Docker or PostgreSQL needed as **H2 embedded** is used for each service.

### 📮 Main Endpoints
| Microservice         | Method | Endpoint                  | Description                                   |
|----------------------|--------|---------------------------|-----------------------------------------------|
| **Clients Service**  | GET    | /clientes                 | Lists all clients                             |
|                      | POST   | /clientes                 | Creates a new client (and its associated cart)|
| **Products Service** | GET    | /productos                | Lists all products                            |
|                      | POST   | /productos                | Creates a new product                         |
| **Carts Service**    | GET    | /carrito/{id}             | Retrieves a cart by ID                        |
|                      | POST   | /carrito                  | Adds a product to an existing cart            |

### 📚 Swagger UI
- Access Swagger documentation at:
  - **Products Service:** `http://localhost:8080/swagger-ui/index.html`
  - **Carts Service:** `http://localhost:8081/swagger-ui/index.html`
  - **Clients Service:** `http://localhost:8082/swagger-ui/index.html`

### 📜 License
This project is licensed under the **MIT** license.
