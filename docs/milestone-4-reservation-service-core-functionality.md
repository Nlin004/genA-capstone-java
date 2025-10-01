### Milestone 4: Reservation Service - Core Functionality
**Goal:** Implement reservation lifecycle management

#### Deliverables:

1. **Reservation Creation**
    - Reserve available books by bookId
    - Validate user has fewer than 5 active reservations (status = RESERVED or CHECKED_OUT)
    - Verify book has availableCopies > 0
    - Set reservedAt to current timestamp
    - Set expiresAt to 7 days from reservedAt
    - Decrement book's availableCopies by 1
    - Set status to RESERVED
    - Return reservation details with bookTitle
    - Error handling for limit exceeded (400) and book unavailable (400)

2. **Active Reservations View**
    - Retrieve all active reservations for authenticated user
    - Filter by status IN (RESERVED, CHECKED_OUT)
    - Calculate daysUntilExpiry for RESERVED status
    - Calculate daysUntilDue for CHECKED_OUT status
    - Include book details: bookTitle, bookAuthor
    - Return totalActive count

3. **Checkout Process**
    - Librarian-only endpoint (requires LIBRARIAN role)
    - Validate reservation status is RESERVED
    - Set status to CHECKED_OUT
    - Set checkedOutAt to current timestamp
    - Calculate dueDate (checkedOutAt + 14 days)
    - Store optional notes
    - Return formatted due date message
    - Error handling for invalid status (400) and forbidden access (403)

4. **Return Processing**
    - Librarian-only endpoint (requires LIBRARIAN role)
    - Validate reservation status is CHECKED_OUT
    - Set status to RETURNED
    - Set returnedAt to current timestamp
    - Store condition (GOOD, FAIR, POOR, DAMAGED)
    - Store optional notes
    - Calculate lateDays if returnedAt > dueDate
    - Calculate lateFee ($1.00 per day late)
    - Increment book's availableCopies by 1
    - Return response with late fee details if applicable
    - Error handling for invalid status (400) and forbidden access (403)

5. **Borrowing History**
    - Retrieve complete borrowing history for authenticated user
    - Include all statuses (RESERVED, CHECKED_OUT, RETURNED, CANCELLED)
    - Paginated results: page (default: 0), size (default: 20)
    - Sort by most recent first (returnedAt or reservedAt descending)
    - Calculate wasLate flag (returnedAt > dueDate)
    - Include book details: bookTitle, bookAuthor
    - Return pagination metadata: page, size, totalElements, totalPages, last

#### Acceptance Criteria:
- [ ] Patron can reserve books when they have fewer than 5 active reservations
- [ ] Reservation limit validation returns 400 error when limit reached
- [ ] Book unavailable returns 400 error when availableCopies = 0
- [ ] expiresAt set to 7 days from reservedAt timestamp
- [ ] Active reservations show daysUntilExpiry for RESERVED status
- [ ] Active reservations show daysUntilDue for CHECKED_OUT status
- [ ] Checkout sets dueDate to 14 days from checkedOutAt
- [ ] Only LIBRARIAN role can access checkout endpoint (403 for PATRON)
- [ ] Only LIBRARIAN role can access return endpoint (403 for PATRON)
- [ ] Late fees calculated correctly at $1.00 per day
- [ ] availableCopies decrements on reservation creation
- [ ] availableCopies increments on book return
- [ ] Borrowing history shows all past reservations with pagination
- [ ] wasLate flag correctly calculated in history
- [ ] All reservation endpoints have 85%+ test coverage

#### API Endpoints Completed:

**1. POST /api/reservations**
- Requires authentication (PATRON or LIBRARIAN)
- Request: bookId
- Response 201: reservationId, bookId, userId, bookTitle, status, reservedAt, expiresAt, message
- Error 400: RESERVATION_LIMIT_EXCEEDED or BOOK_UNAVAILABLE

**2. GET /api/reservations**
- Requires authentication (PATRON or LIBRARIAN)
- Response 200: Array of active reservations with totalActive count
- Includes daysUntilExpiry (for RESERVED) or daysUntilDue (for CHECKED_OUT)

**3. POST /api/reservations/{reservationId}/checkout**
- Requires LIBRARIAN role
- Path parameter: reservationId (UUID)
- Request: notes (optional)
- Response 200: reservationId, status, checkedOutAt, dueDate, message
- Error 403: FORBIDDEN (non-librarian)
- Error 400: INVALID_STATUS (not RESERVED)

**4. POST /api/reservations/{reservationId}/return**
- Requires LIBRARIAN role
- Path parameter: reservationId (UUID)
- Request: condition (GOOD, FAIR, POOR, DAMAGED), notes (optional)
- Response 200: reservationId, returnedAt, lateDays, lateFee, message (includes dueDate if late)
- Error 403: FORBIDDEN (non-librarian)
- Error 400: INVALID_STATUS (not CHECKED_OUT)

**5. GET /api/reservations/history**
- Requires authentication (PATRON or LIBRARIAN)
- Query parameters: page (default: 0), size (default: 20)
- Response 200: Paginated history with wasLate flag
- Includes: reservationId, bookTitle, bookAuthor, reservedAt, checkedOutAt, returnedAt, dueDate, status

#### Testing Requirements:
- Unit tests for ReservationService (business logic)
- Integration tests for complete reservation lifecycle (reserve → checkout → return)
- Concurrent reservation tests (race conditions for last available copy)
- Edge cases:
    - Reservation limit enforcement (exactly 5 active)
    - Book with 0 available copies
    - Expired reservations (past expiresAt date)
    - Overdue returns (returnedAt > dueDate)
- Late fee calculation tests (various days overdue)
- Status transition validation tests (can't checkout CHECKED_OUT, can't return RESERVED)
- Role-based access tests (PATRON cannot checkout/return)
- availableCopies update tests (decrement on reserve, increment on return)
- Pagination tests for borrowing history

#### Technical Specifications:
- Use @Transactional for atomic operations (reserve/checkout/return)
- Date calculations using Java 8 Time API (LocalDateTime, ChronoUnit)
- Late fee calculation: ChronoUnit.DAYS.between(dueDate, returnedAt) × 1.00
- Status enum: RESERVED, CHECKED_OUT, RETURNED, CANCELLED
- Condition enum: GOOD, FAIR, POOR, DAMAGED
- Query optimization with JOIN FETCH for book details