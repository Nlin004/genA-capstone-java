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
- Difficulty managing waitlists when popular books are unavailable
- No analytics or insights for collection development decisions

### Solution

Our Digital Library Management System provides:

- **Self-service portal** for users to browse, search, and reserve books online
- **Automated reservation management** with waitlist functionality
- **Real-time availability tracking** to reduce operational overhead
- **Analytics dashboard** for librarians to make data-driven decisions
- **Scalable architecture** supporting multiple library branches

### Target Users

1. **Library Patrons**: Browse catalog, reserve books, manage reading lists
2. **Librarians**: Manage collections, process reservations, track inventory
3. **Library Administrators**: View analytics, generate reports, system configuration

---

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

---

## API Contracts

### Authentication Endpoints

#### POST /api/auth/register

Register a new user account.

**Request Body:**

```json
{
  "email": "john.doe@example.com",
  "password": "SecurePass123!",
  "firstName": "John",
  "lastName": "Doe",
  "phoneNumber": "+1-555-0123"
}
```

**Response (201 Created):**

```json
{
  "userId": "uuid-123",
  "email": "john.doe@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "role": "PATRON",
  "membershipStatus": "ACTIVE",
  "createdAt": "2025-09-29T10:30:00Z",
  "message": "Registration successful. Please check your email for verification."
}
```

**Error Response (400 Bad Request):**

```json
{
  "error": "VALIDATION_ERROR",
  "message": "Email already exists",
  "timestamp": "2025-09-29T10:30:00Z"
}
```

---

#### POST /api/auth/login

Authenticate user and receive JWT token.

**Request Body:**

```json
{
  "email": "john.doe@example.com",
  "password": "SecurePass123!"
}
```

**Response (200 OK):**

```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "tokenType": "Bearer",
  "expiresIn": 86400,
  "user": {
    "userId": "uuid-123",
    "email": "john.doe@example.com",
    "firstName": "John",
    "lastName": "Doe",
    "role": "PATRON"
  }
}
```

**Error Response (401 Unauthorized):**

```json
{
  "error": "AUTHENTICATION_FAILED",
  "message": "Invalid email or password",
  "timestamp": "2025-09-29T10:30:00Z"
}
```

---

#### POST /api/auth/logout

Invalidate current session token.

**Headers:**

```
Authorization: Bearer {token}
```

**Response (200 OK):**

```json
{
  "message": "Logout successful"
}
```

---

### User Profile Endpoints

#### GET /api/users/profile

Retrieve current user profile.

**Headers:**

```
Authorization: Bearer {token}
```

**Response (200 OK):**

```json
{
  "userId": "uuid-123",
  "email": "john.doe@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "phoneNumber": "+1-555-0123",
  "role": "PATRON",
  "membershipStatus": "ACTIVE",
  "memberSince": "2025-01-15T00:00:00Z",
  "activeReservations": 2,
  "borrowingHistory": 45
}
```

---

#### PUT /api/users/profile

Update user profile information.

**Headers:**

```
Authorization: Bearer {token}
```

**Request Body:**

```json
{
  "firstName": "John",
  "lastName": "Doe",
  "phoneNumber": "+1-555-9999"
}
```

**Response (200 OK):**

```json
{
  "userId": "uuid-123",
  "email": "john.doe@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "phoneNumber": "+1-555-9999",
  "message": "Profile updated successfully"
}
```

---

#### PUT /api/users/change-password

Change user password.

**Headers:**

```
Authorization: Bearer {token}
```

**Request Body:**

```json
{
  "currentPassword": "SecurePass123!",
  "newPassword": "NewSecurePass456!"
}
```

**Response (200 OK):**

```json
{
  "message": "Password changed successfully"
}
```

---

### Catalog Endpoints

#### GET /api/catalog/books

Retrieve paginated list of books.

**Query Parameters:**

- `page` (default: 0)
- `size` (default: 20)
- `sortBy` (default: "title")
- `sortOrder` (default: "asc")

**Response (200 OK):**

```json
{
  "content": [
    {
      "bookId": "uuid-book-1",
      "isbn": "978-0-13-468599-1",
      "title": "Clean Code",
      "author": "Robert C. Martin",
      "genre": "Technology",
      "publicationYear": 2008,
      "description": "A handbook of agile software craftsmanship",
      "coverImageUrl": "https://storage.example.com/covers/clean-code.jpg",
      "totalCopies": 5,
      "availableCopies": 2,
      "status": "AVAILABLE"
    },
    {
      "bookId": "uuid-book-2",
      "isbn": "978-0-13-475759-9",
      "title": "Refactoring",
      "author": "Martin Fowler",
      "genre": "Technology",
      "publicationYear": 2018,
      "description": "Improving the design of existing code",
      "coverImageUrl": "https://storage.example.com/covers/refactoring.jpg",
      "totalCopies": 3,
      "availableCopies": 0,
      "status": "CHECKED_OUT"
    }
  ],
  "page": 0,
  "size": 20,
  "totalElements": 245,
  "totalPages": 13,
  "last": false
}
```

