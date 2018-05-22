package org.tuxotpub.booksmanager.controllers.v1;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.tuxotpub.booksmanager.api.v1.dtos.AuthorDTO;
import org.tuxotpub.booksmanager.services.authors.AuthorService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.tuxotpub.booksmanager.TestHelper.AUTHORDTO1;
import static org.tuxotpub.booksmanager.TestHelper.AUTHORDTO2;
import static org.tuxotpub.booksmanager.TestHelper.toJsonString;
import static org.tuxotpub.booksmanager.controllers.v1.AuthorController.BASE_PATH;

/**
 * Created by tuxsamo.
 */
public class AuthorControllerTest {

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private AuthorController authorController;
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authorController).build();
    }

    @Test
    public void getAllAuthors() throws Exception {

        List<AuthorDTO> authorsDTO = Arrays.asList( AUTHORDTO1, AUTHORDTO2 );
        when(authorService.getAllAuthors()).thenReturn(authorsDTO);

        mockMvc.perform(get(BASE_PATH + "all").contentType(MediaType.APPLICATION_JSON))
                .andExpect( status().isOk() )
                .andExpect( jsonPath("$.authorDTOS", hasSize( authorsDTO.size() ) ) );
    }

    @Test
    public void createAuthor() throws Exception {

        when( authorService.createAuthor( AUTHORDTO1 ) ).thenReturn( AUTHORDTO1 );

        mockMvc.perform(post( BASE_PATH + "create" )
                .contentType( MediaType.APPLICATION_JSON )
                .content(toJsonString(AUTHORDTO1)))
                .andExpect(status().isCreated())
                .andExpect( jsonPath( "$.id", equalTo( AUTHORDTO1.getId().intValue() ) ) )
                .andExpect( jsonPath( "$.name", equalTo( AUTHORDTO1.getName() ) ) )
                .andExpect( jsonPath( "$.surname", equalTo( AUTHORDTO1.getSurname() ) ) )
                .andExpect( jsonPath( "$.email", equalTo( AUTHORDTO1.getEmail() ) ) )
                .andExpect( jsonPath( "$.url", equalTo( AUTHORDTO1.getUrl() ) ) );
    }

    @Test
    public void updateAuthorById() throws Exception {

        when(authorService.updateAuthorById(anyLong(),any(AuthorDTO.class))).thenReturn(AUTHORDTO1);

        mockMvc.perform(put(BASE_PATH + "updatebyid/" + AUTHORDTO1.getId(), AUTHORDTO1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(AUTHORDTO1)))
                .andExpect(status().isOk())
                .andExpect( jsonPath( "$.id", equalTo( AUTHORDTO1.getId().intValue() ) ) )
                .andExpect( jsonPath( "$.name", equalTo( AUTHORDTO1.getName() ) ) )
                .andExpect( jsonPath( "$.surname", equalTo( AUTHORDTO1.getSurname() ) ) )
                .andExpect( jsonPath( "$.email", equalTo( AUTHORDTO1.getEmail() ) ) )
                .andExpect( jsonPath( "$.url", equalTo( AUTHORDTO1.getUrl() ) ) );
    }

    @Test
    public void deleteAuthorById() throws Exception {

        mockMvc.perform( delete( BASE_PATH + "deletebyid/" + AUTHORDTO1.getId() )
                .contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isOk() );
    }
}