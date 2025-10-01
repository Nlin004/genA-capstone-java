Here are the updated milestones with CSV/PDF export functionality removed:

### Milestone 1: Project Setup & Core Infrastructure
**Goal:** Establish development environment and foundational architecture

#### Deliverables:
1. **Project Initialization**
    - Spring Boot 3.x project with Maven
    - Project structure following clean architecture principles
    - Git repository with proper .gitignore
    - README with setup instructions

2. **Database Configuration**
    - PostgreSQL 15+ setup (local and Docker)
    - Spring Data JPA configuration
    - Flyway migration setup
    - Initial schema design document

3. **Base Entity Models**
    - **User Entity**
        - userId (UUID), email, password, firstName, lastName, phoneNumber
        - role (PATRON, LIBRARIAN, ADMIN)
        - membershipStatus (ACTIVE, SUSPENDED)
        - memberSince, createdAt, updatedAt

    - **Book Entity**
        - bookId (UUID), isbn, title, author, genre
        - publicationYear, description, publisher, pageCount, language
        - coverImageUrl, totalCopies, availableCopies
        - status (AVAILABLE, CHECKED_OUT, RESERVED)
        - waitlistCount, averageRating, totalReviews
        - createdAt, updatedAt

    - **Reservation Entity**
        - reservationId (UUID), bookId, userId
        - status (RESERVED, CHECKED_OUT, RETURNED, CANCELLED)
        - reservedAt, expiresAt, checkedOutAt, dueDate, returnedAt
        - renewalCount, lateDays, lateFee
        - condition, notes

    - **Waitlist Entity**
        - waitlistId (UUID), bookId, userId
        - position, estimatedWaitDays, joinedAt, notifiedAt

    - **ReadingList Entity**
        - readingListId (UUID), bookId, userId
        - priority, addedAt

    - **Favorite Entity**
        - favoriteId (UUID), bookId, userId, addedAt

4. **Development Environment**
    - Docker Compose for local PostgreSQL
    - Application properties for dev/test/prod profiles
    - Logging configuration (SLF4J + Logback)
    - Local development guide

#### Acceptance Criteria:
- [ ] Spring Boot application starts successfully
- [ ] Database migrations execute without errors
- [ ] All base entities can be persisted to database
- [ ] Docker Compose successfully runs PostgreSQL
- [ ] Project compiles with zero warnings

#### Technical Debt & Risks:
- Schema design may need iteration based on requirements
- Docker environment may need adjustment for different OS

---

### Milestone 2: User Service & Authentication
**Goal:** Implement complete user authentication and authorization system

#### Deliverables:
1. **User Registration & Profile**
    - User registration with email, password, firstName, lastName, phoneNumber
    - Email uniqueness validation
    - Password hashing with BCrypt
    - Profile retrieval with activeReservations and borrowingHistory counts
    - Profile update (firstName, lastName, phoneNumber)
    - Password change with current password verification

2. **JWT Authentication**
    - Spring Security 6.x configuration
    - JWT token generation with 86400s (24 hour) expiration
    - Custom UserDetailsService implementation
    - Token validation filter
    - SecurityFilterChain configuration
    - Logout with token invalidation

3. **Role-Based Access Control**
    - Role enumeration (PATRON, LIBRARIAN, ADMIN)
    - Method-level security annotations (@PreAuthorize)
    - Custom access denied handler
    - Authorization testing utilities

4. **API Documentation**
    - SpringDoc OpenAPI 3 integration
    - Bearer token authentication scheme
    - Request/response examples for all endpoints
    - Error response documentation

#### Acceptance Criteria:
- [ ] User can register with all required fields
- [ ] JWT token issued on successful login with tokenType "Bearer"
- [ ] Token expires after 24 hours (86400 seconds)
- [ ] Protected endpoints require valid Authorization header
- [ ] Role-based access enforced on all endpoints
- [ ] Swagger UI accessible at /swagger-ui.html
- [ ] All authentication endpoints have 80%+ test coverage

#### API Endpoints Completed:
- **POST /api/auth/register** - 201 Created with user profile
- **POST /api/auth/login** - 200 OK with accessToken and user object
- **POST /api/auth/logout** - 200 OK with success message
- **GET /api/users/profile** - 200 OK with complete user profile
- **PUT /api/users/profile** - 200 OK with updated profile
- **PUT /api/users/change-password** - 200 OK with success message

#### Testing Requirements:
- Unit tests for UserService
- Integration tests for complete auth flow
- Security tests for unauthorized access (401)
- JWT token validation tests
- Email uniqueness validation tests
- Password strength validation tests

---

