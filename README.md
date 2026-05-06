# API Test Automation - Rest Assured

A comprehensive REST API testing framework built with **Java**, **Rest Assured**, and **TestNG** for automating API test cases with ease and efficiency.

## 📋 Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Project Structure](#project-structure)
- [Dependencies](#dependencies)
- [Getting Started](#getting-started)
- [Running Tests](#running-tests)
- [Configuration](#configuration)
- [Contributing](#contributing)
- [License](#license)

## 🎯 Overview

This project provides a robust framework for automating REST API testing using Rest Assured library. It enables developers and QA engineers to write scalable, maintainable, and reusable API test automation scripts. The framework is built around testing APIs like reqres.in and supports comprehensive API validation scenarios.

## ✨ Features

- **Rest Assured Integration**: Fluent API for REST API testing
- **TestNG Framework**: Comprehensive test execution and reporting
- **JSON Support**: Built-in JSON handling and parsing
- **Data Faker**: Generate realistic test data using JavaFaker
- **OAuth Support**: OAuth authentication capabilities via ScribeJava
- **Maven Build Tool**: Easy dependency management and build automation
- **Java 11+**: Modern Java features and compatibility

## 📦 Prerequisites

Before you begin, ensure you have installed:
- **Java Development Kit (JDK) 11** or higher
- **Apache Maven 3.6+**
- **Git**
- An IDE (IntelliJ IDEA, Eclipse, or VS Code recommended)

## 🔧 Installation

### 1. Clone the Repository

```bash
git clone https://github.com/AshfatAbegh/API-Test-Automation-Rest-Assured.git
cd API-Test-Automation-Rest-Assured
```

### 2. Install Dependencies

```bash
mvn clean install
```

This will download all required dependencies specified in `pom.xml`.

## 📁 Project Structure

```
API-Test-Automation-Rest-Assured/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── (main source code)
│   │   └── resources/
│   └── test/
│       ├── java/
│       │   └── (test classes)
│       └── resources/
│           └── (test data, configurations)
├── pom.xml
├── README.md
└── .gitignore
```

## 📚 Dependencies

The project uses the following key dependencies:

| Dependency | Version | Purpose |
|-----------|---------|---------|
| **Rest Assured** | 5.3.2 | REST API testing library |
| **TestNG** | 7.7.1 | Testing framework |
| **JSON** | 20231013 | JSON parsing and handling |
| **JavaFaker** | 1.0.2 | Test data generation |
| **ScribeJava** | 8.3.1 | OAuth authentication |

For complete dependency list, see [pom.xml](pom.xml).

## 🚀 Getting Started

### Writing Your First Test

1. Create a test class in `src/test/java/`
2. Extend or use TestNG annotations
3. Use Rest Assured's fluent API to build API requests

Example:

```java
import io.restassured.RestAssured;
import org.testng.annotations.Test;

public class ApiTest {
    
    @Test
    public void testGetRequest() {
        RestAssured.given()
            .baseUri("https://reqres.in")
            .when()
            .get("/api/users/1")
            .then()
            .statusCode(200);
    }
}
```

### Organizing Tests

- Group related tests in test classes
- Use `@BeforeClass` and `@AfterClass` for setup and teardown
- Utilize data providers for parameterized testing
- Create utility classes for common operations

## ▶️ Running Tests

### Run All Tests

```bash
mvn test
```

### Run Specific Test Class

```bash
mvn test -Dtest=TestClassName
```

### Run Specific Test Method

```bash
mvn test -Dtest=TestClassName#testMethodName
```

### Generate Test Report

```bash
mvn surefire-report:report
```

Test reports are generated in `target/surefire-reports/`.

## ⚙️ Configuration

### Maven Compiler Properties

The project is configured to compile with Java 11:

```xml
<properties>
    <maven.compiler.source>11</maven.compiler.source>
    <maven.compiler.target>11</maven.compiler.target>
</properties>
```

### Base URI Configuration

Set base URI for your API tests:

```java
RestAssured.baseURI = "https://reqres.in";
RestAssured.basePath = "/api";
```

### Logging Configuration

Enable request/response logging:

```java
RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
```

## 🔌 Common Use Cases

### GET Request

```java
given()
    .baseUri("https://reqres.in")
    .when()
    .get("/api/users")
    .then()
    .statusCode(200)
    .body("data", hasSize(greaterThan(0)));
```

### POST Request

```java
given()
    .baseUri("https://reqres.in")
    .header("Content-Type", "application/json")
    .body("{\"name\": \"John\", \"job\": \"Engineer\"}")
    .post("/api/users")
    .then()
    .statusCode(201);
```

### JSON Response Validation

```java
given()
    .baseUri("https://reqres.in")
    .when()
    .get("/api/users/1")
    .then()
    .body("data.first_name", equalTo("George"))
    .body("data.email", containsString("@"));
```

### Using JavaFaker for Test Data

```java
import com.github.javafaker.Faker;

Faker faker = new Faker();
String name = faker.name().fullName();
String email = faker.internet().emailAddress();

given()
    .baseUri("https://reqres.in")
    .body("{\"name\": \"" + name + "\", \"email\": \"" + email + "\"}")
    .post("/api/users")
    .then()
    .statusCode(201);
```

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

This project is open source and available under the MIT License.

## 📧 Contact & Support

For questions, issues, or suggestions, please open a [GitHub Issue](https://github.com/AshfatAbegh/API-Test-Automation-Rest-Assured/issues) in the repository.

---

**Happy Testing! 🧪**
