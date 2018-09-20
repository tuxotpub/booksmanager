package org.tuxotpub.booksmanager.controllers.v1;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.tuxotpub.booksmanager.api.v1.dtos.BookDTO;
import org.tuxotpub.booksmanager.api.v1.dtos.BooksDTO;
import org.tuxotpub.booksmanager.api.v1.mapper.BookMapper;
import org.tuxotpub.booksmanager.bootstrap.BootstrapData;
import org.tuxotpub.booksmanager.services.parchments.BookService;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.tuxotpub.booksmanager.bootstrap.BootstrapData.*;
import static org.tuxotpub.booksmanager.controllers.v1.BookController.*;

/**
 * Created by tuxsamo.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BookControllerTestIT {

    @Autowired
    //private ParchmentService<BookDTO, Book> bookService;
    private BookService bookService;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private BootstrapData bootstrap;

    @Autowired
    private BookMapper bookMapper;

    private List<BookDTO> bookDTOS = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        BOOKS.forEach(
                book -> {
                    BookDTO bookDTO = bookMapper.getBookDTO(book);
                    bookDTO.setUrl( FINDBYID_PATH + bookDTO.getId() );
                    bookDTOS.add(bookDTO);
                });
    }

    @Test
    public void t1getAllBooks() {
        Resources<Resource<BookDTO>> response = restTemplate.getForObject(BASE_PATH, Resources.class);
        assertThat( response.getContent().size() ).isEqualTo( bookDTOS.size() );
    }

    @Test
    public void t2getBookById() {
        BookDTO response = restTemplate.getForObject(FINDBYID_PATH + bookDTOS.get(0).getId(), BookDTO.class);
        assertThat( response ).isEqualToComparingFieldByField( bookDTOS.get(0) );
    }

    @Test
    public void t3createBook() {
        BookDTO bookDTO = bookMapper.getBookDTO( buildBook( PARCHMENTS_SIZE ) );
        HttpEntity<BookDTO> request = new HttpEntity<>(bookDTO);
        BookDTO reponse = restTemplate.postForObject(BASE_PATH + "/create", request, BookDTO.class);
        assertThat( reponse ).isEqualToComparingFieldByField( bookService.getById( reponse.getId() ) );
        BOOKS.add( bookMapper.getBook( reponse ) );
    }

    @Test
    public void t4updateBookById() {
        Long id = bookDTOS.get( PARCHMENTS_SIZE ).getId();
        BookDTO toupdate = bookMapper.getBookDTO( buildBook( PARCHMENTS_SIZE ) );
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType( MediaType.APPLICATION_JSON );
        HttpEntity<BookDTO> request = new HttpEntity<>( toupdate , headers);
        ResponseEntity<BookDTO> response = restTemplate.exchange(BASE_PATH + "/" + id, HttpMethod.PUT, request, BookDTO.class);
        assertThat( response.getBody() ).isEqualToComparingFieldByField( bookService.getById( id ) );
    }

    @Test(expected = ResourceNotFoundException.class)
    public void t5deleteBookById() {
        long todelete = bookDTOS.get( PARCHMENTS_SIZE ).getId();
        restTemplate.delete(BASE_PATH + "/" + todelete );
        BOOKS.remove( PARCHMENTS_SIZE );
        bookService.getById( todelete );
    }
}