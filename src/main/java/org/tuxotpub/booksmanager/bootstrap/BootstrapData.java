package org.tuxotpub.booksmanager.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.tuxotpub.booksmanager.entities.Author;
import org.tuxotpub.booksmanager.entities.Book;
import org.tuxotpub.booksmanager.entities.Magazine;
import org.tuxotpub.booksmanager.repositories.authors.AuthorRepository;
import org.tuxotpub.booksmanager.repositories.parchments.BookRepository;
import org.tuxotpub.booksmanager.repositories.parchments.MagazineRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
//@Profile({"default", "dev"})
public class BootstrapData implements CommandLineRunner {

    public static final String TEST_NAME = "Name";
    public static final String LNAME_TEST = "LName";
    public static final String EHOST_TEST = "@test.com";
    public static final String ISBN_BOOK = "isbnBook";
    public static final String ISBN_MAGAZINE = "isbnMagazine";
    public static final String TITLE_TEST = "TITLE_TEST";
    public static final String DESCPRIPTION_TEST = "description";
    public static final LocalDate RELEASE_DATE_TEST = LocalDate.of(1980, 11, 03);

    public static List<Author> AUTHORS = new ArrayList<>();
    public static List<Book> BOOKS = new ArrayList<>();
    public static List<Magazine> MAGAZINES = new ArrayList<>();

    public static final int AUTHORS_SIZE = 5;
    public static final int PARCHMENTS_SIZE = 4;

    private AuthorRepository authorRepository;
    private BookRepository bookRepository;
    private MagazineRepository magazineRepository;

    public BootstrapData(AuthorRepository authorRepository, BookRepository bookRepository, MagazineRepository magazineRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.magazineRepository = magazineRepository;
    }

    public static Author buildAuthor(int i) {
        return new Author(TEST_NAME + i, LNAME_TEST + i, TEST_NAME + i + LNAME_TEST + i + EHOST_TEST);

    }

    public static Book buildBook(int i) {
        return new Book(null, ISBN_BOOK + i, TITLE_TEST + i, DESCPRIPTION_TEST + i);
    }

    public static Magazine buildMagazine(int i) {
        return new Magazine(null, ISBN_MAGAZINE + i, TITLE_TEST + i, RELEASE_DATE_TEST.plusDays(i));
    }

    @Override
    public void run(String... args) throws Exception {
        initDetachedEntities();
        saveAll();
    }

    /**
     * reset catecories and BOOKS, then addin and map manytomany test data
     */
    public void initDetachedEntities() {
        AUTHORS.clear();
        BOOKS.clear();
        MAGAZINES.clear();

        for (int i = 0; i < AUTHORS_SIZE; i++) {
            AUTHORS.add( buildAuthor(i) );
        }

        for (int i = 0; i < PARCHMENTS_SIZE; i++) {
            BOOKS.add( buildBook(i) );
            //Beginning from PARCHMENTS_SIZE, To avoid collision with BOOKS naturalids
            MAGAZINES.add( buildMagazine(i) );
        }

        AUTHORS.get(0).addParchment(BOOKS.get(0));
        AUTHORS.get(1).addParchment(MAGAZINES.get(0));
        AUTHORS.get(2).addParchment(BOOKS.get(1)).addParchment(MAGAZINES.get(1));
        AUTHORS.get(3).addParchment(BOOKS.get(2)).addParchment(BOOKS.get(3));
        AUTHORS.get(4).addParchment(MAGAZINES.get(2)).addParchment(MAGAZINES.get(3));
    }


    public void saveAll() {
        AUTHORS = authorRepository.saveAll(AUTHORS);
        BOOKS = bookRepository.findAllEager();
        MAGAZINES = magazineRepository.findAllEager();
    }
}