---

#### GET /api/catalog/books/search

Search books with filters.

**Query Parameters:**

- `query` (search term for title/author)
- `genre` (filter by genre)
- `isbn` (exact ISBN match)
- `availableOnly` (boolean, default: false)
- `page` (default: 0)
- `size` (default: 20)

**Example:** `/api/catalog/books/search?query=clean&genre=Technology&availableOnly=true`

**Response (200 OK):**

```json
{
  "content": [
    {
      "bookId": "uuid-book-1",
      "isbn": "978-0-13-468599-1",
      "title": "Clean Code",
      "author": "Robert C. Martin",
      "genre": "Technology",
      "publicationYear": 2008,
      "description": "A handbook of agile software craftsmanship",
      "coverImageUrl": "https://storage.example.com/covers/clean-code.jpg",
      "totalCopies": 5,
      "availableCopies": 2,
      "status": "AVAILABLE",
      "relevanceScore": 0.95
    }
  ],
  "page": 0,
  "size": 20,
  "totalElements": 1,
  "totalPages": 1,
  "last": true
}
```

---

#### GET /api/catalog/books/{bookId}

Retrieve detailed information for a specific book.

**Response (200 OK):**

```json
{
  "bookId": "uuid-book-1",
  "isbn": "978-0-13-468599-1",
  "title": "Clean Code",
  "author": "Robert C. Martin",
  "genre": "Technology",
  "publicationYear": 2008,
  "description": "A handbook of agile software craftsmanship",
  "coverImageUrl": "https://storage.example.com/covers/clean-code.jpg",
  "publisher": "Prentice Hall",
  "pageCount": 464,
  "language": "English",
  "totalCopies": 5,
  "availableCopies": 2,
  "status": "AVAILABLE",
  "waitlistCount": 0,
  "averageRating": 4.8,
  "totalReviews": 1247,
  "createdAt": "2025-01-10T09:00:00Z",
  "updatedAt": "2025-09-15T14:22:00Z"
}
```

---

#### POST /api/catalog/books

Add a new book to the catalog (Librarian only).

**Headers:**

```
Authorization: Bearer {token}
Content-Type: multipart/form-data
```

**Form Data:**

```
isbn: 978-0-13-468599-1
title: Clean Code
author: Robert C. Martin
genre: Technology
publicationYear: 2008
description: A handbook of agile software craftsmanship
publisher: Prentice Hall
pageCount: 464
language: English
totalCopies: 5
coverImage: [binary file data]
```

**Response (201 Created):**

```json
{
  "bookId": "uuid-book-1",
  "isbn": "978-0-13-468599-1",
  "title": "Clean Code",
  "author": "Robert C. Martin",
  "genre": "Technology",
  "totalCopies": 5,
  "availableCopies": 5,
  "coverImageUrl": "https://storage.example.com/covers/uuid-book-1.jpg",
  "message": "Book added successfully"
}
```

---

#### PUT /api/catalog/books/{bookId}

Update book information (Librarian only).

**Headers:**

```
Authorization: Bearer {token}
```

**Request Body:**

```json
{
  "title": "Clean Code: A Handbook of Agile Software Craftsmanship",
  "description": "Updated description with more details...",
  "totalCopies": 7
}
```

**Response (200 OK):**

```json
{
  "bookId": "uuid-book-1",
  "isbn": "978-0-13-468599-1",
  "title": "Clean Code: A Handbook of Agile Software Craftsmanship",
  "totalCopies": 7,
  "availableCopies": 4,
  "message": "Book updated successfully"
}
```

---

#### DELETE /api/catalog/books/{bookId}

Remove a book from the catalog (Admin only).

**Headers:**

```
Authorization: Bearer {token}
```

**Response (200 OK):**

```json
{
  "message": "Book deleted successfully",
  "bookId": "uuid-book-1"
}
```

**Error Response (409 Conflict):**

```json
{
  "error": "CANNOT_DELETE",
  "message": "Cannot delete book with active reservations",
  "activeReservations": 3
}
```

---

### Reservation Endpoints

#### POST /api/reservations

Create a new reservation.

**Headers:**

```
Authorization: Bearer {token}
```

**Request Body:**

