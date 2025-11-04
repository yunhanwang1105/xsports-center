<div align="center">

<img src="assets/logo.png" alt="XSports Center" width="140" />

## XSports Center Platform

Enterprise-grade distributed microservices platform built with Spring Boot and Spring Cloud for managing sports facilities, lessons, user accounts, and reservations. Features service discovery via Eureka, unified API gateway routing, JWT-based authentication, and comprehensive quality gates including mutation testing, static analysis, and code coverage reporting.

</div>

---

### Overview
XSports Center is a modular system built with Spring Boot and Spring Cloud. It provides:
- Authentication and user management
- Facilities, locations, equipment and sports taxonomy
- Lessons creation and scheduling
- Reservations across lessons and equipment
- Unified ingress via API Gateway and dynamic service discovery via Eureka

This repository includes full CI-friendly quality tooling: unit and mutation testing, code style, static analysis, and coverage reporting.

### Architecture

<div align="center">

<img src="assets/Architecture diagram.png" alt="Architecture Diagram" width="800" />

</div>

Services and responsibilities:
- API Gateway (`apigateway`): single entrypoint, route orchestration, Eureka client
- Service Registry (`serviceregistry`): Eureka server for discovery
- Authentication Service (`authenticationservice`): credentials, JWT, auth flows
- User Service (`userservice`): user profiles, roles, membership
- Facility Service (`facilityservice`): locations, equipment, sports
- Lesson Service (`lessonservice`): lessons, scheduling
- Reservation Service (`reservationservice`): reservations across lessons/equipment

### Data Model Snapshot

<div align="center">

<img src="assets/Database schema.png" alt="Database Schema" width="800" />

</div>

### API Gateway Routes
Configured in `apigateway/src/main/resources/application.yml`:

```12:35:apigateway/src/main/resources/application.yml
      routes:
        - id: authentication-service
          uri: lb://authentication-service
          predicates:
            - Path=/authentication/**

        - id: facility-service
          uri: lb://facility-service
          predicates:
            - Path=/equipment/**,/location/**,/sports/**

        - id: lesson-service
          uri: lb://lesson-service
          predicates:
            - Path=/lessons/**

        - id: reservation-service
          uri: lb://reservation-service
          predicates:
            - Path=/reservations/**

        - id: user-service
          uri: lb://user-service
          predicates:
            - Path=/users/**,/teams/**
```

### Service Ports (local dev)
- Service Registry (Eureka): 8761
- API Gateway: 9191
- Authentication Service: 1484
- User Service: 3164
- Lesson Service: 1304
- Reservation Service: 1810
- Facility Service: 2832

### Tech Stack
- Spring Boot 2.6.x, Spring Cloud 2020.0.x
- Spring Cloud Gateway, Netflix Eureka (client + server)
- Spring Data JPA, H2 (embedded for local dev)
- Testing: JUnit 5, Mockito, AssertJ, PIT (mutation testing)
- Quality: Checkstyle, PMD, SpotBugs, JaCoCo coverage

### Quality Gates & Reports
Built-in Gradle configuration enforces professional-quality standards across modules:
- Unit tests with coverage (JaCoCo HTML under each module `build/jacocoHtml`)
- Mutation testing (PIT) reports under `build/reports/pitest`
- Static analysis: Checkstyle, PMD, SpotBugs HTML reports per module

Run all checks and produce reports:

```bash
./gradlew clean build test jacocoTestReport
```

### Getting Started
Prerequisites:
- Java 11
- Gradle Wrapper (included)

Clone and build:
```bash
git clone <repo-url>
cd xsports-center
./gradlew clean build -x test
```

Start the platform (new shells/tabs):
```bash
# 1) Service Registry
./gradlew :serviceregistry:bootRun

# 2) API Gateway
./gradlew :apigateway:bootRun

# 3) Domain services (order not important)
./gradlew :authenticationservice:bootRun
./gradlew :userservice:bootRun
./gradlew :facilityservice:bootRun
./gradlew :lessonservice:bootRun
./gradlew :reservationservice:bootRun
```

Access:
- Eureka dashboard: `http://localhost:8761`
- API Gateway (proxying all services): `http://localhost:9191`

### Local Data & Persistence
- H2 is used for local development; data files are under `/data` (and module-local `data/`).
- H2 web console can be enabled via Spring if desired for inspection.

### Observability
- Spring Boot Actuator is included in the gateway; recommended to enable on all services for health, metrics, and readiness endpoints.

### CI/CD
- Includes `.gitlab-ci.yml` for pipeline integration. Typical stages: build, test, static analysis, mutation testing, and artifacts publishing (HTML reports).

### Documentation Inputs
Design and scenario requirements are captured from the attached documents:
- `assets/Scenario.docx`
- `assets/Assignment 1.docx`

These inform service boundaries, API routes, and domain models reflected in the architecture and schema above.

### Project Structure
```1:2:settings.gradle
rootProject.name = 'sem'
include 'template', 'lessonservice', 'userservice', 'authenticationservice', 'serviceregistry', 'apigateway', 'reservationservice', 'facilityservice'
```

### License
Proprietary — All rights reserved.

