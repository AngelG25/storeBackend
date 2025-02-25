# 🚀 Proyecto: Sistema de Gestión de Clientes, Productos y Carrito de Compras

## 📌 Especificaciones del Proyecto

### 🏗 Arquitectura
- **Tipo de Aplicación:** Monolítica con Spring Boot.
- **Módulos:** Clientes, Productos y Carrito de Compras.
- **Seguridad:** Implementación con Spring Security y JWT.
- **Base de Datos:** PostgreSQL.
- **Despliegue:** Docker y Docker Compose.

### 🛠 Tecnologías Utilizadas
- **Lenguaje:** Java 17.
- **Framework:** Spring Boot 3.x.
- **Persistencia:** Spring Data JPA con Hibernate.
- **Autenticación y Autorización:** Spring Security con JWT.
- **Base de Datos:** PostgreSQL.
- **Gestión de Dependencias:** Maven.
- **Contenerización:** Docker & Docker Compose.

### 🔥 Requisitos
- **JDK 17 o superior.**
- **Maven 3.8+** para gestión de dependencias.
- **Docker y Docker Compose** para la base de datos PostgreSQL.
- **Configuración de variables de entorno para la conexión a la BD y JWT.**

### 🔑 Seguridad
- Implementación de **JWT (JSON Web Token)** para autenticación segura.
- Configuración de permisos y roles con **Spring Security**.

### 📮 Endpoints Principales
| Método | Endpoint         | Descripción                      |
|--------|-----------------|----------------------------------|
| GET    | /clientes       | Lista todos los clientes        |
| POST   | /clientes       | Crea un nuevo cliente           |
| GET    | /productos      | Lista todos los productos       |
| POST   | /productos      | Crea un nuevo producto          |
| GET    | /carrito/{id}   | Obtiene un carrito por ID       |
| POST   | /carrito        | Agrega un producto al carrito   |

### 📜 Licencia
Este proyecto está bajo la licencia **MIT**.

