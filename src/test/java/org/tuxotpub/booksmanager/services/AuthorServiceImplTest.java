package org.tuxotpub.booksmanager.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.tuxotpub.booksmanager.api.v1.dtos.AuthorDTO;
import org.tuxotpub.booksmanager.api.v1.dtos.AuthorsDTO;
import org.tuxotpub.booksmanager.api.v1.mapper.AuthorMapper;
import org.tuxotpub.booksmanager.entities.Author;
import org.tuxotpub.booksmanager.repositories.authors.AuthorRepository;
import org.tuxotpub.booksmanager.services.authors.AuthorService;
import org.tuxotpub.booksmanager.services.authors.AuthorServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.tuxotpub.booksmanager.TestHelper.*;

/**
 * Created by tuxsamo.
 *
 * Assertj and mockito BDD style testing
 */

public class AuthorServiceImplTest {

    @Mock
    private AuthorRepository authorRepository;

    private AuthorService authorService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        authorService = new AuthorServiceImpl(AuthorMapper.INSTANCE, authorRepository);
    }

    @Test
    public void getAllAuthors() {
        List<Author> authors = Arrays.asList(AUTHOR1, AUTHOR2);
        given(authorRepository.findAll()).willReturn(authors);
        AuthorsDTO authorsDTO = new AuthorsDTO(authorService.getAllAuthors());
        then(authorRepository).should(times(1)).findAll();
        assertThat(authorsDTO.getAuthorDTOS().size()).isEqualTo(2);
    }

    @Test
    public void getAuthorById() {
        given(authorRepository.findAuthorViaDynamicGraphById(anyLong())).willReturn(Optional.of(AUTHOR1));
        AuthorDTO authorDTO = authorService.getAuthorById(1L);
        then(authorRepository).should(times(1)).findAuthorViaDynamicGraphById(anyLong());
        assertThat(authorDTO).isEqualToComparingFieldByField(AUTHORDTO1);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getAuthorByIdNotFound() throws Exception {
        given(authorRepository.findAuthorViaDynamicGraphById(anyLong())).willReturn(Optional.empty());
        authorService.getAuthorById(1L);
    }

    @Test
    public void createAuthor() {
        given(authorRepository.save(any(Author.class))).willReturn(AUTHOR1);
        AuthorDTO savedAuthorDTO = authorService.createAuthor(AUTHORDTO1);
        then(authorRepository).should().save(any(Author.class));
        assertThat(savedAuthorDTO).isEqualToComparingFieldByField(AUTHORDTO1);
    }

    @Test
    public void updateAuthorById() {
        given(authorRepository.findAuthorViaDynamicGraphById(anyLong())).willReturn(Optional.of(AUTHOR1));
        given(authorRepository.save(any(Author.class))).willReturn(AUTHOR1);
        AuthorDTO savedAuthorsDTO = authorService.updateAuthorById(AUTHORDTO1.getId(), AUTHORDTO1);
        then(authorRepository).should().save(any(Author.class));
        assertThat(savedAuthorsDTO).isEqualToComparingFieldByField(AUTHORDTO1);
    }

    @Test
    public void deleteAuthorById() {
        authorRepository.deleteAuthorAndAllHisParchmentsById(1L);
        verify(authorRepository, times(1)).deleteAuthorAndAllHisParchmentsById(1L);
    }
}