### Milestone 3: Catalog Service
**Goal:** Build comprehensive book catalog management system

#### Deliverables:
1. **Book CRUD Operations**
    - Create book with complete metadata (isbn, title, author, genre, publicationYear, description, publisher, pageCount, language, totalCopies)
    - Update book information (title, description, totalCopies)
    - Soft delete with active reservation validation
    - ISBN format validation (ISBN-10/ISBN-13)
    - Duplicate ISBN prevention
    - Cover image multipart file upload

2. **Search & Filtering**
    - Paginated book listing with sortBy and sortOrder
    - Full-text search by query (title/author)
    - Filter by genre, isbn, availableOnly
    - Relevance scoring for search results
    - Default page size: 20, default sort: title asc

3. **Image Upload**
    - Multipart file upload for book covers
    - File type validation (JPG, PNG)
    - File size limits (max 2MB)
    - Image storage service (S3 or local filesystem)
    - coverImageUrl generation

4. **Inventory Management**
    - totalCopies and availableCopies tracking
    - Status management (AVAILABLE, CHECKED_OUT, RESERVED)
    - waitlistCount tracking
    - Copy count validation rules
    - averageRating and totalReviews fields

#### Acceptance Criteria:
- [ ] Librarian can add books with multipart/form-data
- [ ] Book cover images upload and return coverImageUrl
- [ ] Search returns results with relevanceScore
- [ ] Pagination includes totalElements, totalPages, last flag
- [ ] Available copies automatically update on checkout/return
- [ ] ISBN validation prevents invalid formats
- [ ] Delete blocked if activeReservations > 0 (409 Conflict)
- [ ] All catalog endpoints have 80%+ test coverage

#### API Endpoints Completed:
- **POST /api/catalog/books** - 201 Created (Librarian only, multipart/form-data)
- **GET /api/catalog/books** - 200 OK with pagination (page, size, sortBy, sortOrder)
- **GET /api/catalog/books/{bookId}** - 200 OK with detailed book info
- **GET /api/catalog/books/search** - 200 OK with filtered results (query, genre, isbn, availableOnly)
- **PUT /api/catalog/books/{bookId}** - 200 OK (Librarian only)
- **DELETE /api/catalog/books/{bookId}** - 200 OK or 409 Conflict (Admin only)

#### Testing Requirements:
- Unit tests for BookService and search logic
- Integration tests for CRUD operations
- File upload tests with mock multipart files
- Search performance tests with sample dataset
- Validation tests for ISBN and metadata
- Authorization tests for role-specific endpoints

---

### Milestone 4: Reservation Service - Core Functionality
**Goal:** Implement reservation lifecycle management

#### Deliverables:
1. **Reservation Creation**
    - Reserve available books with bookId
    - Validate reservation limit (max 5 active)
    - Set expiresAt to 7 days from reservedAt
    - Decrement availableCopies
    - Return reservation with status RESERVED
    - Error handling for limit exceeded (400)

2. **Checkout Process**
    - Librarian checkout confirmation with optional notes
    - Calculate dueDate (14 days from checkedOutAt)
    - Status transition (RESERVED → CHECKED_OUT)
    - Update checkedOutAt timestamp
    - Return renewalsRemaining (default: 2)

3. **Renewal System**
    - Patron-initiated renewals
    - Maximum 2 renewals per book
    - Validation: no active waitlist
    - Extend dueDate by 14 days
    - Decrement renewalsRemaining
    - Error handling for waitlist conflict (400)

4. **Return Processing**
    - Librarian return confirmation with condition and notes
    - Calculate lateDays and lateFee ($1/day)
    - Status transition (CHECKED_OUT → RETURNED)
    - Set returnedAt timestamp
    - Increment availableCopies
    - Display late fee message if applicable

5. **Reservation Management**
    - Get active reservations with daysUntilExpiry/daysUntilDue
    - Cancel reservation (status → CANCELLED)
    - Borrowing history with pagination
    - Include renewalCount and wasLate flag in history

#### Acceptance Criteria:
- [ ] Patron can reserve up to 5 books simultaneously
- [ ] expiresAt set to 7 days from reservation
- [ ] dueDate set to 14 days from checkout
- [ ] Book can be renewed twice if waitlistCount = 0
- [ ] Late fees calculated correctly ($1/day)
- [ ] Borrowing history shows complete record with pagination
- [ ] Available copies update in real-time
- [ ] All reservation endpoints have 85%+ test coverage

