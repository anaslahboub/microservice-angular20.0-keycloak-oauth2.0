# micro-app


A sample microservices system built with Spring Boot and Spring Cloud. It demonstrates service discovery, an API gateway, inter-service communication, and simple domain models for customers and bank accounts.

## Overview

Services included in this repository:
- discovery-service: Eureka server for service discovery.
- gitway-service: Spring Cloud Gateway that routes requests to services discovered via Eureka.
- customer-service: Manages customers and exposes REST endpoints.
- account-service: Manages bank accounts, calls customer-service via OpenFeign to enrich account data.
- config-service: Placeholder for centralized configuration service (not yet enabled as a Config Server).
- auth-service: Placeholder for authentication service (to be implemented).

## Architecture

- Service Discovery: Eureka (discovery-service).
- API Gateway: Spring Cloud Gateway (gitway-service) with DiscoveryClientRouteDefinitionLocator to create routes from the discovery client.
- Inter-service Communication: OpenFeign client from account-service to customer-service.
- Persistence: Spring Data JPA repositories for customer-service and account-service.
- Data seeding: CommandLineRunner seeds sample customers and accounts on startup (idempotent for customers).

Example flow:
1. account-service receives GET /accounts/{accountId}
2. Loads the BankAccount from its database
3. Calls customer-service to fetch customer details via Feign
4. Returns BankAccount enriched with the transient Customer field

## Project Structure

- account-service/
    - src/main/java/com/anas/accountservice/
        - AccountServiceApplication.java (Spring Boot app; seeds two accounts)
        - controllers/AccountRestController.java (GET /accounts, GET /accounts/{accountId})
        - entities/BankAccount.java
        - enums/AccountType.java
        - repository/BankAccountRepository.java
        - model/Customer.java (used for Feign response; referenced in code)
    - src/test/java/.../AccountServiceApplicationTests.java
- customer-service/
    - src/main/java/com/anas/customerservice/
        - CustomerServiceApplication.java (Spring Boot app; seeds sample customers)
        - controllers/CustomerController.java (GET /customers, GET /customers/{id})
        - entities/Customer.java
        - repository/CustomerRepository.java
    - src/test/java/.../CustomerServiceApplicationTests.java
- discovery-service/
    - src/main/java/com/anas/discoveryservice/DiscoveryServiceApplication.java (@EnableEurekaServer)
    - src/test/java/.../DiscoveryServiceApplicationTests.java
- gitway-service/
    - src/main/java/com/anas/gitwayservice/GitwayServiceApplication.java (Spring Cloud Gateway; DiscoveryClientRouteDefinitionLocator bean)
    - src/test/java/.../GitwayServiceApplicationTests.java
- config-service/
    - src/main/java/com/anas/configservice/ConfigServiceApplication.java (placeholder)
- auth-service/
    - src/main/java/com/anas/authservice/AuthServiceApplication.java (placeholder)
- README.md (this file)

Note: Build files and exact ports are defined in each service’s configuration (e.g., application.properties or application.yml). See the respective service resource files in your IDE.

## REST APIs

- customer-service
    - GET /customers — list all customers
    - GET /customers/{id} — get a customer by id
- account-service
    - GET /accounts — list all bank accounts
    - GET /accounts/{accountId} — get an account by id (includes Customer details fetched from customer-service)

## Getting Started

Prerequisites:
- Java 21+ (recommended)
- Maven or Gradle
- An IDE such as IntelliJ IDEA 

Run order (recommended):
1) discovery-service (Eureka)
2) customer-service
3) account-service
4) gitway-service (API Gateway)
5) config-service (optional placeholder)
6) auth-service (optional placeholder)

You can run each service from your IDE or via command line. Use the wrapper scripts if present in the project; otherwise, use your local Maven/Gradle.

Maven (example):
```bash
cd discovery-service && mvn spring-boot:run
# In new terminals:
cd customer-service && mvn spring-boot:run
cd account-service && mvn spring-boot:run
cd gitway-service && mvn spring-boot:run
```


Once running:
- Open the Eureka dashboard (discovery-service) to verify registrations. The URL/port is defined in discovery-service configuration (commonly http://localhost:8761 but may differ).
- Access services directly using their configured ports, or through the API gateway using discovery-based routes. The gateway auto-creates routes from registered services; routes typically follow the pattern:
    - http://<gateway-host>:<gateway-port>/<service-id>/...
    - The service ID equals the Spring application name (spring.application.name) of each service.

Examples:
- Direct:
    - customer-service: GET /customers
    - account-service: GET /accounts
- Through gateway (example pattern; replace with your service IDs and port):
    - GET http://localhost:<gateway-port>/<customer-service-id>/customers
    - GET http://localhost:<gateway-port>/<account-service-id>/accounts

## Data Seeding

- customer-service:
    - Seeds several customers on first run if the repository is empty.
- account-service:
    - Seeds two accounts with random UUIDs and currencies on startup.

## Tech Stack

- Java, Spring Boot
- Spring Cloud Netflix Eureka (service discovery)
- Spring Cloud Gateway (API gateway)
- Spring Data JPA
- OpenFeign (service-to-service HTTP client)
- JUnit 5 (basic context tests)

## Roadmap / TODO

- config-service: enable Spring Cloud Config Server and externalize configurations.
- auth-service: implement authentication/authorization (e.g., JWT/OAuth2) and secure gateway routes.
- Add Docker and/or docker-compose for one-click local orchestration.
- Add API documentation (e.g., Springdoc OpenAPI) and Postman collection.
- Add CI workflow and health/readiness probes.

## License

This repository currently has no license file. Consider adding one (e.g., MIT, Apache-2.0).

## Acknowledgements

Built and maintained by [anaslahboub](https://github.com/anaslahboub).