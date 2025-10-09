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
- Difficulty managing overdue books and calculating late fees
- Time-consuming checkout and return processes at the library desk

### Solution

Our Digital Library Management System provides:

- **Self-service portal** for users to browse, search, and reserve books online
- **Automated reservation management** with 7-day pickup windows
- **Real-time availability tracking** to reduce operational overhead
- **Librarian tools** for efficient checkout and return processing
- **Borrowing history** for patrons to track their reading activity
- **Scalable architecture** ready for cloud deployment

### Target Users

1. **Library Patrons**: Browse catalog, reserve books, view borrowing history
2. **Librarians**: Process checkouts and returns, manage reservations

---

## Getting Started

### Start Here - Core Requirements

**Before diving into implementation, thoroughly review these foundational documents:**

1. **[User Stories](docs/user-stories.md)** - **START HERE**
    - 11 user stories defining all system functionality
    - Business requirements and acceptance criteria
    - This is your primary requirements document

2. **[API Contracts](docs/api-contracts.md)** - **CRITICAL**
    - Complete external API interface specification
    - All 10 endpoint definitions with request/response formats
    - This defines WHAT your API must do (the contract you must fulfill)

3. **[Development Environment Setup](docs/dev-enviroment-setup.md)**
    - Initial project setup and local development configuration

### Implementation Approach

**Prioritize understanding the requirements over implementation details:**

- **User Stories** define the business requirements and desired outcomes
- **API Contracts** define the exact external interface you must implement
- **Milestone documents** provide guidance on technical approach and acceptance criteria

You have flexibility in **HOW** you implement the solution, but you must meet the requirements defined in the User
Stories and API Contracts.

---

## Project Structure

### Requirements Documentation (Read First)

- **[User Stories](docs/user-stories.md)** - What needs to be built (business requirements)
- **[API Contracts](docs/api-contracts.md)** - External API interface (what your API must expose)

### Implementation Guides (Milestones)

1. [Milestone 1: Data Modeling](docs/milestone-1-data-modeling-guide.md) - Database schema and entity design
2. [Milestone 2: User Service & Authentication](docs/milestone-2-user-service-authentication.md) - Authentication system
3. [Milestone 3: Catalog Service](docs/milestone-3-catalog-service.md) - Book browsing and search
4. [Milestone 4: Reservation Service](docs/milestone-4-reservation-service-core-functionality.md) - Reservation
   lifecycle
5. [Milestone 5: Testing & Quality Assurance](docs/milestone-5-testing-quality-assurance.md) - Comprehensive testing
6. [Milestone 6: Deployment & Production Readiness](docs/milestone-6-deployment-production-readiness.md) - Cloud
   deployment

### Setup Guide

- [Development Environment Setup](docs/dev-enviroment-setup.md) - Local development configuration
- [Production Environment Setup](docs/production-enviroment-setup.md) - Production configuration

---

## API Endpoints (10 Total)

### Authentication & User Management (3 endpoints)

- `POST /api/auth/register` - Create new user account
- `POST /api/auth/login` - Authenticate and receive JWT token
- `GET /api/users/profile` - View user profile with statistics

### Catalog Management (2 endpoints)

- `GET /api/catalog/books` - Browse and search books with pagination
- `GET /api/catalog/books/{bookId}` - View detailed book information

### Reservation Management (5 endpoints)

- `POST /api/reservations` - Reserve an available book
- `GET /api/reservations` - View active reservations
- `POST /api/reservations/{reservationId}/checkout` - Checkout book (Librarian only)
- `POST /api/reservations/{reservationId}/return` - Return book with late fee calculation (Librarian only)
- `GET /api/reservations/history` - View complete borrowing history

**See [API Contracts](docs/api-contracts.md) for complete endpoint specifications.**

---

## Technical Stack

### Required Technologies

- **Java**: 17 or 21 (LTS)
- **Spring Boot**: 3.2+
- **Spring Security**: 6.x with JWT authentication
- **Spring Data JPA**: Database access
- **PostgreSQL**: 15+ (via Docker locally, RDS in production)
- **Maven**: Build tool

### Additional Libraries

You may choose appropriate libraries for:

- JWT token handling
- API documentation (e.g., SpringDoc OpenAPI)
- Testing frameworks
- Validation

### Deployment

- **AWS Elastic Beanstalk**: Application hosting
- **AWS RDS**: PostgreSQL database
- Secure environment configuration

---

## Success Criteria

### Business Requirements

Your implementation must satisfy all requirements from the **User Stories**:

- All 11 user stories fully implemented
- All acceptance criteria met
- All business rules enforced (5 reservation limit, 7-day expiry, 14-day checkout, $1/day late fees)

### API Contract Compliance

Your API must match the **API Contracts** specification exactly:

- All 10 endpoints implemented as specified
- Request/response formats match exactly
- HTTP status codes correct
- Error response format consistent
- Authentication and authorization working as specified

### Technical Quality

- Minimum 80% test coverage
- All endpoints tested (unit and integration tests)
- Proper error handling (400, 401, 403, 404, 500)
- Security properly implemented (JWT, role-based access)
- Successfully deployed to cloud environment

### Functional Verification

- Complete reservation lifecycle works (reserve → checkout → return)
- Role-based access control enforced (PATRON vs LIBRARIAN)
- Real-time availability tracking works correctly
- Late fee calculation accurate
- Pagination and search functional

---

## Development Philosophy

### Requirements First

1. **Understand the requirements** (User Stories and API Contracts)
2. **Plan your implementation** (data model, architecture)
3. **Build to meet the contract** (implement the API as specified)
4. **Verify completeness** (test against acceptance criteria)

### Implementation Flexibility

You have freedom to decide:

- Internal code organization and architecture
- Service layer design patterns
- Repository implementation approaches
- Validation strategies
- Testing frameworks and approaches
- Error handling mechanisms

### Non-Negotiable Constraints

You must adhere to:

- User Story requirements and acceptance criteria
- API Contract specifications (external interface)
- Business rules (reservation limits, dates, fees)
- Technology stack (Spring Boot, PostgreSQL, JWT)
- Security requirements (authentication, authorization)

---

## Quick Start Guide

1. **Read [User Stories](docs/user-stories.md)** to understand what you're building
2. **Study [API Contracts](docs/api-contracts.md)** to understand the exact API interface
3. **Set up your environment** using [Development Environment Setup](docs/dev-enviroment-setup.md)
4. **Follow the milestones** for structured implementation guidance
5. **Test against requirements** to verify you've met all acceptance criteria
6. **Deploy to production** following Milestone 6 guidance

---

## Support & Resources

- **User Stories**: Business requirements and functionality definitions
- **API Contracts**: External API interface specifications
- **Milestone Guides**: Implementation guidance and acceptance criteria
- **Spring Boot Documentation**: Framework reference
- **PostgreSQL Documentation**: Database reference

---

**Remember**: The User Stories and API Contracts define **WHAT** you must build. The milestone documents suggest **HOW**
you might approach it, but you have flexibility in your implementation choices as long as you meet the requirements!