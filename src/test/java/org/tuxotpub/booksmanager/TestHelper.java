package org.tuxotpub.booksmanager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.tuxotpub.booksmanager.api.v1.dtos.AuthorDTO;
import org.tuxotpub.booksmanager.api.v1.dtos.BookDTO;
import org.tuxotpub.booksmanager.api.v1.dtos.MagazineDTO;
import org.tuxotpub.booksmanager.controllers.v1.AuthorController;
import org.tuxotpub.booksmanager.controllers.v1.BookController;
import org.tuxotpub.booksmanager.controllers.v1.MagazineController;
import org.tuxotpub.booksmanager.entities.Author;
import org.tuxotpub.booksmanager.entities.Book;
import org.tuxotpub.booksmanager.entities.Magazine;

import java.time.LocalDate;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by tuxsamo.
 */

public abstract class TestHelper {

    public static final LocalDate RELEASE_DATE_TEST = LocalDate.of(1980, 11, 03);

    public static final Author AUTHOR1 = new Author(1L, "fname1", "lname1", "test1@test.com", new HashSet<>());
    public static final Author AUTHOR2 = new Author(2L, "fname2", "lname2", "test2@test.com", new HashSet<>());

    public static final Book BOOK1 = new Book(1L, "123456789011", "title1", "desc1");
    public static final Book BOOK2 = new Book(2L, "123456789012", "title2", "desc2");

    public static final Magazine MAGAZINE1 = new Magazine(3L, "123456789013", "title3", RELEASE_DATE_TEST.plusDays(1L) );
    public static final Magazine MAGAZINE2 = new Magazine(4L, "123456789014", "title4", RELEASE_DATE_TEST.plusDays(2L));

    public static final AuthorDTO AUTHORDTO1 = new AuthorDTO(1L, "fname1", "lname1", "test1@test.com", new HashSet<>(), AuthorController.FINDBYID_PATH + 1L);
    public static final AuthorDTO AUTHORDTO2 = new AuthorDTO(2L, "fname2", "lname2", "test2@test.com", new HashSet<>(), AuthorController.FINDBYID_PATH + 2L);

    public static final BookDTO BOOKDTO1 = new BookDTO(1L, "123456789011", "title1", "desc1", new HashSet<>(), BookController.FINDBYID_PATH + "1");
    public static final BookDTO BOOKDTO2 = new BookDTO(2L, "123456789012", "title2", "desc2", new HashSet<>(), BookController.FINDBYID_PATH + "2");

    public static final MagazineDTO MAGAZINEDTO1 = new MagazineDTO(3L, "123456789013", "title3", RELEASE_DATE_TEST.plusDays(1L), new HashSet<>(), MagazineController.FINDBYID_PATH + "3");
    public static final MagazineDTO MAGAZINEDTO2 = new MagazineDTO(4L, "123456789014", "title4", RELEASE_DATE_TEST.plusDays(2L), new HashSet<>(), MagazineController.FINDBYID_PATH + "4");

    public static String toJsonString(Object obj){
        try {
            return new ObjectMapper()
                    .registerModule( new JavaTimeModule() )
                    .disable(SerializationFeature.CLOSE_CLOSEABLE.WRITE_DATES_AS_TIMESTAMPS)
                    .writeValueAsString( obj );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
