# DEVICES API

Device Resources API is a RESTful service built with Java and Spring Boot to manage device information, including CRUD operations, database persistence, auditing, and interactive documentation.

---

## Features

- Create, read, update, and delete (CRUD) devices
- Exception handling for common errors
- Auditing fields (createdAt, updatedAt, createdBy, updatedBy)
- MySQL database integration
- Docker Compose setup for easy deployment
- Ready-to-use Postman collection for API testing
- Interactive API documentation via Swagger UI
- Integrated unit and integration testing

---

## Technologies

- [Java 21](<https://www.oracle.com/java/technologies/downloads/>)
- [Spring Boot 3](<https://spring.io/projects/spring-boot/>)
- [Maven](<https://maven.apache.org/>)
- [MySQL](<https://www.mysql.com/>)
- [Spring-data-jpa](<https://spring.io/projects/spring-data-jpa/>)
- [JPA](<https://spring.io/guides/gs/accessing-data-jpa/>)
- [Docker](<https://www.docker.com/>)
- [Docker Compose](<https://docs.docker.com/compose/>)
- [JUnit 5](<https://junit.org/junit5/>)
- [Mockito](<https://site.mockito.org/>)
- [Lombok](<https://projectlombok.org/>)
- [Swagger UI (OpenAPI 3)](<https://swagger.io/specification/>)
- [Postman (for API testing)](<https://www.postman.com/>)

---

## Getting Started

---

### Prerequisites

- Java 21
- Maven 3.9
- Docker and Docker Compose
- Postman (optional, for testing)

---

### Clone the Repository

```bash
git clone git@github.com:abimaelrsergio/devices_api.git
cd devices_api
```

---

### Running Locally

#### 1. Using Docker Compose

Navigate to the `docker-compose` directory and run:

```bash
cd docker-compose
docker-compose up -d
```

This will start:
- A **MySQL** database container
- The **Device Resources API** application container

The API will be available at: [http://localhost:8080](http://localhost:8080)

#### 2. (Alternative) Running with Maven (without Docker)

You must have MySQL running locally and configure the database credentials in `src/main/resources/application.yml`.

Then run:

```bash
./mvnw spring-boot:run
```

---

### Accessing API Documentation

After starting the application (using Docker or Maven), you can access the **Swagger UI** documentation at:

> [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

It provides a complete and interactive description of all available endpoints.

---

### Postman Collection

A ready-to-use Postman collection is included:  
`Tests.postman_collection.json`

#### Import the collection into Postman:

1. Open Postman.
2. Click `Import`.
3. Select the `Tests.postman_collection.json` file.
4. Start testing the API endpoints easily!

---

### API Endpoints

| Method | Endpoint                     | Description           |
|--------|------------------------------|-----------------------|
| GET    | `/api/devices`               | List all devices      |
| GET    | `/api/devices/{id}`          | Get device by ID      |
| GET    | `/api/devices?brand={brand}` | Get device by brand   |
| GET    | `/api/devices?state={state}` | Get device by state   |
| POST   | `/api/devices`               | Create a new device   |
| PUT    | `/api/devices/{id}`          | Update a device       |
| DELETE | `/api/devices/{id}`          | Delete a device       |

### Example Request - Create Device

```bash
curl -X POST http://localhost:8080/devices \
     -H "Content-Type: application/json" \
     -d '{"name": "LapTop", "brand": "Dell", "state": "IN_USE"}'
```

---

### Database Schema

The MySQL database schema is automatically initialized from the `schema.sql` file located in `src/main/resources/`.

---

### Running Tests

To execute all unit and integration tests:

```bash
./mvnw test
```

Tests are located under `src/test/java/com/abimael/deviceresources/`.

---

## Project Structure

```
src/
 ├── main/
 │   ├── java/com/abimael/deviceresources
 │   │    ├── controller/
 │   │    ├── dto/
 │   │    ├── entity/
 │   │    ├── exception/
 │   │    ├── mapper/
 │   │    ├── repository/
 │   │    ├── service/
 │   │    └── util/
 │   └── resources/
 │        ├── application.yml
 │        └── schema.sql
 └── test/
      └── java/com/abimael/deviceresources/
docker-compose/
 └── docker-compose.yml
Tests.postman_collection.json
```

---

## License

```
                                 Apache License
                           Version 2.0, January 2004
                        http://www.apache.org/licenses/
```

Licensed under the [Apache License, Version 2.0](https://www.apache.org/licenses/LICENSE-2.0)

---

# About
With over 26 years of software engineering experience, I am a proactive and responsible Java developer focusing on backend systems. I have strong technical knowledge and a track record of delivering solid systems that increase revenue. I am pursuing my Ph.D. in Informatics and Knowledge Management at Uninove University in Brazil, where I research evolutionary dynamics for population games based on cellular automata, machine learning and genetic algorithms.

I use Java (Spring Boot, Spring Cloud) to create innovative and scalable solutions for various projects. I am constantly updating myself with the newest technologies and concepts on the market to offer the best possible solution for clients. I am passionate about learning new skills and solving challenging problems.

### 🛠 Skills
#### Hard Skills:
• Backend: Java, Spring Boot, Spring Cloud, Docker.\
•  In the process of learning: Containerization & orchestration (Docker Compose, Kubernetes, Helm), observability and monitoring (Prometheus, Grafana, Loki, Tempo, Promtail), messaging systems (Kafka, RabbitMQ, Spring Cloud Stream), and microservices security (OAuth2, OpenID Connect, Spring Security).\
• Databases: PostgreSQL, MongoDB, MySQL, Oracle, Microsoft SQL Server, DB2.\
•  Versioning Tools: Git.\
• Masters in Informatics.\

#### Soft Skills: 
• Curiosity.\
• Teamwork.\
• Proactive.\
• Self-taught.


### Author

- [@abimaelrsergio](https://github.com/abimaelrsergio)
- 📫 Contact me abimaelr.sergio@gmail.com \
[![linkedin](https://img.shields.io/badge/linkedin-0A66C2?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/abimaelsergio)

---

### GitHub Stats

![GitHub Stats](https://github-readme-stats.vercel.app/api?username=abimaelrsergio&theme=transparent&bg_color=000&border_color=30A3DC&show_icons=true&icon_color=30A3DC&title_color=E94D5F&text_color=FFF)
![Top Langs](https://github-readme-stats-git-masterrstaa-rickstaa.vercel.app/api/top-langs/?username=abimaelrsergio&layout=compact&bg_color=000&border_color=30A3DC&title_color=E94D5F&text_color=FFF)

