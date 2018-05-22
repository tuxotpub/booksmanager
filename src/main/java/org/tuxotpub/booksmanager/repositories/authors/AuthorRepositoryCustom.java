package org.tuxotpub.booksmanager.repositories.authors;

import org.tuxotpub.booksmanager.entities.Author;

import java.util.Optional;

/**
 * Created by tuxsamo.
 */

public interface AuthorRepositoryCustom {
    
    Optional<Author> findAuthorViaDynamicGraphById(Long id);

    void deleteAuthorAndAllHisParchmentsById(Long id);
}
