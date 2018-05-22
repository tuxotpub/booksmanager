package org.tuxotpub.booksmanager.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.tuxotpub.booksmanager.api.v1.dtos.BookDTO;
import org.tuxotpub.booksmanager.api.v1.dtos.BooksDTO;
import org.tuxotpub.booksmanager.api.v1.mapper.BookMapper;
import org.tuxotpub.booksmanager.entities.Book;
import org.tuxotpub.booksmanager.repositories.parchments.BookRepository;
import org.tuxotpub.booksmanager.services.parchments.BookService;
import org.tuxotpub.booksmanager.services.parchments.BookServiceImpl;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.*;
import static org.tuxotpub.booksmanager.TestHelper.*;

/**
 * Created by tuxsamo.
 *
 * Assertj and mockito BDD style testing
 */

public class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    //private ParchmentService<BookDTO, Book> bookService;
    private BookService bookService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        bookService = new BookServiceImpl(BookMapper.INSTANCE, bookRepository);
    }

    @Test
    public void getAllBooks() {
        List<Book> books = Arrays.asList(BOOK1, BOOK2);
        given(bookRepository.findAll()).willReturn(books);
        BooksDTO booksDTO = new BooksDTO(bookService.getAll());
        then(bookRepository).should(times(1)).findAll();
        assertThat(booksDTO.getBookDTOS().size()).isEqualTo(2);
    }

    @Test
    public void getBookById() {
        given(bookRepository.findWithAuthorsByIdEager(anyLong())).willReturn(Optional.of(BOOK1));
        BookDTO bookDTO = bookService.getById(1L);
        then(bookRepository).should(times(1)).findWithAuthorsByIdEager(anyLong());
        assertThat(bookDTO).isEqualToComparingFieldByField(BOOKDTO1);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getBookByIdNotFound() throws Exception {
        given(bookRepository.findWithAuthorsByIdEager(anyLong())).willReturn(Optional.empty());
        bookService.getById(1L);
    }

    @Test
    public void createBook() {
        given(bookRepository.save(any(Book.class))).willReturn(BOOK1);
        BookDTO savedBookDTO = bookService.create(BOOKDTO1);
        then(bookRepository).should().save(any(Book.class));
        assertThat(savedBookDTO).isEqualToComparingFieldByField(BOOKDTO1);
    }

    @Test
    public void updateBookById() {
        given(bookRepository.findWithAuthorsByIdEager(anyLong())).willReturn(Optional.of(BOOK1));
        given(bookRepository.save(any(Book.class))).willReturn(BOOK1);
        BookDTO savedBooksDTO = bookService.updateById(BOOKDTO1.getId(), BOOKDTO1);
        then(bookRepository).should().save(any(Book.class));
        assertThat(savedBooksDTO).isEqualToComparingFieldByField(BOOKDTO1);
    }

    @Test
    public void deleteBookById() {
        bookRepository.deleteById(1L);
        verify(bookRepository, times(1)).deleteById(1L);
    }
}