```json
{
  "bookId": "uuid-book-1"
}
```

**Response (201 Created):**

```json
{
  "reservationId": "uuid-res-1",
  "bookId": "uuid-book-1",
  "userId": "uuid-123",
  "bookTitle": "Clean Code",
  "status": "RESERVED",
  "reservedAt": "2025-09-29T10:30:00Z",
  "expiresAt": "2025-10-06T10:30:00Z",
  "message": "Book reserved successfully. Please pick up within 7 days."
}
```

**Error Response (400 Bad Request):**

```json
{
  "error": "RESERVATION_LIMIT_EXCEEDED",
  "message": "You have reached the maximum of 5 active reservations",
  "currentReservations": 5
}
```

---

#### GET /api/reservations

Get current user's active reservations.

**Headers:**

```
Authorization: Bearer {token}
```

**Response (200 OK):**

```json
{
  "reservations": [
    {
      "reservationId": "uuid-res-1",
      "bookId": "uuid-book-1",
      "bookTitle": "Clean Code",
      "bookAuthor": "Robert C. Martin",
      "coverImageUrl": "https://storage.example.com/covers/clean-code.jpg",
      "status": "RESERVED",
      "reservedAt": "2025-09-29T10:30:00Z",
      "expiresAt": "2025-10-06T10:30:00Z",
      "daysUntilExpiry": 7
    },
    {
      "reservationId": "uuid-res-2",
      "bookId": "uuid-book-2",
      "bookTitle": "Refactoring",
      "bookAuthor": "Martin Fowler",
      "coverImageUrl": "https://storage.example.com/covers/refactoring.jpg",
      "status": "CHECKED_OUT",
      "checkedOutAt": "2025-09-20T14:00:00Z",
      "dueDate": "2025-10-04T14:00:00Z",
      "daysUntilDue": 5,
      "renewalsRemaining": 2
    }
  ],
  "totalActive": 2
}
```

---

#### POST /api/reservations/{reservationId}/checkout

Process book checkout at library (Librarian only).

**Headers:**

```
Authorization: Bearer {token}
```

**Request Body:**

```json
{
  "notes": "Book condition: Good"
}
```

**Response (200 OK):**

```json
{
  "reservationId": "uuid-res-1",
  "status": "CHECKED_OUT",
  "checkedOutAt": "2025-09-29T15:00:00Z",
  "dueDate": "2025-10-13T15:00:00Z",
  "message": "Book checked out successfully. Due date: October 13, 2025"
}
```

---

#### POST /api/reservations/{reservationId}/renew

Renew a checked-out book.

**Headers:**

```
Authorization: Bearer {token}
```

**Response (200 OK):**

```json
{
  "reservationId": "uuid-res-1",
  "newDueDate": "2025-10-27T15:00:00Z",
  "renewalsRemaining": 1,
  "message": "Book renewed successfully. New due date: October 27, 2025"
}
```

**Error Response (400 Bad Request):**

```json
{
  "error": "RENEWAL_NOT_ALLOWED",
  "message": "Cannot renew book with active waitlist",
  "waitlistCount": 3
}
```

---

#### POST /api/reservations/{reservationId}/return

Process book return (Librarian only).

**Headers:**

```
Authorization: Bearer {token}
```

**Request Body:**

```json
{
  "condition": "GOOD",
  "notes": "Returned in good condition"
}
```

**Response (200 OK):**

```json
{
  "reservationId": "uuid-res-1",
  "returnedAt": "2025-10-10T10:00:00Z",
  "lateDays": 0,
  "lateFee": 0.00,
  "message": "Book returned successfully"
}
```

**Response with Late Fee (200 OK):**

```json
{
  "reservationId": "uuid-res-1",
  "returnedAt": "2025-10-15T10:00:00Z",
  "dueDate": "2025-10-13T15:00:00Z",
  "lateDays": 2,
  "lateFee": 2.00,
  "message": "Book returned. Late fee of $2.00 applied to account."
}
```

---

#### POST /api/reservations/{reservationId}/cancel

Cancel an active reservation.

**Headers:**

```
Authorization: Bearer {token}
```

**Response (200 OK):**

```json
{
  "reservationId": "uuid-res-1",
  "status": "CANCELLED",
  "message": "Reservation cancelled successfully"
}
```

---

#### GET /api/reservations/history

Get borrowing history for current user.

**Headers:**

```
Authorization: Bearer {token}
```

**Query Parameters:**

- `page` (default: 0)
- `size` (default: 20)

**Response (200 OK):**

