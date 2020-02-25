package org.tuxotpub.booksmanager.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tuxotpub.booksmanager.entities.Author;
import org.tuxotpub.booksmanager.exceptions.ResourceNotFoundException;
import org.tuxotpub.booksmanager.repositories.AuthorRepository;

@Slf4j
@Service
@Transactional(readOnly = true)
public class AuthorServiceImpl extends BaseServiceImpl<Author, Long> implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        super(authorRepository);
        this.authorRepository = authorRepository;
    }

    @Override
    public Author getByEmail(String email) {
        return authorRepository.getByEmail(email).orElseThrow(ResourceNotFoundException::new);
    }
}
