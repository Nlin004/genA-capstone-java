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