```json
{
  "content": [
    {
      "reservationId": "uuid-res-100",
      "bookTitle": "Clean Code",
      "bookAuthor": "Robert C. Martin",
      "coverImageUrl": "https://storage.example.com/covers/clean-code.jpg",
      "reservedAt": "2025-08-15T10:00:00Z",
      "checkedOutAt": "2025-08-16T14:00:00Z",
      "returnedAt": "2025-08-30T09:00:00Z",
      "dueDate": "2025-08-30T14:00:00Z",
      "status": "RETURNED",
      "renewalCount": 0,
      "wasLate": false
    }
  ],
  "page": 0,
  "size": 20,
  "totalElements": 45,
  "totalPages": 3,
  "last": false
}
```

---

### Waitlist Endpoints

#### POST /api/waitlist

Join waitlist for an unavailable book.

**Headers:**

```
Authorization: Bearer {token}
```

**Request Body:**

```json
{
  "bookId": "uuid-book-2"
}
```

**Response (201 Created):**

```json
{
  "waitlistId": "uuid-wait-1",
  "bookId": "uuid-book-2",
  "bookTitle": "Refactoring",
  "position": 3,
  "estimatedWaitDays": 42,
  "joinedAt": "2025-09-29T10:30:00Z",
  "message": "Added to waitlist. You are #3 in queue."
}
```

---

#### GET /api/waitlist

Get current user's waitlist entries.

**Headers:**

```
Authorization: Bearer {token}
```

**Response (200 OK):**

```json
{
  "waitlistEntries": [
    {
      "waitlistId": "uuid-wait-1",
      "bookId": "uuid-book-2",
      "bookTitle": "Refactoring",
      "bookAuthor": "Martin Fowler",
      "coverImageUrl": "https://storage.example.com/covers/refactoring.jpg",
      "position": 3,
      "estimatedWaitDays": 42,
      "joinedAt": "2025-09-29T10:30:00Z"
    }
  ],
  "totalWaiting": 1
}
```

---

#### DELETE /api/waitlist/{waitlistId}

Remove self from waitlist.

**Headers:**

```
Authorization: Bearer {token}
```

**Response (200 OK):**

```json
{
  "message": "Removed from waitlist successfully"
}
```

---

### Reading List Endpoints

#### POST /api/reading-list

Add book to reading list.

**Headers:**

```
Authorization: Bearer {token}
```

**Request Body:**

```json
{
  "bookId": "uuid-book-1",
  "priority": 1
}
```

**Response (201 Created):**

```json
{
  "readingListId": "uuid-rl-1",
  "bookId": "uuid-book-1",
  "bookTitle": "Clean Code",
  "priority": 1,
  "addedAt": "2025-09-29T10:30:00Z",
  "message": "Book added to reading list"
}
```

---

#### GET /api/reading-list

Get current user's reading list.

**Headers:**

```
Authorization: Bearer {token}
```

**Response (200 OK):**

```json
{
  "readingList": [
    {
      "readingListId": "uuid-rl-1",
      "bookId": "uuid-book-1",
      "bookTitle": "Clean Code",
      "bookAuthor": "Robert C. Martin",
      "coverImageUrl": "https://storage.example.com/covers/clean-code.jpg",
      "genre": "Technology",
      "priority": 1,
      "availabilityStatus": "AVAILABLE",
      "addedAt": "2025-09-29T10:30:00Z"
    },
    {
      "readingListId": "uuid-rl-2",
      "bookId": "uuid-book-2",
      "bookTitle": "Refactoring",
      "bookAuthor": "Martin Fowler",
      "coverImageUrl": "https://storage.example.com/covers/refactoring.jpg",
      "genre": "Technology",
      "priority": 2,
      "availabilityStatus": "CHECKED_OUT",
      "addedAt": "2025-09-28T14:00:00Z"
    }
  ],
  "totalBooks": 2
}
```

---

#### PUT /api/reading-list/{readingListId}/priority

Update priority of book in reading list.

**Headers:**

```
Authorization: Bearer {token}
```

**Request Body:**

```json
{
  "priority": 5
}
```

**Response (200 OK):**

```json
{
  "readingListId": "uuid-rl-1",
  "priority": 5,
  "message": "Priority updated successfully"
}
```

---

#### DELETE /api/reading-list/{readingListId}

Remove book from reading list.

**Headers:**

```
Authorization: Bearer {token}
```

**Response (200 OK):**

```json
{
  "message": "Book removed from reading list"
}
```

---

### Favorites Endpoints

#### POST /api/favorites

Add book to favorites.

**Headers:**

```
Authorization: Bearer {token}
```

**Request Body:**

```json
{
  "bookId": "uuid-book-1"
}
```

**Response (201 Created):**

