package assembly.general.api.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "books")
@EntityListeners(AuditingEntityListener.class)
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String isbn;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String genre;

    @Column(nullable = false)
    private Integer publicationYear;

    @Column(length = 4000)
    private String description;

    private String publisher;
    private String language;
    private Integer pageCount;

    @Column(nullable = false)
    private Integer totalCopies;

    @Column(nullable = false)
    private Integer availableCopies;

    @Version
    private Long version;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedAt;

    public String availabilityStatus() {
        return availableCopies != null && availableCopies > 0 ? "AVAILABLE" : "CHECKED_OUT";
    }

//    public UUID getId() { return id; }
//    public void setId(UUID id) { this.id = id; }
//    public String getIsbn() { return isbn; }
//    public void setIsbn(String isbn) { this.isbn = isbn; }
//    public String getTitle() { return title; }
//    public void setTitle(String title) { this.title = title; }
//    public String getAuthor() { return author; }
//    public void setAuthor(String author) { this.author = author; }
//    public String getGenre() { return genre; }
//    public void setGenre(String genre) { this.genre = genre; }
//    public Integer getPublicationYear() { return publicationYear; }
//    public void setPublicationYear(Integer publicationYear) { this.publicationYear = publicationYear; }
//    public String getDescription() { return description; }
//    public void setDescription(String description) { this.description = description; }
//    public String getPublisher() { return publisher; }
//    public void setPublisher(String publisher) { this.publisher = publisher; }
//    public String getLanguage() { return language; }
//    public void setLanguage(String language) { this.language = language; }
//    public Integer getPageCount() { return pageCount; }
//    public void setPageCount(Integer pageCount) { this.pageCount = pageCount; }
//    public Integer getTotalCopies() { return totalCopies; }
//    public void setTotalCopies(Integer totalCopies) { this.totalCopies = totalCopies; }
//    public Integer getAvailableCopies() { return availableCopies; }
//    public void setAvailableCopies(Integer availableCopies) { this.availableCopies = availableCopies; }
//    public Long getVersion() { return version; }
//    public void setVersion(Long version) { this.version = version; }
//    public Instant getCreatedAt() { return createdAt; }
//    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
//    public Instant getUpdatedAt() { return updatedAt; }
//    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }



}
