### Milestone 5: Testing & Quality Assurance
**Goal:** Achieve comprehensive test coverage using REST Assured and MockMVC for all 11 endpoints

#### Deliverables:

1. **REST Assured Setup**
   - REST Assured configuration with baseURI and basePath
   - Base test class with common setup (RestAssuredBaseTest)
   - RequestSpecification with default headers and content-type
   - ResponseSpecification for common assertions
   - Authentication helper methods (login and token extraction)
   - Test data builders for request bodies
   - JSON schema validation files for all 11 endpoints

2. **MockMVC Configuration**
   - @WebMvcTest configuration for controller layer tests
   - MockMVC test context setup with security configuration
   - Base controller test class (ControllerTestBase)
   - @MockBean for service layer dependencies
   - Mock security context with authenticated users (PATRON and LIBRARIAN roles)
   - Request builders using MockMvcRequestBuilders
   - Result matchers using MockMvcResultMatchers
   - ObjectMapper configuration for JSON serialization

3. **Comprehensive Test Suite**

   **Unit Tests (MockMVC) - Target: 85% coverage**
   - **AuthController Tests**
      - POST /api/auth/register: Valid registration, duplicate email (400), validation errors
      - POST /api/auth/login: Valid login, invalid credentials (401)
      - POST /api/auth/logout: Authenticated logout (200), unauthenticated (401)

   - **UserController Tests**
      - GET /api/users/profile: Authenticated access (200), unauthenticated (401)
      - Verify activeReservations and borrowingHistory calculations

   - **CatalogController Tests**
      - GET /api/catalog/books: Default pagination, custom sorting, filtering (query, genre, isbn, availableOnly)
      - GET /api/catalog/books/{bookId}: Valid bookId (200), invalid bookId (404)
      - Public access (no authentication required)

   - **ReservationController Tests**
      - POST /api/reservations: Valid reservation (201), limit exceeded (400), book unavailable (400)
      - GET /api/reservations: Authenticated access, empty list
      - POST /api/reservations/{id}/checkout: LIBRARIAN success (200), PATRON forbidden (403), invalid status (400)
      - POST /api/reservations/{id}/return: LIBRARIAN success (200), late return with fees, PATRON forbidden (403)
      - GET /api/reservations/history: Paginated history with wasLate flag

   - **Exception Handling Tests**
      - @RestControllerAdvice exception handler tests
      - Validation error responses (400)
      - Authentication failures (401)
      - Authorization failures (403)
      - Not found errors (404)
      - Internal server errors (500)

   **Integration Tests (REST Assured) - End-to-End**
   - **Authentication Flow Tests**
      - Complete flow: register → login → access protected endpoint
      - Token expiration validation (24 hours)
      - Invalid token rejection (401)

   - **Authorization Tests**
      - PATRON can access: profile, catalog, reservations (own)
      - PATRON cannot access: checkout (403), return (403)
      - LIBRARIAN can access: all PATRON endpoints + checkout + return
      - Public endpoints accessible without authentication

   - **Catalog Integration Tests**
      - Browse books with various pagination parameters
      - Search with query parameter
      - Combined filtering (query + genre + availableOnly)
      - Sort by title, author, publicationYear (asc/desc)
      - Retrieve specific book details

   - **Reservation Lifecycle Tests**
      - Create reservation → verify availableCopies decremented
      - Checkout as LIBRARIAN → verify dueDate calculation
      - Return on time → verify availableCopies incremented, no late fee
      - Return late → verify lateDays and lateFee calculation
      - View borrowing history → verify wasLate flag

   - **Multi-Step Workflow Tests**
      - User registers → logs in → searches books → reserves book → views active reservations
      - Librarian checks out book → patron views updated reservation → librarian returns book
      - Reservation limit enforcement (create 5 reservations, 6th fails with 400)

   - **Error Scenario Validation**
      - Reserve book with 0 available copies (400)
      - Checkout non-RESERVED reservation (400)
      - Return non-CHECKED_OUT reservation (400)
      - Access protected endpoint without token (401)
      - PATRON attempts checkout (403)
      - Invalid bookId in catalog (404)

   **API Contract Testing**
   - Request schema validation for all POST endpoints (register, login, reservations, checkout, return)
   - Response schema validation for all 200/201 responses
   - HTTP status code verification (200, 201, 400, 401, 403, 404, 500)
   - Authorization header validation (Bearer token format)
   - Content-Type header verification (application/json)
   - Error response format consistency across all endpoints (error, message, timestamp)
   - Pagination structure validation (content, page, size, totalElements, totalPages, last)

4. **Test Coverage & Quality**
   - Unit test coverage target: 85%
   - Integration test coverage for all 11 endpoints
   - Edge cases: empty results, boundary values (page=0, size=1000)
   - Boundary conditions: exactly 5 reservations, 0 available copies
   - Negative scenarios: invalid UUIDs, malformed JSON, missing required fields
   - Concurrent request tests: multiple users reserving last available copy

#### Acceptance Criteria:
- [ ] Overall test coverage >80% (measured by JaCoCo)
- [ ] All 11 endpoints have REST Assured integration tests
- [ ] All 11 endpoints have MockMVC unit tests
- [ ] API contract validation implemented with JSON schemas
- [ ] All authentication flows tested (register, login, logout, token validation)
- [ ] All authorization rules tested (PATRON vs LIBRARIAN access)
- [ ] Pagination tested across catalog and history endpoints
- [ ] Reservation lifecycle fully tested (create → checkout → return)
- [ ] Late fee calculation verified with multiple test cases
- [ ] Error responses follow consistent format (error, message, timestamp)
- [ ] No failing tests in CI/CD pipeline

#### Test Organization:

**Package Structure:**
```
src/test/java/
├── com.library.integration/          (REST Assured tests)
│   ├── AuthIntegrationTest
│   ├── CatalogIntegrationTest
│   ├── ReservationIntegrationTest
│   └── base/
│       └── RestAssuredBaseTest
├── com.library.controller/           (MockMVC tests)
│   ├── AuthControllerTest
│   ├── UserControllerTest
│   ├── CatalogControllerTest
│   ├── ReservationControllerTest
│   └── base/
│       └── ControllerTestBase
└── com.library.service/              (Service layer unit tests)
    ├── UserServiceTest
    ├── BookServiceTest
    └── ReservationServiceTest
```

#### Testing Deliverables:
- JaCoCo test coverage report (HTML + XML)
- REST Assured test execution results
- MockMVC test results
- SonarQube code quality metrics report
- Test documentation with examples for each endpoint category
- CI/CD integration configuration (GitHub Actions or Jenkins)

#### Technical Specifications:
- REST Assured version: 5.3+
- Spring Boot Test version: 3.x
- JUnit 5 (Jupiter)
- Mockito for mocking
- Hamcrest matchers for assertions
- JSON Schema Validator for contract testing
- JaCoCo for code coverage
- H2 or Testcontainers for test database

#### Technical Debt & Risks:
- Concurrent reservation tests may be flaky without proper transaction isolation
- Test data cleanup between tests required to avoid state leakage
- Performance tests not included (consider separate milestone if needed)
- Security penetration testing not included
- Load testing not included