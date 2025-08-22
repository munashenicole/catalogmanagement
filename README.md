# Template Spring Boot Application

## Prerequisites
- Java 17+
- Maven 3.9+
- MySQL running locally (or adjust `application.properties`)

## Getting Started
1. Configure database in `src/main/resources/application.properties`.
2. Build and run:
   - Windows PowerShell:
     ```bash
     ./mvnw.cmd spring-boot:run
     ```
   - Maven locally:
     ```bash
     mvn spring-boot:run
     ```

App runs at `http://localhost:8080`.

## Features
- Spring Security with method security and hardened headers
- Global REST exception handling using `ProblemDetail`
- Actuator `/actuator/health` and `/actuator/info`

## Useful Commands
- Run tests:
  ```bash
  mvn test
  ```
- Package jar:
  ```bash
  mvn clean package
  ```


