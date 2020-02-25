package org.tuxotpub.booksmanager.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.tuxotpub.booksmanager.BaseTest;
import org.tuxotpub.booksmanager.TestHelper;
import org.tuxotpub.booksmanager.entities.Author;
import org.tuxotpub.booksmanager.repositories.AuthorRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


/**
 * Created by tuxsamo.
 *
 * Assertj and mockito BDD style testing
 */

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith({SpringExtension.class})
public class AuthorServiceImplTest extends BaseTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorServiceImpl authorService;

    private List<Author> authors  = new ArrayList<>();

    @BeforeEach
    public void setUp() throws Exception {
        //MockitoAnnotations.initMocks(this);
        //authorService = new AuthorServiceImpl(authorRepository);

        authors.add( TestHelper.GET_NEW_AUTHOR(1L) );
        TestHelper.SETUP_ENTITY(authors.get(0), 1L);
        authors.add( TestHelper.GET_NEW_AUTHOR(2L) );
        TestHelper.SETUP_ENTITY(authors.get(1), 2L);
    }

    @Test
    public void save() {
        when(authorRepository.save(any(Author.class))).thenReturn(authors.get(0));
        Author author= authorService.save(authors.get(0));
        assertThat(author).isEqualTo(authors.get(0));
    }

    @Test
    public void update() {
        when(authorRepository.save(any(Author.class))).thenReturn(authors.get(0));
        Author author= authorService.update(authors.get(0));
        assertThat(author).isEqualTo(authors.get(0));
    }

    @Test
    public void findById() {
        when(authorRepository.findById(anyLong())).thenReturn(Optional.of(authors.get(0)));
        Author author= authorService.findById(1L);
        assertThat(author).isEqualTo(authors.get(0));
    }

    @Test
    public void findAll() {
        when(authorRepository.findAll()).thenReturn(authors);
        List<Author> authorTest  = authorService.findAll();
        assertThat(authorTest.size()).isEqualTo(authors.size());
    }

    @Test
    public void deleteById() {
        authorRepository.deleteById(authors.get(0).getEntityId());
        verify(authorRepository, times(1)).deleteById(authors.get(0).getEntityId());
    }
}
