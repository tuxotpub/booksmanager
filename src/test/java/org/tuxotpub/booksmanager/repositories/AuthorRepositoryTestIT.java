package org.tuxotpub.booksmanager.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.tuxotpub.booksmanager.TestConfig;
import org.tuxotpub.booksmanager.entities.Author;

@Commit @Transactional
@Import(TestConfig.class)
public class AuthorRepositoryTestIT extends AbstractRepositoriesTestIT<Author, Long> {

    @Autowired
    Author author;

    @BeforeEach
    public void setup(){
        clazz = Author.class;
    }

    @Override
    protected Author getEntity() {
        return author;
    }

    @Override
    protected void updateEntity(Author author) {
    }
}
