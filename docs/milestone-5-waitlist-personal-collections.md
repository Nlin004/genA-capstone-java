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
