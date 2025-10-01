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
