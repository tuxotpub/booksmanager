package org.tuxotpub.booksmanager.services;

import org.tuxotpub.booksmanager.entities.Author;

/**
 * Created by tuxsamo.
 */

public interface AuthorService extends BaseService<Author, Long> {

    Author getByEmail(String email);
}