```json
{
  "favoriteId": "uuid-fav-1",
  "bookId": "uuid-book-1",
  "bookTitle": "Clean Code",
  "addedAt": "2025-09-29T10:30:00Z",
  "message": "Book added to favorites"
}
```

---

#### GET /api/favorites

Get current user's favorite books.

**Headers:**

```
Authorization: Bearer {token}
```

**Response (200 OK):**

```json
{
  "favorites": [
    {
      "favoriteId": "uuid-fav-1",
      "bookId": "uuid-book-1",
      "bookTitle": "Clean Code",
      "bookAuthor": "Robert C. Martin",
      "coverImageUrl": "https://storage.example.com/covers/clean-code.jpg",
      "genre": "Technology",
      "availabilityStatus": "AVAILABLE",
      "addedAt": "2025-09-29T10:30:00Z"
    }
  ],
  "totalFavorites": 1
}
```

---

#### DELETE /api/favorites/{favoriteId}

Remove book from favorites.

**Headers:**

```
Authorization: Bearer {token}
```

**Response (200 OK):**

```json
{
  "message": "Book removed from favorites"
}
```

---

### Admin Dashboard Endpoints

#### GET /api/admin/dashboard/stats

Get system-wide statistics (Admin only).

**Headers:**

```
Authorization: Bearer {token}
```

**Response (200 OK):**

```json
{
  "overview": {
    "totalBooks": 1247,
    "totalCopies": 3891,
    "availableCopies": 1523,
    "totalUsers": 5432,
    "activeUsers": 3201,
    "activeReservations": 2368,
    "overdueReservations": 127
  },
  "monthlyStats": {
    "newRegistrations": 156,
    "totalCheckouts": 892,
    "totalReturns": 834,
    "averageBorrowingDays": 11.3
  },
  "popularGenres": [
    {
      "genre": "Fiction",
      "totalCheckouts": 2341,
      "percentageOfTotal": 28.5
    },
    {
      "genre": "Technology",
      "totalCheckouts": 1876,
      "percentageOfTotal": 22.8
    },
    {
      "genre": "Biography",
      "totalCheckouts": 1234,
      "percentageOfTotal": 15.0
    }
  ],
  "topBooks": [
    {
      "bookId": "uuid-book-5",
      "title": "The Pragmatic Programmer",
      "author": "David Thomas",
      "totalCheckouts": 234,
      "currentWaitlist": 12
    }
  ]
}
```

---

#### GET /api/admin/users

Get all users with filtering (Admin only).

**Headers:**

```
Authorization: Bearer {token}
```

**Query Parameters:**

- `search` (search by name or email)
- `role` (filter by role: PATRON, LIBRARIAN, ADMIN)
- `status` (filter by status: ACTIVE, SUSPENDED)
- `page` (default: 0)
- `size` (default: 50)

**Response (200 OK):**

```json
{
  "content": [
    {
      "userId": "uuid-123",
      "email": "john.doe@example.com",
      "firstName": "John",
      "lastName": "Doe",
      "role": "PATRON",
      "membershipStatus": "ACTIVE",
      "memberSince": "2025-01-15T00:00:00Z",
      "activeReservations": 2,
      "overdueBooks": 0,
      "totalBorrowed": 45,
      "lastActive": "2025-09-28T14:22:00Z"
    }
  ],
  "page": 0,
  "size": 50,
  "totalElements": 5432,
  "totalPages": 109,
  "last": false
}
```

---

#### PUT /api/admin/users/{userId}/role

Update user role (Admin only).

**Headers:**

```
Authorization: Bearer {token}
```

**Request Body:**

```json
{
  "role": "LIBRARIAN"
}
```

**Response (200 OK):**

```json
{
  "userId": "uuid-123",
  "email": "john.doe@example.com",
  "role": "LIBRARIAN",
  "message": "User role updated successfully"
}
```

---

#### PUT /api/admin/users/{userId}/status

Suspend or activate user account (Admin only).

**Headers:**

```
Authorization: Bearer {token}
```

**Request Body:**

```json
{
  "status": "SUSPENDED",
  "reason": "Repeated late returns"
}
```

**Response (200 OK):**

```json
{
  "userId": "uuid-123",
  "membershipStatus": "SUSPENDED",
  "message": "User account suspended successfully"
}
```

---

#### GET /api/admin/reports/overdue

Generate overdue books report (Admin only).

**Headers:**

```
Authorization: Bearer {token}
```

**Query Parameters:**

- `startDate` (optional)
- `endDate` (optional)

**Response (200 OK):**

