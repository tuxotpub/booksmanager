package org.tuxotpub.booksmanager;

import org.tuxotpub.booksmanager.entities.Author;
import org.tuxotpub.booksmanager.entities.BaseEntity;
import org.tuxotpub.booksmanager.entities.Publication;

import java.time.LocalDateTime;

/**
 * Created by tuxsamo.
 */

public final class TestHelper {

    private TestHelper(){}

    public static final int CREATE = 1, UPDATE = 2, AFTER_UPDATE = 3, EXIST = 4, DELETE = 5, NOT_EXIST =6;

    public static  <T extends BaseEntity<Long>> T SETUP_ENTITY(final T entity, final Long salt){
        entity.setEntityId(salt);
        entity.setCreatedOn(LocalDateTime.now());
        entity.setUpdatedOn(entity.getCreatedOn());
        entity.setCreatedBy("USER" + salt);
        entity.setUpdatedBy("USER" + salt);
        return entity;
    }

    public static Author GET_NEW_AUTHOR(final Long salt){
        Author author = new Author();
        author.setName("Name" + salt);
        author.setSurname("LName" + salt);
        author.setEmail("LName" + salt + "@test.com");
        return author;
    }

    public static Publication GET_NEW_PUBLICATION(final Long salt){
        Publication publication = new Publication();
        publication.setIsbn("ISBN-B" + salt);
        publication.setDescription("Desc" + salt);
        return publication;
    }

    /*
    public static final LocalDate RELEASE_DATE_TEST = LocalDate.of(1980, 11, 03);

    public static final Author AUTHOR1 = new Author(1L, "fname1", "lname1", "test1@test.com", new HashSet<>());
    public static final Author AUTHOR2 = new Author(2L, "fname2", "lname2", "test2@test.com", new HashSet<>());

    public static final Book BOOK1 = new Book();
        BOOK1.setIsbn("123456789011");
        BOOK1.setTitle("title");
        BOOK1.setDescription("desc1");

    public static final Book BOOK2 = new Book(2L, "123456789012", "title2", "desc2");

    public static final Magazine MAGAZINE1 = new Magazine(3L, "123456789013", "title3", RELEASE_DATE_TEST.plusDays(1L) );
    public static final Magazine MAGAZINE2 = new Magazine(4L, "123456789014", "title4", RELEASE_DATE_TEST.plusDays(2L));

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
*/
}

