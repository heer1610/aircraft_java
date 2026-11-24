# Aircraft Management System

A Java web application for managing airlines, aircraft and aircraft maintenance records, with JWT-based authentication and role-based access control.

Built with **Spring Boot 3.2**, **Spring Security**, **Hibernate / Spring Data JPA**, **Thymeleaf** and **MySQL**.

## Features

- Register and log in with JWT authentication
- Role-based access: Admin, Technician, Viewer
- Manage airlines (add, view, update, delete)
- Manage aircraft (add, view, update, delete, filter by status or airline)
- Log and review maintenance records per aircraft or per technician
- Validation and a global exception handler with clear error responses
- Dashboard and pages for aircraft, airlines and maintenance

## Tech Stack

| Layer      | Technology                              |
|------------|-----------------------------------------|
| Language   | Java 17                                 |
| Framework  | Spring Boot 3.2.5 (Web, Validation)     |
| Security   | Spring Security, JWT (jjwt 0.11.5)      |
| Data       | Spring Data JPA, Hibernate, MySQL       |
| Frontend   | Thymeleaf templates (HTML, CSS, JS)     |
| Build      | Maven                                   |

## Project Structure

```
aircraft_enhanced/
├── pom.xml
└── src/main/
    ├── java/com/student/aircraft/
    │   ├── controller/   REST + web controllers
    │   ├── service/      Business logic
    │   ├── repository/   JPA repositories
    │   ├── model/        Entities (Aircraft, Airline, MaintenanceRecord, User, Role)
    │   ├── dto/          Login / register request and response objects
    │   ├── security/     JWT filter, utilities, user details service
    │   ├── config/       Security config, default data seeding
    │   └── exception/    Custom exceptions and global handler
    └── resources/
        ├── application.properties
        └── templates/    Thymeleaf pages
```

More detail is in [ARCHITECTURE.md](aircraft_enhanced/ARCHITECTURE.md) and [IMPLEMENTATION_SUMMARY.md](aircraft_enhanced/IMPLEMENTATION_SUMMARY.md).

## Getting Started

### Prerequisites

- JDK 17 or newer
- Maven (or use the included wrapper)
- MySQL (for example via XAMPP)

### Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/YOUR-USERNAME/aircraft_java.git
   cd aircraft_java/aircraft_enhanced
   ```
2. Create the database:
   ```sql
   CREATE DATABASE aircraftdb;
   ```
3. Check the database settings in `src/main/resources/application.properties` (default user `root`, empty password) and change them to match your MySQL.
4. Run the app:
   ```bash
   mvn spring-boot:run
   ```
5. Open http://localhost:8080

Tables are created automatically on first run (`spring.jpa.hibernate.ddl-auto=update`).

## Default Accounts

Created automatically on first start:

| Role       | Username  | Password   |
|------------|-----------|------------|
| Admin      | `admin`   | `admin123` |
| Technician | `tech1`   | `tech123`  |
| Viewer     | `viewer1` | `view123`  |

> Change these passwords and the `jwt.secret` value before using the app anywhere other than your own machine.

## Roles and Permissions

| Action                         | Admin | Technician | Viewer |
|--------------------------------|:-----:|:----------:|:------:|
| View airlines, aircraft, maintenance | ✅ | ✅ | ✅ |
| Add / update aircraft          | ✅    | ✅         | ❌     |
| Add / update maintenance       | ✅    | ✅         | ❌     |
| Delete aircraft                | ✅    | ❌         | ❌     |
| Manage airlines                | ✅    | ❌         | ❌     |
| Manage users and roles         | ✅    | ❌         | ❌     |

## API Overview

| Endpoint                                   | Methods                 |
|--------------------------------------------|-------------------------|
| `/api/auth/register`, `/api/auth/login`    | POST                    |
| `/api/airlines`, `/api/airlines/{id}`      | GET, POST, PUT, DELETE  |
| `/api/aircraft`, `/api/aircraft/{id}`      | GET, POST, PUT, DELETE  |
| `/api/aircraft/status/{status}`            | GET                     |
| `/api/aircraft/airline/{airlineId}`        | GET                     |
| `/api/maintenance`, `/api/maintenance/{id}`| GET, POST, PUT, DELETE  |
| `/api/maintenance/aircraft/{aircraftId}`   | GET                     |
| `/api/maintenance/technician/{userId}`     | GET                     |

Send the token from login as `Authorization: Bearer <token>` on protected endpoints.

## License

This project was created for learning purposes.