```json
{
  "reportGenerated": "2025-09-29T10:30:00Z",
  "totalOverdue": 127,
  "totalLateFees": 487.50,
  "overdueReservations": [
    {
      "reservationId": "uuid-res-999",
      "userId": "uuid-123",
      "userName": "John Doe",
      "userEmail": "john.doe@example.com",
      "userPhone": "+1-555-0123",
      "bookTitle": "Clean Code",
      "isbn": "978-0-13-468599-1",
      "dueDate": "2025-09-15T14:00:00Z",
      "daysOverdue": 14,
      "lateFee": 14.00,
      "lastContactDate": "2025-09-20T10:00:00Z"
    }
  ]
}
```

---

#### GET /api/admin/reports/collection-utilization

Generate collection utilization report (Admin only).

**Headers:**

```
Authorization: Bearer {token}
```

**Response (200 OK):**

```json
{
  "reportGenerated": "2025-09-29T10:30:00Z",
  "totalBooks": 1247,
  "neverBorrowed": 89,
  "lowUtilization": 234,
  "recommendations": [
    {
      "bookId": "uuid-book-999",
      "title": "Obscure Programming Language Guide",
      "author": "Unknown Author",
      "totalCopies": 5,
      "timesCheckedOut": 0,
      "monthsSincePurchase": 24,
      "recommendation": "REMOVE",
      "reason": "Never borrowed in 2 years"
    }
  ]
}
```

---

#### GET /api/admin/reports/monthly-summary

Generate monthly activity summary (Admin only).

**Headers:**

```
Authorization: Bearer {token}
```

**Query Parameters:**

- `month` (format: YYYY-MM)

**Response (200 OK):**

```json
{
  "month": "2025-09",
  "reportGenerated": "2025-09-29T10:30:00Z",
  "summary": {
    "newRegistrations": 156,
    "totalCheckouts": 892,
    "totalReturns": 834,
    "totalRenewals": 234,
    "newBooksAdded": 47,
    "averageBorrowingDays": 11.3,
    "totalLateFees": 1250.00,
    "uniqueActiveUsers": 1892
  },
  "dailyActivity": [
    {
      "date": "2025-09-01",
      "checkouts": 32,
      "returns": 28,
      "renewals": 8
    }
  ],
  "topPerformers": {
    "mostBorrowedBooks": [
      {
        "title": "The Pragmatic Programmer",
        "checkouts": 42
      }
    ],
    "mostActiveUsers": [
      {
        "userName": "Jane Smith",
        "totalCheckouts": 12
      }
    ]
  }
}
```

---

#### POST /api/admin/reports/export

Export report as CSV or PDF (Admin only).

**Headers:**

```
Authorization: Bearer {token}
```

**Request Body:**

```json
{
  "reportType": "OVERDUE",
  "format": "CSV",
  "dateRange": {
    "startDate": "2025-09-01",
    "endDate": "2025-09-30"
  }
}
```

**Response (200 OK):**

```json
{
  "exportId": "uuid-export-1",
  "downloadUrl": "https://storage.example.com/reports/overdue-2025-09.csv",
  "expiresAt": "2025-09-30T10:30:00Z",
  "message": "Report generated successfully"
}
```

---


markdown
## Project Milestones

### Milestone 1: Project Setup & Core Infrastructure
**Goal:** Establish development environment and foundational architecture

#### Deliverables:
1. **Project Initialization**
  - Spring Boot 3.x project with Maven/Gradle
  - Project structure following clean architecture principles
  - Git repository with proper .gitignore
  - README with setup instructions

2. **Database Configuration**
  - PostgreSQL 15+ setup (local and Docker)
  - Spring Data JPA configuration
  - Flyway/Liquibase migration setup
  - Initial schema design document

3. **Base Entity Models**
  - User entity with role enumeration
  - Book entity with catalog metadata
  - Reservation entity with status tracking
  - Audit fields (createdAt, updatedAt, createdBy)

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

---

### Milestone 3: Catalog Service
**Goal:** Build comprehensive book catalog management system

#### Deliverables:
1. **Book CRUD Operations**
  - Create book with metadata validation
  - Update book information
  - Soft delete implementation
  - ISBN format validation (ISBN-10/ISBN-13)
  - Duplicate ISBN prevention

2. **Search & Filtering**
  - Full-text search on title and author
  - Filter by genre, publication year, availability
  - Pagination with customizable page size
  - Sorting by title, author, publication year
  - Search relevance scoring

3. **Image Upload**
  - Multipart file upload for book covers
  - File type validation (JPG, PNG)
  - File size limits (max 2MB)
  - Image storage service (S3 or local filesystem)
  - Image URL generation

