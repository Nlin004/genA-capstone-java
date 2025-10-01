### Milestone 1: Project Setup & Core Infrastructure
**Goal:** Establish development environment and foundational architecture

#### Deliverables:

1. **Project Initialization**
    - Spring Boot 3.x project with Maven
    - Project structure following clean architecture principles (controller → service → repository)
    - Git repository with proper .gitignore
    - README with setup instructions and API overview

2. **Database Configuration**
    - PostgreSQL 15+ setup (local and Docker)
    - Spring Data JPA configuration
    - Flyway migration setup
    - Initial schema design document

3. **Base Entity Models**

   **User Entity:**
    - userId (UUID, Primary Key)
    - email (String, unique, not null)
    - password (String, BCrypt hashed, not null)
    - firstName (String, not null)
    - lastName (String, not null)
    - phoneNumber (String, not null)
    - role (Enum: PATRON, LIBRARIAN)
    - membershipStatus (Enum: ACTIVE, SUSPENDED)
    - memberSince (Timestamp)
    - createdAt, updatedAt (Audit fields)

   **Book Entity:**
    - bookId (UUID, Primary Key)
    - isbn (String, unique, not null)
    - title (String, not null)
    - author (String, not null)
    - genre (String, not null)
    - publicationYear (Integer)
    - description (Text)
    - publisher (String)
    - pageCount (Integer)
    - language (String)
    - totalCopies (Integer, not null, default: 0)
    - availableCopies (Integer, not null, default: 0)
    - status (Enum: AVAILABLE, CHECKED_OUT - calculated based on availableCopies)
    - createdAt, updatedAt (Audit fields)

   **Reservation Entity:**
    - reservationId (UUID, Primary Key)
    - bookId (UUID, Foreign Key to Book)
    - userId (UUID, Foreign Key to User)
    - status (Enum: RESERVED, CHECKED_OUT, RETURNED, CANCELLED)
    - reservedAt (Timestamp)
    - expiresAt (Timestamp, nullable)
    - checkedOutAt (Timestamp, nullable)
    - dueDate (Timestamp, nullable)
    - returnedAt (Timestamp, nullable)
    - renewalCount (Integer, default: 0)
    - lateDays (Integer, nullable)
    - lateFee (Decimal, nullable)
    - condition (Enum: GOOD, FAIR, POOR, DAMAGED, nullable)
    - notes (Text, nullable)
    - createdAt, updatedAt (Audit fields)

4. **Development Environment**
    - Docker Compose for local PostgreSQL with initialization scripts
    - Application properties for dev/test/prod profiles
    - Logging configuration (SLF4J + Logback)
    - Local development guide with database setup

#### Acceptance Criteria:
- [ ] Spring Boot application starts successfully on port 8080
- [ ] Database migrations execute without errors
- [ ] All base entities (User, Book, Reservation) can be persisted to database
- [ ] Docker Compose successfully runs PostgreSQL on port 5432
- [ ] Project compiles with zero warnings
- [ ] All entity relationships (User ↔ Reservation ↔ Book) are properly configured
- [ ] Enum types for role, membershipStatus, status, and condition are defined

#### Technical Specifications:
- Spring Boot version: 3.2+
- Java version: 17 or 21
- PostgreSQL version: 15+
- Maven/Gradle with standard Spring Boot starter dependencies
- JPA entity auditing enabled (@EnableJpaAuditing)