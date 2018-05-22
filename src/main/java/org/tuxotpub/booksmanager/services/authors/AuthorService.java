package org.tuxotpub.booksmanager.services.authors;

import org.tuxotpub.booksmanager.api.v1.dtos.AuthorDTO;

import java.util.List;

/**
 * Created by tuxsamo.
 */

public interface AuthorService {

    List<AuthorDTO> getAllAuthors();

    AuthorDTO getAuthorById(Long id);

    AuthorDTO createAuthor(AuthorDTO authorDTO);

    AuthorDTO updateAuthorById(Long id, AuthorDTO authorDTO);

    AuthorDTO getAuthorByEmail(String email);

    void deleteAuthorById(Long id);
}
