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
import org.tuxotpub.booksmanager.entities.Publication;
import org.tuxotpub.booksmanager.exceptions.RestResponseExceptionHandler;
import org.tuxotpub.booksmanager.services.PublicationService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.tuxotpub.booksmanager.controllers.PublicationController.BASE_PATH;

/**
 * Created by tuxsamo.
 */
public class PublicationControllerTest extends BaseTest {

    @Mock
    private PublicationService publicationService;

    @InjectMocks
    private PublicationController publicationController;
    private MockMvc mockMvc;
    private JacksonTester<Publication> jsonPublication;
    private Publication publication1, publication2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        JacksonTester.initFields(this, new ObjectMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(publicationController)
                .setControllerAdvice(new RestResponseExceptionHandler()).build();

        publication1 = TestHelper.GET_NEW_PUBLICATION(1L);
        TestHelper.SETUP_ENTITY(publication1, 1L);

        publication2 = TestHelper.GET_NEW_PUBLICATION(2L);
        TestHelper.SETUP_ENTITY(publication2, 2L);

    }

    @Test
    public void save() throws Exception {
        Publication newPublication = TestHelper.GET_NEW_PUBLICATION(1L);
        when(publicationService.save(newPublication)).thenReturn(publication1);

        ResultActions actions = mockMvc.perform(
                post(BASE_PATH).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(
                        jsonPublication.write(newPublication).getJson()
                )).andExpect(status().isOk());

        verify(actions, publication1);
    }

    @Test
    public void update() throws Exception {
        publication1.setUpdatedOn(LocalDateTime.now());
        when(publicationService.update(publication1)).thenReturn(publication1);

        ResultActions actions = mockMvc.perform(
                put(BASE_PATH).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content( jsonPublication.write(publication1).getJson()
                        )).andExpect( status().isOk() );

        verify(actions, publication1);
    }

    @Test
    public void findById() throws Exception {
        when(publicationService.findById(publication1.getEntityId())).thenReturn(publication1);

        ResultActions result = mockMvc.perform(
                get(BASE_PATH + "/" + publication1.getEntityId()).accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
        ).andExpect(status().isOk());

        verify(result, publication1);
    }

    @Test
    public void getByIsbn() throws Exception {
        when(publicationService.findByIsbn(publication1.getIsbn())).thenReturn(publication1);

        ResultActions actions = mockMvc.perform(
                get(BASE_PATH + "/isbn/" + publication1.getIsbn()).accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(status().isOk());

        verify(actions, publication1);
    }

    @Test
    public void findAll() throws Exception {
        List<Publication> publications = Arrays.asList(publication1, publication2);
        when(publicationService.findAll()).thenReturn(publications);

        mockMvc.perform(
                get(BASE_PATH).accept(MediaType.APPLICATION_JSON_UTF8_VALUE
                )).andExpect(status().isOk());
    }

    @Test
    public void deleteById() throws Exception {
        mockMvc.perform(
                delete(BASE_PATH + "/" + publication1.getEntityId())
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    private void verify(final ResultActions action, Publication publication) throws Exception {
        action.andExpect( jsonPath("$.entityId", equalTo( publication.getEntityId().intValue() ) ) )
                .andExpect( jsonPath("$.isbn", equalTo( publication.getIsbn() ) ) )
                .andExpect( jsonPath("$.description", equalTo( publication.getDescription() ) ) );
    }

    private void verifyWithHal(final ResultActions action, Publication publication) throws Exception {
        String server = "http://" + action.andReturn().getRequest().getServerName();
        verify(action, publication);
        action.andExpect( jsonPath("links[0].rel", equalTo("self") ) )
                .andExpect( jsonPath("links[0].href", equalTo(server + BASE_PATH + "/" + publication.getEntityId() ) ) )
                .andExpect( jsonPath("links[1].rel", equalTo("self") ) )
                .andExpect( jsonPath("links[1].href", equalTo( server + BASE_PATH ) ) )
                .andExpect( jsonPath("links[2].rel", equalTo("self") ) )
                .andExpect( jsonPath("links[2].href", equalTo(server + BASE_PATH + "/isbn/" + publication.getIsbn() ) ) );
    }

}