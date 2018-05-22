package org.tuxotpub.booksmanager.services.parchments;

import org.tuxotpub.booksmanager.api.v1.dtos.BookDTO;
import org.tuxotpub.booksmanager.services.ParchmentService;

import java.util.List;

/**
 * Created by tuxsamo.
 */
public interface BookService extends ParchmentService<BookDTO>{

    List<BookDTO> findByDescription(String description);
}
