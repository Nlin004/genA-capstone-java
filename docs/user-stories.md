## User Stories

### Epic 1: User Management & Authentication

**US-001: User Registration**

- **As a** library patron
- **I want to** create an account with my email and personal information
- **So that** I can access library services and reserve books
- **Acceptance Criteria**:
    - User provides email, password, full name, and phone number
    - Email must be unique and validated
    - Password must meet security requirements (min 8 chars, uppercase, number, special char)
    - Account is created with PATRON role by default
    - Confirmation email is sent upon successful registration

**US-002: User Authentication**

- **As a** registered user
- **I want to** log in securely with my credentials
- **So that** I can access my account and library services
- **Acceptance Criteria**:
    - User logs in with email and password
    - JWT token is issued upon successful authentication
    - Token expires after 24 hours
    - Failed login attempts are logged for security

**US-003: Profile Management**

- **As a** logged-in user
- **I want to** view and update my profile information
- **So that** the library has my current contact details
- **Acceptance Criteria**:
    - User can view their profile (name, email, phone, membership status)
    - User can update name and phone number
    - Email changes require re-verification
    - Password changes require current password confirmation

### Epic 2: Catalog Management

**US-004: Browse Book Catalog**

- **As a** library patron
- **I want to** browse the complete book catalog
- **So that** I can discover available books
- **Acceptance Criteria**:
    - Catalog displays books with title, author, ISBN, genre, and availability
    - Results are paginated (20 books per page)
    - Book cover images are displayed when available
    - Availability status shows: Available, Checked Out, or Reserved

**US-005: Search Books**

- **As a** library patron
- **I want to** search books by title, author, ISBN, or genre
- **So that** I can quickly find specific books
- **Acceptance Criteria**:
    - Search accepts partial matches on title and author
    - Search supports exact match on ISBN
    - Multiple filters can be applied simultaneously
    - Search results display relevance ranking
    - Empty results show helpful message

**US-006: Add Book to Catalog**

- **As a** librarian
- **I want to** add new books to the catalog
- **So that** patrons can discover and borrow them
- **Acceptance Criteria**:
    - Librarian provides ISBN, title, author, genre, publication year, description
    - System validates ISBN format (ISBN-10 or ISBN-13)
    - Multiple copies of same book can be added
    - Book cover image can be uploaded (JPG/PNG, max 2MB)
    - System tracks total copies and available copies

**US-007: Update Book Information**

- **As a** librarian
- **I want to** update book metadata and availability
- **So that** catalog information remains accurate
- **Acceptance Criteria**:
    - Librarian can edit all book fields except ISBN
    - Changes to total copies automatically adjust availability
    - Update history is logged with timestamp and user
    - Cover image can be replaced or removed

### Epic 3: Reservation & Borrowing

**US-008: Reserve Available Book**

- **As a** library patron
- **I want to** reserve an available book
- **So that** I can borrow it from the library
- **Acceptance Criteria**:
    - Patron can reserve books with "Available" status
    - Reservation is valid for 7 days for pickup
    - System sends confirmation email with pickup instructions
    - Available copies count decreases by 1
    - Patron can have maximum 5 active reservations

**US-009: Join Waitlist**

- **As a** library patron
- **I want to** join a waitlist when a book is unavailable
- **So that** I'm notified when it becomes available
- **Acceptance Criteria**:
    - Patron can join waitlist for books that are checked out or reserved
    - Waitlist position is displayed to user
    - Notification sent when book becomes available
    - Waitlist reservation expires in 3 days if not confirmed
    - System maintains FIFO order for waitlist

**US-010: Checkout Book**

- **As a** librarian
- **I want to** process book checkouts
- **So that** patrons can take books home
- **Acceptance Criteria**:
    - Librarian confirms patron reservation at pickup
    - Checkout period is 14 days from checkout date
    - Due date is calculated and stored
    - System sends email with due date reminder
    - Book status changes to "Checked Out"

**US-011: Renew Book**

- **As a** library patron
- **I want to** renew my borrowed book
- **So that** I can keep it longer without late fees
- **Acceptance Criteria**:
    - Patron can renew up to 2 times if no waitlist exists
    - Each renewal extends due date by 14 days
    - System sends confirmation with new due date
    - Renewals are not allowed if others are waiting
    - Overdue books cannot be renewed

**US-012: Return Book**

- **As a** librarian
- **I want to** process book returns
- **So that** books become available for other patrons
- **Acceptance Criteria**:
    - Librarian scans book ISBN to process return
    - System calculates late fees if overdue (no payment processing)
    - Book status changes to "Available"
    - Next waitlist patron is automatically notified
    - Return date and condition notes are recorded

**US-013: View Borrowing History**

- **As a** library patron
- **I want to** see my borrowing history
- **So that** I can track books I've read
- **Acceptance Criteria**:
    - History shows all past checkouts with dates
    - Includes book details, checkout date, return date, and status
    - Results are paginated and sorted by most recent
    - Export to CSV functionality available

### Epic 4: Personal Collections

**US-014: Manage Reading List**

- **As a** library patron
- **I want to** create and manage a reading list
- **So that** I can track books I want to read
- **Acceptance Criteria**:
    - Patron can add/remove books to reading list
    - Reading list shows book details and current availability
    - List can be reordered by drag-and-drop priority
    - No limit on reading list size
    - Quick "Reserve" button for available books

**US-015: Mark Favorites**

- **As a** library patron
- **I want to** mark books as favorites
- **So that** I can easily find books I love
- **Acceptance Criteria**:
    - One-click favorite toggle on book details page
    - Favorites page shows all favorited books
    - Favorites persist across sessions
    - Can remove from favorites list

### Epic 5: Admin Dashboard & Analytics

**US-016: View Usage Analytics**

- **As a** library administrator
- **I want to** see system usage statistics
- **So that** I can make informed collection decisions
- **Acceptance Criteria**:
    - Dashboard shows total books, active users, active reservations
    - Charts display borrowing trends over time
    - Most popular books/genres are highlighted
    - Average borrowing duration is calculated
    - Data can be filtered by date range

**US-017: Manage Users**

- **As a** library administrator
- **I want to** view and manage user accounts
- **So that** I can handle membership issues
- **Acceptance Criteria**:
    - Admin can search users by name or email
    - User details show borrowing history and active reservations
    - Admin can suspend/activate user accounts
    - Admin can change user roles (PATRON, LIBRARIAN, ADMIN)
    - Activity log shows all admin actions

**US-018: Generate Reports**

- **As a** library administrator
- **I want to** generate usage reports
- **So that** I can analyze library performance
- **Acceptance Criteria**:
    - Monthly borrowing summary report
    - Overdue books report with patron contact info
    - Collection utilization report (books never borrowed)
    - Waitlist analysis report
    - Reports exportable as PDF or CSV