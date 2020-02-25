package org.tuxotpub.booksmanager.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.tuxotpub.booksmanager.BaseTest;
import org.tuxotpub.booksmanager.TestHelper;
import org.tuxotpub.booksmanager.entities.Author;
import org.tuxotpub.booksmanager.exceptions.RestResponseExceptionHandler;
import org.tuxotpub.booksmanager.services.AuthorService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.tuxotpub.booksmanager.controllers.AuthorController.BASE_PATH;

/**
 * Created by tuxsamo.
 */
public class AuthorControllerTest extends BaseTest {

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private AuthorController authorController;
    private MockMvc mockMvc;
    private JacksonTester<Author> jsonAuthor;
    private Author author1, author2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        JacksonTester.initFields(this, new ObjectMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(authorController)
                .setControllerAdvice(new RestResponseExceptionHandler()).build();

        author1 = TestHelper.GET_NEW_AUTHOR(1L);
        TestHelper.SETUP_ENTITY(author1, 1L);

        author2 = TestHelper.GET_NEW_AUTHOR(2L);
        TestHelper.SETUP_ENTITY(author2, 2L);

    }

    @Test
    public void save() throws Exception {
        Author newAuthor = TestHelper.GET_NEW_AUTHOR(1L);
        when(authorService.save(newAuthor)).thenReturn(author1);

        ResultActions actions = mockMvc.perform(
                post(BASE_PATH).contentType(MediaType.APPLICATION_JSON_VALUE).content(
                        jsonAuthor.write(newAuthor).getJson()
                )).andExpect(status().isOk());

        verify(actions, author1);
    }

    @Test
    public void update() throws Exception {
        author1.setUpdatedOn(LocalDateTime.now());
        when(authorService.update(author1)).thenReturn(author1);

        ResultActions actions = mockMvc.perform(
                put(BASE_PATH).contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content( jsonAuthor.write(author1).getJson()
                )).andExpect( status().isOk() );

        verify(actions, author1);
    }

    @Test
    public void findById() throws Exception {
        when(authorService.findById(author1.getEntityId())).thenReturn(author1);

        ResultActions result = mockMvc.perform(
                    get(BASE_PATH + "/" + author1.getEntityId()).accept(MediaType.APPLICATION_JSON_VALUE)
                ).andExpect(status().isOk());

        verify(result, author1);
    }

    @Test
    public void findAll() throws Exception {
        List<Author> authors = Arrays.asList(author1, author2);
        when(authorService.findAll()).thenReturn(authors);

        mockMvc.perform(
                    get(BASE_PATH).accept(MediaType.APPLICATION_JSON_VALUE
                )).andExpect(status().isOk());
    }

    @Test
    public void deleteById() throws Exception {
        mockMvc.perform(
                    delete(BASE_PATH + "/" + author1.getEntityId())
                    .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    private void verify(final ResultActions action, Author author) throws Exception {
        action.andExpect( jsonPath("$.entityId", equalTo( author.getEntityId().intValue() ) ) )
                .andExpect( jsonPath("$.name", equalTo( author.getName() ) ) )
                .andExpect( jsonPath("$.surname", equalTo( author.getSurname() ) ) );
    }
}
