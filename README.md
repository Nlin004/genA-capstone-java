# Digital Library Management System API

## Business Context

### Overview

The Digital Library Management System is a modern backend API solution designed to digitize and streamline library
operations for public and institutional libraries. As libraries transition from manual record-keeping to digital
platforms, this system provides a comprehensive solution for managing book catalogs, user memberships, and borrowing
workflows.

### Business Problem

Traditional libraries face several operational challenges:

- Manual tracking of book availability and reservations leads to errors and inefficiency
- Limited visibility into borrowing patterns and inventory usage
- Poor user experience with no self-service capabilities for browsing or reserving books
- Difficulty managing waitlists when popular books are unavailable
- No analytics or insights for collection development decisions

### Solution

Our Digital Library Management System provides:

- **Self-service portal** for users to browse, search, and reserve books online
- **Automated reservation management** with waitlist functionality
- **Real-time availability tracking** to reduce operational overhead
- **Analytics dashboard** for librarians to make data-driven decisions
- **Scalable architecture** supporting multiple library branches

### Target Users

1. **Library Patrons**: Browse catalog, reserve books, manage reading lists
2. **Librarians**: Manage collections, process reservations, track inventory
3. **Library Administrators**: View analytics, generate reports, system configuration

---

## Milestones and References

1. [User Stories](docs/user-stories.md)
2. [API Contracts](docs/api-contracts.md)
3. [Milestone 1: Project Setup & Core Infrastructure](docs/milestone-1-project-setup-core-infrastructure.md)
4. [Milestone 2: User Service & Authentication](docs/milestone-2-user-service-authentication.md)
5. [Milestone 3: Catalog Service](docs/milestone-3-catalog-service.md)
6. [Milestone 4: Reservation Service - Core Functionality](docs/milestone-4-reservation-service-core-functionality.md)
7. [Milestone 5: Waitlist & Personal Collections](docs/milestone-5-waitlist-personal-collections.md)
8. [Milestone 6: Admin Dashboard & Analytics](docs/milestone-6-admin-dashboard-analytics.md)
9. [Milestone 7: Testing & Quality Assurance](docs/milestone-7-testing-quality-assurance.md)
10. [Milestone 8: Deployment & Production Readiness](docs/milestone-8-deployment-production-readiness.md)

---

## Technical Stack Summary

### Core Technologies

- **Java**: 17 or 21 (LTS)
- **Spring Boot**: 3.2+
- **Spring Security**: 6.2+
- **Spring Data JPA**: 3.2+
- **PostgreSQL**: 15+

### Additional Libraries

- **JWT**: io.jsonwebtoken:jjwt
- **Validation**: spring-boot-starter-validation
- **OpenAPI**: springdoc-openapi-starter-webmvc-ui
- **Testing**: JUnit 5, Mockito, TestContainers

---

## Success Criteria

### Functional Requirements

- ✅ All 18 user stories fully implemented
- ✅ 50+ API endpoints documented and functional
- ✅ Complete reservation lifecycle working end-to-end
- ✅ Role-based access control enforced
- ✅ Admin dashboard with real-time analytics

### Technical Requirements

- ✅ >80% test coverage across all services
- ✅ All API responses <500ms (p95)
- ✅ Zero critical security vulnerabilities
- ✅ OpenAPI documentation complete
- ✅ Deployed to AWS with RDS integration

### Quality Standards

- ✅ Clean code principles followed
- ✅ SOLID principles applied
- ✅ Comprehensive error handling
- ✅ Proper logging throughout
- ✅ Production-ready configuration

---

## Getting Started

### Prerequisites

```bash
- Java 17+
- Maven 3.8+ or Gradle 8+
- Docker & Docker Compose
- PostgreSQL 15+ (local or Docker)
- AWS Account (for deployment phase)
```

### Local Development Setup

```bash
# Clone repository
git clone <repository-url>
cd java-capstone

# Start application
./mvnw spring-boot:run

# Access Swagger UI
open http://localhost:8080/swagger-ui.html
```

### Running Tests

```bash
# Run all tests
./mvnw test

# Run integration tests
./mvnw verify

# Generate coverage report
./mvnw jacoco:report
```
