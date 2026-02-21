Java Backend Zest

This is a Spring Boot backend application for the Zest project. It uses Spring Boot, Spring Security (JWT), Spring Data JPA, and MySQL. The application is fully documented with Swagger for easy API testing.

 Features

RESTful APIs with Spring Boot

JWT-based authentication & authorization

MySQL database integration with JPA/Hibernate

Swagger UI for API testing

Fully modular project structure

Gradle build system

 Project Structure
java_backend_zest/
├── src/main/java/com/example/java_backend_zest
│   ├── config       # JWT, Security, Swagger configs
│   ├── controller   # REST controllers
│   ├── entity       # JPA Entities
│   ├── repository   # Spring Data JPA Repositories
│   └── service      # Service layer
├── src/main/resources
│   ├── application.properties
│   └── data.sql (optional)
├── build.gradle
├── settings.gradle
└── README.md
⚙️ Prerequisites

Java 17+

Gradle 7+

MySQL 8+

IDE (IntelliJ/Eclipse/VSCode)

🔧 Setup & Run

Clone the repo

git clone https://github.com/Kunal8577/java-backend-zest.git
cd java-backend-zest

Configure MySQL

Create a database, e.g., zest_db

Update application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/zest_db
spring.datasource.username=<your-db-username>
spring.datasource.password=<your-db-password>

Build & Run

./gradlew bootRun     # Linux/Mac
gradlew.bat bootRun   # Windows

Access Swagger UI

Open your browser:
http://localhost:9090/swagger-ui.html

Test all APIs from the Swagger interface.

🛠️ Technologies Used

Spring Boot 3.x

Spring Security (JWT)

Spring Data JPA & Hibernate

MySQL

Swagger/OpenAPI

Gradle

Java 17

🔗 API Documentation

Swagger UI: http://localhost:9090/swagger-ui.html

Dockerfile
# Use OpenJDK 17 base image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy Gradle build files
COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle

# Copy source code
COPY src ./src

# Give execute permission to Gradle wrapper
RUN chmod +x ./gradlew

# Build the application
RUN ./gradlew clean build -x test

# Expose port 9090
EXPOSE 9090

# Run the Spring Boot app
ENTRYPOINT ["java","-jar","build/libs/java_backend_zest-0.0.1-SNAPSHOT.jar"]
docker-compose.yml
version: "3.9"

services:
  db:
    image: mysql:8.0
    container_name: zest-db
    restart: always
    environment:
      MYSQL_DATABASE: zest_db
      MYSQL_ROOT_PASSWORD: rootpassword
      MYSQL_USER: zestuser
      MYSQL_PASSWORD: zestpass
    ports:
      - "3306:3306"
    volumes:
      - db_data:/var/lib/mysql

  app:
    build: .
    container_name: java-backend-zest
    restart: always
    ports:
      - "9090:9090"
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://db:3306/zest_db
      SPRING_DATASOURCE_USERNAME: zestuser
      SPRING_DATASOURCE_PASSWORD: zestpass
    depends_on:
      - db

volumes:
  db_data:
