### Milestone 2: User Service & Authentication
**Goal:** Implement complete user authentication and authorization system

#### Deliverables:
1. **User Registration & Profile**
  - User registration endpoint with validation
  - Email uniqueness validation
  - Password hashing with BCrypt
  - Profile management endpoints
  - Password change functionality

2. **JWT Authentication**
  - Spring Security 6.x configuration
  - JWT token generation and validation
  - Custom UserDetailsService implementation
  - Token expiration and refresh logic
  - SecurityFilterChain configuration

3. **Role-Based Access Control**
  - Role enumeration (PATRON, LIBRARIAN, ADMIN)
  - Method-level security annotations
  - Custom access denied handler
  - Authorization testing utilities

4. **API Documentation**
  - SpringDoc OpenAPI 3 integration
  - Authentication endpoints documented
  - Security scheme configuration
  - Request/response examples

#### Acceptance Criteria:
- [ ] User can register with email and password
- [ ] JWT token issued on successful login
- [ ] Token expires after 24 hours
- [ ] Protected endpoints require valid token
- [ ] Role-based access enforced on all endpoints
- [ ] Swagger UI accessible at /swagger-ui.html
- [ ] All authentication endpoints have 80%+ test coverage

#### API Endpoints Completed:
- POST /api/auth/register
- POST /api/auth/login
- POST /api/auth/logout
- GET /api/users/profile
- PUT /api/users/profile
- PUT /api/users/change-password

#### Testing Requirements:
- Unit tests for UserService
- Integration tests for authentication flow
- Security tests for unauthorized access
- JWT token validation tests