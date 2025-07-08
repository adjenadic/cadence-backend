# Cadence Backend

Microservices-based music platform backend built with Java 17 and Spring Boot 3.3.2.

## Architecture

### Services
- **User Service** (Port 8000) - Authentication, user management, social features
- **Music Service** (Port 8002) - Music catalog, reviews, search functionality  
- **Notification Service** (Port 8001) - Email notifications via RabbitMQ

### Databases
- **PostgreSQL** - User data, chirps
- **MongoDB** - Music catalog, reviews
- **RabbitMQ** - Message queuing

## Tech Stack

- Java 17, Spring Boot 3.3.2
- Spring Security, Spring Data JPA
- JWT Authentication
- Docker & Docker Compose
- OpenAPI/Swagger documentation

## Quick Start

```bash
# Start all services
docker-compose up

# Start in background
docker-compose up -d

# Stop all services
docker-compose down
```

### Alternative: Infrastructure + Manual Services

```bash
# Start only infrastructure
docker-compose up postgres mongo rabbitmq

# Run microservices separately
cd user-service && ./mvnw spring-boot:run
cd music-service && ./mvnw spring-boot:run  
cd notification-service && ./mvnw spring-boot:run
```

## API Documentation

- User Service: `http://localhost:8000/swagger-ui.html`
- Music Service: `http://localhost:8002/swagger-ui.html`
- Notification Service: `http://localhost:8001/swagger-ui.html`
