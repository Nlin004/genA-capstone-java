# Data Modeling Guide

### Milestone 1: Data Modeling

**Goal:** Create entity classes and establish database schema for the Library Management System

#### Deliverables:

1. **Entity-Relationship Overview**
   - Understand the three core entities: User, Book, Reservation
   - Identify relationships between entities
   - Understand foreign key constraints

2. **Entity Classes**

   **User Entity:**
   - userId (UUID, Primary Key)
   - email (String, unique, not null, max length: 255)
   - password (String, BCrypt hashed, not null)
   - firstName (String, not null, max length: 100)
   - lastName (String, not null, max length: 100)
   - phoneNumber (String, not null, max length: 20)
   - role (Enum: PATRON, LIBRARIAN)
   - membershipStatus (Enum: ACTIVE, SUSPENDED)
   - memberSince (LocalDateTime, nullable)
   - createdAt, updatedAt (Audit fields)

   **Book Entity:**
   - bookId (UUID, Primary Key)
   - isbn (String, unique, not null, max length: 20)
   - title (String, not null, max length: 255)
   - author (String, not null, max length: 255)
   - genre (String, not null, max length: 100)
   - publicationYear (Integer, nullable)
   - description (String, TEXT type, nullable)
   - publisher (String, nullable, max length: 255)
   - pageCount (Integer, nullable)
   - language (String, nullable, max length: 50)
   - totalCopies (Integer, not null, default: 0)
   - availableCopies (Integer, not null, default: 0)
   - createdAt, updatedAt (Audit fields)
   - **Note:** status field (AVAILABLE/CHECKED_OUT) is calculated, not stored

   **Reservation Entity:**
   - reservationId (UUID, Primary Key)
   - bookId (UUID, Foreign Key to Book)
   - userId (UUID, Foreign Key to User)
   - status (Enum: RESERVED, CHECKED_OUT, RETURNED, CANCELLED)
   - reservedAt (LocalDateTime, not null)
   - expiresAt (LocalDateTime, nullable)
   - checkedOutAt (LocalDateTime, nullable)
   - dueDate (LocalDateTime, nullable)
   - returnedAt (LocalDateTime, nullable)
   - renewalCount (Integer, default: 0)
   - lateDays (Integer, nullable)
   - lateFee (BigDecimal, nullable)
   - condition (Enum: GOOD, FAIR, POOR, DAMAGED, nullable)
   - notes (String, TEXT type, nullable)
   - createdAt, updatedAt (Audit fields)

3. **Enum Classes**

   Create the following enums in `com.library.entity` or `com.library.enums`:

   **Role Enum:**
   - PATRON (Regular library user)
   - LIBRARIAN (Staff member who can checkout/return books)

   **MembershipStatus Enum:**
   - ACTIVE (User can use the system)
   - SUSPENDED (User is blocked from making reservations)

   **ReservationStatus Enum:**
   - RESERVED (Book is reserved but not picked up)
   - CHECKED_OUT (Book has been checked out)
   - RETURNED (Book has been returned)
   - CANCELLED (Reservation was cancelled)

   **BookCondition Enum:**
   - GOOD (Book is in good condition)
   - FAIR (Book shows some wear)
   - POOR (Book is damaged but usable)
   - DAMAGED (Book has significant damage)

4. **Repository Interfaces**
   - UserRepository extends JpaRepository<User, UUID>
   - BookRepository extends JpaRepository<Book, UUID>
   - ReservationRepository extends JpaRepository<Reservation, UUID>

#### Acceptance Criteria:

- [ ] Spring Boot application starts successfully on port 8080
- [ ] All three entity classes created with proper JPA annotations
- [ ] All base entities (User, Book, Reservation) can be persisted to database
- [ ] Hibernate auto-creates tables in PostgreSQL (check logs for "create table" statements)
- [ ] Docker Compose successfully runs PostgreSQL on port 5432
- [ ] Project compiles with zero warnings
- [ ] All entity relationships (User ↔ Reservation ↔ Book) are properly configured with @ManyToOne
- [ ] All four enum types are defined (Role, MembershipStatus, ReservationStatus, BookCondition)
- [ ] Repository interfaces created for all three entities
- [ ] JPA auditing is enabled and createdAt/updatedAt fields auto-populate
- [ ] Can view created tables in database client (pgAdmin, DBeaver, etc.)

#### Technical Specifications:

- Spring Boot version: 3.2+
- Java version: 17 or 21
- PostgreSQL version: 15+ (via Docker)
- Build tool: Maven (with provided pom.xml)
- ORM: Hibernate (via Spring Data JPA)
- Schema management: Hibernate DDL auto-update (`spring.jpa.hibernate.ddl-auto=update`)
- Use `@Entity`, `@Table`, `@Id`, `@GeneratedValue(strategy = GenerationType.UUID)`
- Use `@Enumerated(EnumType.STRING)` for all enum fields
- Use `@EntityListeners(AuditingEntityListener.class)` for audit fields
- Use `@CreatedDate` and `@LastModifiedDate` for timestamps