### Milestone 2: User Service & Authentication

**Goal:** Implement complete user authentication and authorization system

#### Deliverables:

1. **User Registration & Profile**
    - User registration endpoint with email, password, firstName, lastName, phoneNumber
    - Email uniqueness validation (return 400 if email exists)
    - Password hashing with BCrypt (minimum 8 characters, uppercase, lowercase, number, special character)
    - Phone number format validation
    - Profile retrieval with activeReservations and borrowingHistory counts
    - Default role assignment (PATRON) on registration
    - Default membershipStatus (ACTIVE) on registration

2. **JWT Authentication**
    - Spring Security 6.x configuration
    - JWT token generation with 86400 seconds (24 hour) expiration
    - JWT token claims: userId, email, role
    - Custom UserDetailsService implementation
    - Token validation filter (JwtAuthenticationFilter)
    - SecurityFilterChain configuration
    - Stateless session management (no token storage on server)

3. **Role-Based Access Control**
    - Role enumeration (PATRON, LIBRARIAN)
    - Method-level security annotations (@PreAuthorize)
    - Custom access denied handler (403 responses)
    - Authorization testing utilities
    - Public endpoints: POST /api/auth/register, POST /api/auth/login, GET /api/catalog/books, GET /api/catalog/books/{bookId}
    - Protected endpoints: All others require authentication

4. **API Documentation**
    - SpringDoc OpenAPI 3 integration
    - Bearer token authentication scheme configuration
    - All 4 authentication/user endpoints documented
    - Request/response examples with actual schemas
    - Error response documentation (400, 401, 403, 500)

#### Acceptance Criteria:
- [ ] User can register with all required fields (email, password, firstName, lastName, phoneNumber)
- [ ] Registration returns 201 with userId, role=PATRON, membershipStatus=ACTIVE
- [ ] JWT token issued on successful login with tokenType "Bearer" and expiresIn 86400
- [ ] Login returns 200 with accessToken and user object
- [ ] Token expires after 24 hours (86400 seconds)
- [ ] Logout endpoint returns 200 OK with success message
- [ ] Protected endpoints return 401 without valid Authorization header
- [ ] Profile endpoint returns activeReservations and borrowingHistory counts
- [ ] Role-based access enforced (LIBRARIAN-only endpoints return 403 for PATRON)
- [ ] Swagger UI accessible at /swagger-ui.html with Bearer auth configured
- [ ] All authentication endpoints have 80%+ test coverage

#### API Endpoints Completed:

**1. POST /api/auth/register**
- Public endpoint
- Request: email, password, firstName, lastName, phoneNumber
- Response 201: userId, email, firstName, lastName, role, membershipStatus, createdAt, message
- Error 400: Email already exists

**2. POST /api/auth/login**
- Public endpoint
- Request: email, password
- Response 200: accessToken, tokenType, expiresIn, user object
- Error 401: Invalid email or password

**3. GET /api/users/profile**
- Requires authentication
- Request: Authorization Bearer token in header
- Response 200: Complete user profile with activeReservations and borrowingHistory counts
- activeReservations = count where status IN (RESERVED, CHECKED_OUT)
- borrowingHistory = total count of all reservations

#### Testing Requirements:
- Unit tests for UserService (registration, login, profile retrieval)
- Integration tests for complete authentication flow (register → login → access protected endpoint)
- Security tests for unauthorized access (401 responses)
- JWT token validation and expiration tests
- Email uniqueness validation tests
- Password strength validation tests (min 8 chars, uppercase, lowercase, number, special char)
- Profile statistics calculation tests (activeReservations, borrowingHistory)

#### Technical Specifications:
- Spring Security version: 6.x
- JWT library: io.jsonwebtoken:jjwt-api:0.11.5+
- Password encoder: `BCryptPasswordEncoder` with strength 10
- Token signing algorithm: HS256
- Token secret: Configurable via `application.properties` (min 256 bits)
- Stateless session management (`SessionCreationPolicy.STATELESS`)