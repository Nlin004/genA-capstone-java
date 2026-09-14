package assembly.general.api.config;


import assembly.general.api.entity.Book;
import assembly.general.api.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class DevDataSeeder implements CommandLineRunner {
    private final BookRepository bookRepository;

    public DevDataSeeder(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) {
        if (bookRepository.count() > 0) {
            return;
        }

        bookRepository.save(book(
                "978-0-13-468599-1", "Clean Code", "Robert C. Martin", "Technology", 2008,
        "A handbook of agile software craftsmanship", "Prentice Hall", 464, "English", 5, 2));
        bookRepository.save(book(
                "978-0-13-475759-9", "Refactoring", "Martin Fowler", "Technology", 2018,
                "Improving the design of existing code", "Addison-Wesley", 448, "English", 3, 0));
        bookRepository.save(book(
                "978-0-321-12521-7", "Domain-Driven Design", "Eric Evans", "Technology", 2003,
                "Tackling complexity in the heart of software", "Addison-Wesley", 560, "English",
                2, 2));
        bookRepository.save(book(
                "978-0-13-235088-4", "Clean Architecture", "Robert C. Martin", "Technology", 2017,
                "A craftsman's guide to software structure", "Prentice Hall", 432, "English", 10, 10));
        bookRepository.save(book(
                "978-0-201-61622-4", "The Pragmatic Programmer", "Andrew Hunt", "Technology", 1999,
                "From journeyman to master", "Addison-Wesley", 352, "English", 2, 1));
    }


    private Book book (
        String isbn, String title, String author, String genre, int year, String description,
        String publisher, int pages, String language, int total, int available) {
    Book book = new Book();
    book.setIsbn(isbn);
    book.setTitle(title);
    book.setAuthor(author);
    book.setGenre(genre);
    book.setPublicationYear(year);
    book.setDescription(description);
    book.setPublisher(publisher);
    book.setPageCount(pages);
    book.setLanguage(language);
    book.setTotalCopies(total);
    book.setAvailableCopies(available);
    return book;


    }
}