4. **Inventory Management**
  - Total copies tracking
  - Available copies calculation
  - Status management (AVAILABLE, CHECKED_OUT, RESERVED)
  - Copy count validation rules

#### Acceptance Criteria:
- [ ] Librarian can add books with complete metadata
- [ ] Book cover images upload and display correctly
- [ ] Search returns relevant results within 1 second
- [ ] Pagination works for large datasets (1000+ books)
- [ ] Available copies automatically update on checkout/return
- [ ] ISBN validation prevents invalid formats
- [ ] All catalog endpoints have 80%+ test coverage

#### API Endpoints Completed:
- POST /api/catalog/books
- GET /api/catalog/books
- GET /api/catalog/books/{bookId}
- GET /api/catalog/books/search
- PUT /api/catalog/books/{bookId}
- DELETE /api/catalog/books/{bookId}

#### Testing Requirements:
- Unit tests for BookService and search logic
- Integration tests for CRUD operations
- File upload tests with mock multipart files
- Search performance tests with sample dataset
- Validation tests for ISBN and metadata

---

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

---

### Milestone 5: Waitlist & Personal Collections
**Goal:** Add advanced user features for book discovery and tracking

#### Deliverables:
1. **Waitlist Management**
  - Join waitlist for unavailable books
  - FIFO queue implementation
  - Position tracking and display
  - Automatic notification when available
  - Waitlist expiration (3 days to confirm)
  - Remove from waitlist functionality

2. **Reading List**
  - Add/remove books to reading list
  - Priority/ordering system
  - Display with current availability
  - Quick reserve from reading list
  - Unlimited list size

3. **Favorites System**
  - One-click favorite toggle
  - Favorites collection view
  - Display with availability status
  - Quick access to favorite books

4. **Notification System** (Basic)
  - Email service integration (mock for testing)
  - Waitlist availability notifications
  - Reservation reminders
  - Due date reminders
  - Notification preferences (future enhancement)

#### Acceptance Criteria:
- [ ] Waitlist maintains FIFO order correctly
- [ ] Next patron automatically notified on book return
- [ ] Reading list supports custom ordering
- [ ] Favorites persist across sessions
- [ ] Notifications sent at appropriate times (mocked)
- [ ] All feature endpoints have 80%+ test coverage

#### API Endpoints Completed:
- POST /api/waitlist
- GET /api/waitlist
- DELETE /api/waitlist/{id}
- POST /api/reading-list
- GET /api/reading-list
- PUT /api/reading-list/{id}/priority
- DELETE /api/reading-list/{id}
- POST /api/favorites
- GET /api/favorites
- DELETE /api/favorites/{id}

#### Testing Requirements:
- Waitlist ordering and notification tests
- Concurrent waitlist join tests
- Reading list priority update tests
- Favorites add/remove tests
- Integration tests for notification triggers

---

### Milestone 6: Admin Dashboard & Analytics
**Goal:** Provide administrative oversight and business intelligence

#### Deliverables:
1. **System Statistics Dashboard**
  - Total books, copies, users overview
  - Active reservations and overdue count
  - Monthly checkout/return metrics
  - Genre popularity analysis
  - Top borrowed books ranking

2. **User Management**
  - Search and filter users
  - View user details and activity
  - Update user roles
  - Suspend/activate accounts
  - Activity audit log

3. **Reporting System**
  - Overdue books report with contact info
  - Collection utilization report
  - Monthly activity summary
  - Waitlist analysis report
  - Export to CSV/PDF

4. **Data Analytics**
  - Borrowing trends over time
  - Peak usage hours/days
  - Genre performance metrics
  - User engagement statistics
  - Collection gaps identification

#### Acceptance Criteria:
- [ ] Dashboard loads within 2 seconds
- [ ] All statistics calculated accurately
- [ ] Reports generate within 5 seconds
- [ ] CSV exports include all requested data
- [ ] User suspension immediately revokes access
- [ ] Role changes enforce new permissions immediately
- [ ] All admin endpoints have 75%+ test coverage

#### API Endpoints Completed:
- GET /api/admin/dashboard/stats
- GET /api/admin/users
- PUT /api/admin/users/{id}/role
- PUT /api/admin/users/{id}/status
- GET /api/admin/reports/overdue
- GET /api/admin/reports/collection-utilization
- GET /api/admin/reports/monthly-summary
- POST /api/admin/reports/export

#### Testing Requirements:
- Statistics calculation tests
- Report generation tests
- User management integration tests
- Authorization tests for admin-only endpoints
- Performance tests for dashboard queries

---

### Milestone 7: Testing & Quality Assurance
**Goal:** Achieve comprehensive test coverage and code quality

