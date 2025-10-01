### Milestone 4: Reservation Service - Core Functionality
**Goal:** Implement reservation lifecycle management

#### Deliverables:
1. **Reservation Creation**
  - Reserve available books
  - Validate reservation limits (max 5 active)
  - Reservation expiration (7 days for pickup)
  - Available copies decrement logic
  - Email notification integration (mock in testing)

2. **Checkout Process**
  - Librarian checkout confirmation
  - Due date calculation (14 days from checkout)
  - Status transition (RESERVED → CHECKED_OUT)
  - Checkout history tracking

3. **Renewal System**
  - Patron-initiated renewals
  - Maximum 2 renewals per book
  - Renewal validation (no waitlist)
  - Due date extension (14 days per renewal)
  - Renewal count tracking

4. **Return Processing**
  - Librarian return confirmation
  - Late fee calculation (no payment processing)
  - Status transition (CHECKED_OUT → RETURNED)
  - Available copies increment
  - Return date and condition recording

5. **Borrowing History**
  - Complete history view for patrons
  - Pagination and filtering
  - Export to CSV functionality
  - Statistics (total borrowed, on-time returns)

#### Acceptance Criteria:
- [ ] Patron can reserve up to 5 books simultaneously
- [ ] Reservation expires after 7 days if not picked up
- [ ] Book can be renewed twice if no waitlist
- [ ] Late fees calculated correctly ($1/day)
- [ ] Borrowing history shows accurate records
- [ ] Available copies update in real-time
- [ ] All reservation endpoints have 85%+ test coverage

#### API Endpoints Completed:
- POST /api/reservations
- GET /api/reservations
- POST /api/reservations/{id}/checkout
- POST /api/reservations/{id}/renew
- POST /api/reservations/{id}/return
- POST /api/reservations/{id}/cancel
- GET /api/reservations/history

#### Testing Requirements:
- Unit tests for reservation business logic
- Integration tests for complete reservation lifecycle
- Concurrent reservation tests
- Edge cases (expired reservations, overdue books)
- Email notification tests (mocked)