#### API Endpoints Completed:
- **POST /api/reservations** - 201 Created with reservation details
- **GET /api/reservations** - 200 OK with active reservations array
- **POST /api/reservations/{reservationId}/checkout** - 200 OK (Librarian only)
- **POST /api/reservations/{reservationId}/renew** - 200 OK with newDueDate
- **POST /api/reservations/{reservationId}/return** - 200 OK with lateDays/lateFee (Librarian only)
- **POST /api/reservations/{reservationId}/cancel** - 200 OK
- **GET /api/reservations/history** - 200 OK with pagination

#### Testing Requirements:
- Unit tests for reservation business logic
- Integration tests for complete lifecycle
- Concurrent reservation tests (race conditions)
- Edge cases (expired reservations, overdue books)
- Late fee calculation tests
- Renewal validation tests (waitlist blocking)

---

### Milestone 5: Waitlist & Personal Collections
**Goal:** Add advanced user features for book discovery and tracking

#### Deliverables:
1. **Waitlist Management**
    - Join waitlist with bookId
    - FIFO queue with position tracking
    - Calculate estimatedWaitDays based on position
    - Return position and joinedAt
    - Remove from waitlist by waitlistId
    - Get all waitlist entries for current user

2. **Reading List**
    - Add book with bookId and priority
    - Get reading list sorted by priority
    - Update priority by readingListId
    - Display availabilityStatus (AVAILABLE, CHECKED_OUT)
    - Remove from reading list by readingListId
    - Show genre, coverImageUrl, author in list view

3. **Favorites System**
    - Add book to favorites with bookId
    - Get favorites with availabilityStatus
    - Remove from favorites by favoriteId
    - Display genre, coverImageUrl, author in favorites view
    - Show addedAt timestamp

#### Acceptance Criteria:
- [ ] Waitlist maintains FIFO order with accurate position
- [ ] estimatedWaitDays calculated based on average borrowing period
- [ ] Reading list supports custom priority ordering
- [ ] Favorites persist across sessions
- [ ] All collections show current availabilityStatus
- [ ] All feature endpoints have 80%+ test coverage

#### API Endpoints Completed:
- **POST /api/waitlist** - 201 Created with position and estimatedWaitDays
- **GET /api/waitlist** - 200 OK with waitlistEntries array
- **DELETE /api/waitlist/{waitlistId}** - 200 OK
- **POST /api/reading-list** - 201 Created with priority
- **GET /api/reading-list** - 200 OK with sorted readingList array
- **PUT /api/reading-list/{readingListId}/priority** - 200 OK
- **DELETE /api/reading-list/{readingListId}** - 200 OK
- **POST /api/favorites** - 201 Created
- **GET /api/favorites** - 200 OK with favorites array
- **DELETE /api/favorites/{favoriteId}** - 200 OK

#### Testing Requirements:
- Waitlist ordering and position calculation tests
- Concurrent waitlist join tests
- Reading list priority update tests
- Favorites add/remove tests
- Integration tests for all collections
- availabilityStatus accuracy tests

---

### Milestone 6: Admin Dashboard & Analytics
**Goal:** Provide administrative oversight and business intelligence

#### Deliverables:
1. **System Statistics Dashboard**
    - Overview: totalBooks, totalCopies, availableCopies, totalUsers, activeUsers, activeReservations, overdueReservations
    - Monthly stats: newRegistrations, totalCheckouts, totalReturns, averageBorrowingDays
    - Popular genres with totalCheckouts and percentageOfTotal
    - Top books with totalCheckouts and currentWaitlist

2. **User Management**
    - Search users by name/email
    - Filter by role (PATRON, LIBRARIAN, ADMIN)
    - Filter by status (ACTIVE, SUSPENDED)
    - Paginated results (default size: 50)
    - View activeReservations, overdueBooks, totalBorrowed, lastActive
    - Update user role with new role value
    - Update membershipStatus with status and reason

3. **Reporting System**
    - **Overdue Report**: reservationId, user details (name, email, phone), book details, dueDate, daysOverdue, lateFee, lastContactDate
    - **Collection Utilization**: neverBorrowed count, lowUtilization count, recommendations with REMOVE suggestions
    - **Monthly Summary**: daily activity array, top performers (books and users), summary metrics

4. **Report Generation**
    - Generate reports with startDate/endDate filters
    - totalOverdue and totalLateFees in overdue report
    - monthsSincePurchase in utilization report
    - uniqueActiveUsers in monthly summary

#### Acceptance Criteria:
- [ ] Dashboard loads within 2 seconds
- [ ] All statistics calculated accurately
- [ ] Reports generate within 5 seconds
- [ ] User suspension immediately updates membershipStatus
- [ ] Role changes enforce new permissions immediately
- [ ] All admin endpoints have 75%+ test coverage
- [ ] Admin endpoints return 403 for non-admin users

