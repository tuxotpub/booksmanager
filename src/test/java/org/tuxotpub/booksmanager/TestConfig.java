package org.tuxotpub.booksmanager;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.tuxotpub.booksmanager.entities.Author;
import org.tuxotpub.booksmanager.entities.Publication;

@TestConfiguration
public class TestConfig {

    @Bean
    Author author() {
        return TestHelper.GET_NEW_AUTHOR(1L);
    }

    @Bean
    Publication publication() {
        return TestHelper.GET_NEW_PUBLICATION(1L);
    }

}