#### Deliverables:
1. **TestContainers Integration**
  - PostgreSQL test container setup
  - Integration test base classes
  - Test data builders and fixtures
  - Database cleanup between tests

2. **Comprehensive Test Suite**
  - Unit tests (target: 85% coverage)
  - Integration tests for all API endpoints
  - Security tests for authentication/authorization
  - Performance tests for search and reports
  - Edge case and error handling tests

3. **API Contract Testing**
  - OpenAPI specification validation
  - Request/response schema validation
  - HTTP status code verification
  - Error response format consistency

4. **Code Quality**
  - SonarQube or similar tool integration
  - Code smell resolution
  - Security vulnerability scanning
  - Dependency version updates
  - Code documentation (JavaDoc)

5. **Load Testing**
  - JMeter or Gatling test scenarios
  - Concurrent user simulation
  - Database connection pool tuning
  - Performance baseline documentation

#### Acceptance Criteria:
- [ ] Overall test coverage >80%
- [ ] All integration tests pass with TestContainers
- [ ] Zero critical security vulnerabilities
- [ ] API response times <500ms (p95)
- [ ] Load test supports 100 concurrent users
- [ ] SonarQube quality gate passes
- [ ] All public methods have JavaDoc

#### Testing Deliverables:
- Test coverage report
- Performance test results
- Security scan report
- Code quality metrics
- Test execution documentation

---

### Milestone 8: Deployment & Production Readiness

**Goal:** Deploy to AWS with production-grade configuration

#### Deliverables:

1. **Docker Containerization**
  - Multi-stage Dockerfile for Spring Boot app
  - Docker Compose for local full-stack setup
2. **AWS Infrastructure**
  - RDS PostgreSQL instance setup
  - AWS Elastic Beanstalk for application hosting
  - VPC and subnet configuration
3. **Documentation**
  - API documentation (Swagger/OpenAPI)
  - Architecture diagrams

#### Acceptance Criteria:
- [ ] Application runs successfully on AWS
- [ ] RDS database accessible and secured
- [ ] S3 image uploads work in production
- [ ] Health check endpoint returns 200
- [ ] CI/CD pipeline successfully deploys on commit
- [ ] Logs accessible in CloudWatch
- [ ] API documentation accessible via /swagger-ui
- [ ] Zero critical AWS security findings

#### Production Checklist:
- [ ] HTTPS enabled with valid SSL certificate
- [ ] Database backups configured (daily)
- [ ] Secrets not hardcoded in configuration
- [ ] CORS configured for frontend integration
- [ ] Rate limiting implemented
- [ ] Error tracking (Sentry or similar)
- [ ] Performance monitoring (New Relic or similar)
- [ ] Disaster recovery plan documented

## Technical Stack Summary

### Core Technologies

- **Java**: 17 or 21 (LTS)
- **Spring Boot**: 3.2+
- **Spring Security**: 6.2+
- **Spring Data JPA**: 3.2+
- **PostgreSQL**: 15+

### Additional Libraries

- **JWT**: io.jsonwebtoken:jjwt
- **Validation**: spring-boot-starter-validation
- **OpenAPI**: springdoc-openapi-starter-webmvc-ui
- **Testing**: JUnit 5, Mockito, TestContainers

---

## Success Criteria

### Functional Requirements

- ✅ All 18 user stories fully implemented
- ✅ 50+ API endpoints documented and functional
- ✅ Complete reservation lifecycle working end-to-end
- ✅ Role-based access control enforced
- ✅ Admin dashboard with real-time analytics

### Technical Requirements

- ✅ >80% test coverage across all services
- ✅ All API responses <500ms (p95)
- ✅ Zero critical security vulnerabilities
- ✅ OpenAPI documentation complete
- ✅ Deployed to AWS with RDS integration

### Quality Standards

- ✅ Clean code principles followed
- ✅ SOLID principles applied
- ✅ Comprehensive error handling
- ✅ Proper logging throughout
- ✅ Production-ready configuration

---

## Getting Started

### Prerequisites

```bash
- Java 17+
- Maven 3.8+ or Gradle 8+
- Docker & Docker Compose
- PostgreSQL 15+ (local or Docker)
- AWS Account (for deployment phase)
```

### Local Development Setup

```bash
# Clone repository
git clone <repository-url>
cd java-capstone

# Start application
./mvnw spring-boot:run

# Access Swagger UI
open http://localhost:8080/swagger-ui.html
```

### Running Tests

```bash
# Run all tests
./mvnw test

# Run integration tests
./mvnw verify

# Generate coverage report
./mvnw jacoco:report
```