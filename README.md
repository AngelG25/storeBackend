# 🚀 Proyecto: Sistema de Gestión de Clientes, Productos y Carrito de Compras

## 📌 Descripción
Este proyecto es una aplicación **Spring Boot** monolítica que gestiona clientes, productos y un carrito de compras. Se usa **Spring Security con JWT** para autenticación, **Spring Data JPA** para el acceso a la base de datos **PostgreSQL**, y se despliega con **Docker**.

---

## 🏗 Arquitectura del Proyecto

📁 **Estructura del código:**
```
/src/main/java/com/miapp/
│── controller/       # Controladores REST
│   ├── ClienteController.java
│   ├── ProductoController.java
│   ├── CarritoController.java
│── service/          # Lógica de negocio
│   ├── ClienteService.java
│   ├── ProductoService.java
│   ├── CarritoService.java
│── repository/       # Repositorios JPA
│   ├── ClienteRepository.java
│   ├── ProductoRepository.java
│   ├── CarritoRepository.java
│── model/            # Entidades de la BD
│   ├── Cliente.java
│   ├── Producto.java
│   ├── Carrito.java
│── security/         # Configuración de seguridad JWT
│   ├── JwtUtil.java
│   ├── SecurityConfig.java
│── MiAppApplication.java
```

---

## 🛠 Tecnologías Usadas
- **Java 17**
- **Spring Boot 3.x**
- **Spring Security con JWT**
- **Spring Data JPA**
- **PostgreSQL**
- **Docker & Docker Compose**
- **Lombok** (Para reducir código boilerplate)

---

## ⚙️ Configuración de PostgreSQL con Docker

Crea un archivo `docker-compose.yml` y agrega lo siguiente:
```yaml
version: '3.8'
services:
  postgres:
    image: postgres:latest
    container_name: postgres-db
    restart: always
    environment:
      POSTGRES_USER: admin
      POSTGRES_PASSWORD: admin
      POSTGRES_DB: microservices_db
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data
volumes:
  postgres_data:
```
Para levantar la base de datos ejecuta:
```sh
docker-compose up -d
```

---

## 🔑 Configuración de Seguridad con JWT

El sistema usa **JWT (JSON Web Token)** para autenticación segura.
Ejemplo de generación de token en `JwtUtil.java`:
```java
@Component
public class JwtUtil {
    private final String SECRET_KEY = "mysecretkey";
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
}
```

---

## 🚀 Cómo Ejecutar el Proyecto

1️⃣ **Clonar el repositorio**
```sh
git clone https://github.com/tu-usuario/tu-repositorio.git
cd tu-repositorio
```

2️⃣ **Levantar PostgreSQL con Docker**
```sh
docker-compose up -d
```

3️⃣ **Configurar `application.properties`**
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/microservices_db
spring.datasource.username=admin
spring.datasource.password=admin
spring.jpa.hibernate.ddl-auto=update
```

4️⃣ **Compilar y ejecutar el proyecto**
```sh
mvn clean install
mvn spring-boot:run
```

---

## 📮 API Endpoints
| Método | Endpoint         | Descripción                      |
|--------|-----------------|----------------------------------|
| GET    | /clientes       | Lista todos los clientes        |
| POST   | /clientes       | Crea un nuevo cliente           |
| GET    | /productos      | Lista todos los productos       |
| POST   | /productos      | Crea un nuevo producto          |
| GET    | /carrito/{id}   | Obtiene un carrito por ID       |
| POST   | /carrito        | Agrega un producto al carrito   |

---

## 📜 Licencia
Este proyecto está bajo la licencia **MIT**.

