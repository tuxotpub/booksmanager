package org.tuxotpub.booksmanager.controllers.v1;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.tuxotpub.booksmanager.api.v1.dtos.BookDTO;
import org.tuxotpub.booksmanager.services.parchments.BookService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.tuxotpub.booksmanager.TestHelper.*;
import static org.tuxotpub.booksmanager.controllers.v1.BookController.BASE_PATH;

/**
 * Created by tuxsamo.
 */
public class BookControllerTest {

    @Mock
    //private ParchmentService<BookDTO, Book> bookService;
    private BookService bookService;

    @InjectMocks
    private BookController bookController;
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    public void getAllBooks() throws Exception {
        List<BookDTO> booksDTO = Arrays.asList( BOOKDTO1, BOOKDTO2 );
        when(bookService.getAll()).thenReturn(booksDTO);

        mockMvc.perform(get(BASE_PATH + "all").contentType(MediaType.APPLICATION_JSON))
                .andExpect( status().isOk() )
                .andExpect( jsonPath("$.bookDTOS", hasSize( booksDTO.size() ) ) );
    }

    @Test
    public void createBook() throws Exception {

        when( bookService.create( BOOKDTO1 ) ).thenReturn( BOOKDTO1 );

        mockMvc.perform(post( BASE_PATH + "create" )
                .contentType( MediaType.APPLICATION_JSON )
                .content(toJsonString(BOOKDTO1)))
                .andExpect(status().isCreated())
                .andExpect( jsonPath( "$.id", equalTo( BOOKDTO1.getId().intValue() ) ) )
                .andExpect( jsonPath( "$.isbn", equalTo( BOOKDTO1.getIsbn() ) ) )
                .andExpect( jsonPath( "$.title", equalTo( BOOKDTO1.getTitle() ) ) )
                .andExpect( jsonPath( "$.description", equalTo( BOOKDTO1.getDescription() ) ) )
                .andExpect( jsonPath( "$.url", equalTo( BOOKDTO1.getUrl() ) ) );
    }

    @Test
    public void updateBookById() throws Exception {
        when(bookService.updateById(anyLong(),any(BookDTO.class))).thenReturn(BOOKDTO1);

        mockMvc.perform(put(BASE_PATH + BOOKDTO1.getId(), BOOKDTO1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(BOOKDTO1)))
                .andExpect(status().isOk())
                .andExpect( jsonPath( "$.id", equalTo( BOOKDTO1.getId().intValue() ) ) )
                .andExpect( jsonPath( "$.isbn", equalTo( BOOKDTO1.getIsbn() ) ) )
                .andExpect( jsonPath( "$.title", equalTo( BOOKDTO1.getTitle() ) ) )
                .andExpect( jsonPath( "$.description", equalTo( BOOKDTO1.getDescription() ) ) )
                .andExpect( jsonPath( "$.url", equalTo( BOOKDTO1.getUrl() ) ) );
    }

    @Test
    public void deleteBookById() throws Exception {

        mockMvc.perform( delete( BASE_PATH + BOOKDTO1.getId() )
                .contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isOk() );
    }
}