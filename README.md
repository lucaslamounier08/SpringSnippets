# Java 21 + Spring Boot 3.4.2 Application

This repository contains a simple Java application built with Java 21 and Spring Boot 3.4.2. The purpose of this project is to serve as a sandbox for testing and learning new features, frameworks, and libraries in a hands-on way.

---

## Getting Started

### Prerequisites
- Java 21
- Maven 3.8+
- An IDE of your choice (e.g., IntelliJ IDEA, Eclipse, VS Code)
- Docker (optional, for containerized testing)

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/lucaslamounier08/SpringSnippets.git
   cd SpringSnippets
   ```
2. Build the project:
   ```bash
   mvn clean install
   ```
3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

### Accessing the Application
- Default API URL: `http://localhost:8080`

---

## Features
This section lists the features and components implemented in the project. Use it to document what you add as you develop further.

### Implemented Features
- Basic Spring Boot setup with Java 21 compatibility.
- Sample REST endpoint: `GET /api/health` returns a simple health check message.
- Maven configuration for dependency management.

### Upcoming or Planned Features
- [x] Health check with Spring Actuator
- [x] Database integration (e.g., PostgreSQL, MySQL, or H2).
  - [x] H2 integration with Flyway migrations.
- [x] Implement CRUD functionality for a sample entity.
  - [ ] With integration tests
- [ ] Add validation to request payloads.
- [ ] Introduce Spring Security for authentication and authorization.
- [ ] Dockerize the application for easier deployment.
- [ ] Add unit and integration tests with JUnit and Mockito.
- [ ] Explore Spring Boot's Actuator for monitoring.
- [ ] Set up OpenAPI documentation (e.g., Swagger UI).
- [ ] Implement caching using Redis.
- [ ] Experiment with messaging systems like RabbitMQ or Kafka.

---

## Contributing
Feel free to fork this repository and create pull requests to contribute to its growth. You can also open issues for bugs or feature requests.

---

## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

---

## Resources
Here are some useful links to help you as you develop this project:
- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Java 21 Documentation](https://docs.oracle.com/en/java/javase/21/)
- [Maven Documentation](https://maven.apache.org/guides/index.html)

---

Happy coding!