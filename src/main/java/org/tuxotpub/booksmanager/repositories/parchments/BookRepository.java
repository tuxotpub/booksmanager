package org.tuxotpub.booksmanager.repositories.parchments;

import org.tuxotpub.booksmanager.entities.Book;
import org.tuxotpub.booksmanager.repositories.EntityRepository;

import java.util.List;

/**
 * Created by tuxsamo.
 */
public interface BookRepository extends ParchmentRepository<Book>, EntityRepository {

    List<Book> findBooksByDescription(String description);

    List<Book> findBooksByDescriptionContainsOrderByIsbn(String description);
}
