package org.tuxotpub.booksmanager.repositories;

import org.tuxotpub.booksmanager.entities.Author;

import java.util.Optional;

/**
 * Created by tuxsamo.
 */

public interface AuthorRepositoryCustom {
    
    Optional<Author> findAuthorViaDynamicGraphById(Long id);

    void deleteAuthorAndAllHisPublicationsById(Long id);
}
