package org.tuxotpub.booksmanager.services;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.tuxotpub.booksmanager.api.v1.dtos.BookDTO;
import org.tuxotpub.booksmanager.api.v1.mapper.BookMapper;
import org.tuxotpub.booksmanager.bootstrap.BootstrapData;
import org.tuxotpub.booksmanager.controllers.v1.BookController;
import org.tuxotpub.booksmanager.services.parchments.BookService;
import org.tuxotpub.booksmanager.services.parchments.BookServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.tuxotpub.booksmanager.bootstrap.BootstrapData.BOOKS;
import static org.tuxotpub.booksmanager.bootstrap.BootstrapData.PARCHMENTS_SIZE;

/**
 * Created by tuxsamo.
 *
 * Integration test
 */

@Slf4j
public class BookServiceImplTestIT extends BaseServiceTestIT {

    private BookMapper bookMapper = BookMapper.INSTANCE;
    private BookService bookService;
    private List<BookDTO> bookDTOS = new ArrayList<>();

    @BeforeClass
    public static void initTest(){
        log.debug("Init test of : {}", BookServiceImplTestIT.class.getSimpleName());
    }

    @Before
    public void setUp() throws Exception {
        bootStrapData();
        bookService = new BookServiceImpl(bookMapper, bookRepository);
        BootstrapData.BOOKS.forEach(
                book -> {
                    BookDTO bookDTO = bookMapper.getBookDTO( book );
                    bookDTO.setUrl(BookController.FINDBYID_PATH + bookDTO.getId());
                    bookDTOS.add( bookDTO );
                });
    }

    @Test
    public void getAllBooks() throws Exception {
        assertThat( bookService.getAll() ).containsExactlyInAnyOrderElementsOf( bookDTOS );
    }

    @Test
    public void getBookByIsbn() throws Exception {
        assertThat( bookService.getByIsbn( bookDTOS.get(0).getIsbn() ) ).isEqualToComparingFieldByField( bookDTOS.get(0) );
    }

    @Test
    public void getBookById() throws Exception {
        assertThat( bookService.getById( bookDTOS.get(0).getId() )).isEqualToComparingFieldByField( bookDTOS.get(0) );
    }

    @Test
    public void createBook() throws Exception {
        BookDTO toCreate = bookMapper.getBookDTO( BootstrapData.buildBook( PARCHMENTS_SIZE ) );
        toCreate = bookService.create( toCreate );
        assertThat( toCreate ).isEqualToComparingFieldByField( bookService.getById( toCreate.getId() ) );
    }

    @Test
    public void updateBookById() throws Exception {
        Long id = bookDTOS.get( 0 ).getId();
        BookDTO toUpdate = bookMapper.getBookDTO( BootstrapData.buildBook( PARCHMENTS_SIZE + 1 ) );
        toUpdate = bookService.updateById( id, toUpdate );
        assertThat( toUpdate ).isEqualToComparingFieldByField( bookService.getById( id ) );
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deleteBookById() throws Exception {
        Long idToDelete = BOOKS.get( 0 ).getId();
        bookService.deleteById( idToDelete );
        bookService.getOne( idToDelete );
    }
}