#### API Endpoints Completed:
- **GET /api/admin/dashboard/stats** - 200 OK with overview, monthlyStats, popularGenres, topBooks (Admin only)
- **GET /api/admin/users** - 200 OK with pagination and filters (Admin only)
- **PUT /api/admin/users/{userId}/role** - 200 OK with new role (Admin only)
- **PUT /api/admin/users/{userId}/status** - 200 OK with new status (Admin only)
- **GET /api/admin/reports/overdue** - 200 OK with overdueReservations array (Admin only)
- **GET /api/admin/reports/collection-utilization** - 200 OK with recommendations (Admin only)
- **GET /api/admin/reports/monthly-summary** - 200 OK with daily activity (Admin only)

#### Testing Requirements:
- Statistics calculation accuracy tests
- Report generation tests with sample data
- User management integration tests
- Authorization tests (Admin only endpoints)
- Performance tests for dashboard queries

---

### Milestone 7: Testing & Quality Assurance
**Goal:** Achieve comprehensive test coverage using REST Assured and MockMVC

#### Deliverables:

1. **REST Assured Setup**
    - REST Assured configuration and base test classes
    - Request/response specifications with baseURI and basePath
    - Authentication filters for Bearer token injection
    - JSON schema validation setup for all endpoints

2. **MockMVC Configuration**
    - MockMVC test context with @WebMvcTest
    - Controller test base classes with @MockBean services
    - Mock security context configuration with authenticated users
    - Request builders for all HTTP methods
    - Result matchers for status and JSON path

3. **Comprehensive Test Suite**
    - **Unit Tests (MockMVC)**
        - Controller layer tests with mocked services
        - Request validation tests (required fields, formats)
        - Response mapping tests (DTO conversions)
        - Exception handling tests (@RestControllerAdvice)

    - **Integration Tests (REST Assured)**
        - End-to-end API endpoint tests for all 37 endpoints
        - Authentication flows (register, login, logout)
        - Authorization tests (role-based access)
        - Multi-step workflows (reserve → checkout → return)
        - Error scenario validation (400, 401, 403, 404, 409)

    - **API Contract Testing**
        - Request body schema validation (all POST/PUT endpoints)
        - Response schema validation (all 200/201 responses)
        - HTTP status code verification (success and error cases)
        - Header validation (Authorization, Content-Type)
        - Content-type verification (application/json, multipart/form-data)
        - Error response format consistency (error, message, timestamp)
        - Pagination structure validation (content, page, size, totalElements, totalPages, last)

4. **Test Coverage**
    - Unit tests (target: 85% coverage)
    - Edge case tests (reservation limits, overdue calculations)
    - Boundary condition tests (max renewals, waitlist position)
    - Negative test scenarios (invalid tokens, missing fields)
    - Concurrent request handling tests (race conditions)

#### Acceptance Criteria:
- [ ] Overall test coverage >80%
- [ ] All REST Assured integration tests pass
- [ ] All MockMVC unit tests pass
- [ ] API contract validation implemented for all 37 endpoints
- [ ] JSON schema validation for all request/response payloads
- [ ] Error responses follow consistent format across all endpoints

#### Testing Deliverables:
- Test coverage report (JaCoCo)
- REST Assured test execution results
- MockMVC test results
- Code quality metrics (SonarQube)
- Test documentation with examples for each endpoint category

---

### Milestone 8: Deployment & Production Readiness
**Goal:** Deploy to AWS with production-grade configuration

#### Deliverables:

1. **Docker Containerization**
    - Multi-stage Dockerfile for Spring Boot app
    - Docker Compose for local full-stack setup (app + PostgreSQL)
    - Environment variable configuration
    - Health check configuration

2. **AWS Infrastructure**
    - RDS PostgreSQL instance setup with encryption
    - AWS Elastic Beanstalk for application hosting
    - VPC and subnet configuration with security groups
    - CloudWatch logs integration

3. **Documentation**
    - OpenAPI 3.0 specification (accessible at /swagger-ui)
    - Architecture diagrams (entity relationships, API flow)
    - Deployment guide
    - API usage examples for all endpoint categories

#### Acceptance Criteria:
- [ ] Application runs successfully on AWS Elastic Beanstalk
- [ ] RDS database accessible and secured with VPC
- [ ] Health check endpoint returns 200
- [ ] Logs accessible in CloudWatch
- [ ] Swagger UI accessible at /swagger-ui with Bearer auth
- [ ] All 37 endpoints documented in OpenAPI spec