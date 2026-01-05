# 🚀 Project & Task Management System (Riwi Assessment)

Sistema integral para la gestión de proyectos y tareas, desarrollado con un enfoque en **Arquitectura Hexagonal (Ports & Adapters)** y **Domain-Driven Design (DDD)**.

## 🏗️ Decisiones Técnicas y Arquitectura

- **Arquitectura Hexagonal:** Se separó estrictamente la lógica de negocio (Dominio) de los detalles técnicos (Infraestructura). Esto permite que el sistema sea agnóstico a la base de datos o al cliente que lo consume.
- **Seguridad:** Implementación de **Spring Security con JWT**. Los usuarios solo pueden gestionar sus propios recursos, cumpliendo con el aislamiento de datos requerido.
- **Persistencia:** Uso de **Soft Delete** mediante anotaciones de Hibernate (`@SQLDelete` y `@Where`), asegurando que la integridad referencial se mantenga sin eliminar registros físicamente.
- **Dockerización Multi-Etapa:** El proyecto orquesta tres servicios:
    - **PostgreSQL 15:** Almacenamiento persistente.
    - **Spring Boot 3.4 (Java 17):** API REST robusta.
    - **React (Vite):** Interfaz de usuario reactiva y moderna.

## 🛠️ Requisitos previos
- Docker Desktop (Funcionando)
- Maven (o usar el `mvnw` incluido)

## 🚀 Pasos para ejecutar la aplicación

1. **Construir el ejecutable del Backend:**
   ```powershell
   cd backend/demo
   ./mvnw clean package -DskipTests
   cd ../..
   
2.Levantar el ecosistema completo con Docker Compose:
docker compose up --build -d

3.Acceso a las plataformas:
Frontend: http://localhost:5173

Swagger (Documentación API): http://localhost:8080/swagger-ui/index.html

redenciales de prueba (DataInitializer)
El sistema cuenta con un usuario precargado para facilitar la evaluación:

Usuario: admin

Contraseña: admin123

O puedes registrar un nuevo usuario desde el Frontend / Swagger.

