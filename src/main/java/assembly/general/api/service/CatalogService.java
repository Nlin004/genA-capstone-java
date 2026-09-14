package assembly.general.api.service;


import assembly.general.api.dto.BookDetailResponse;
import assembly.general.api.dto.BookSummaryResponse;
import assembly.general.api.dto.PageResponse;
import assembly.general.api.entity.Book;
import assembly.general.api.exception.ApiException;
import assembly.general.api.repository.BookRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class CatalogService {

    private static final Set<String> SORTABLE = Set.of("title", "author", "publicationYear");

    private final BookRepository bookRepository;

    public CatalogService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional(readOnly = true)
    public PageResponse<BookSummaryResponse> list(
            int page, int size, String sortBy, String sortOrder,
            String query, String genre, String isbn, boolean availableOnly) {
        String field = SORTABLE.contains(sortBy) ? sortBy : "title";
        Sort.Direction direction = "desc".equalsIgnoreCase(sortOrder)
                ? Sort.Direction.DESC : Sort.Direction.ASC;
        Page<Book> result = bookRepository.findAll(
                bookSpec(query, genre, isbn, availableOnly),
                PageRequest.of(page, size, Sort.by(direction, field)));
        List<BookSummaryResponse> content = result.getContent().stream()
                .map(this::toSummary)
                .toList();

        return  new PageResponse<>(
                content,
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages(),
                result.isLast()
        );
    }

    @Transactional(readOnly = true)
    public BookDetailResponse get(UUID bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(()-> ApiException.notFound("Book not found with ID: " + bookId));
        return new BookDetailResponse(
                book.getId(),
                book.getIsbn(),
                book.getTitle(),
                book.getAuthor(),
                book.getGenre(),
                book.getPublicationYear(),
                book.getDescription(),
                book.getPublisher(),
                book.getPageCount(),
                book.getLanguage(),
                book.getTotalCopies(),
                book.getAvailableCopies(),
                book.availabilityStatus(),
                book.getCreatedAt(),
                book.getUpdatedAt()
        );
    }

    private Specification<Book> bookSpec(String query, String genre, String isbn, boolean availableOnly) {
        return (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (query != null && !query.isBlank()) {
                String like = "%" + query.toLowerCase() + "%";
                predicates.add(cb.or(
                        cb.like(cb.lower(root.get("title")), like),
                        cb.like(cb.lower(root.get("author")), like)
                ));
            }
            if (genre != null && !genre.isBlank()) {
                predicates.add(cb.equal(root.get("genre"), genre));
            }
            if (isbn != null && !isbn.isBlank()) {
                predicates.add(cb.equal(root.get("isbn"), isbn));
            }
            if (!availableOnly) {
                predicates.add(cb.greaterThan(root.get("availableCopies"), 0));
            }
            return cb.and(predicates.toArray(Predicate[]::new));
        };
    }

    private BookSummaryResponse toSummary(Book book) {
        return new BookSummaryResponse(
                book.getId(),
                book.getIsbn(),
                book.getTitle(),
                book.getAuthor(),
                book.getGenre(),
                book.getPublicationYear(),
                book.getDescription(),
                book.getTotalCopies(),
                book.getAvailableCopies(),
                book.availabilityStatus()
        )   ;
    }
}
