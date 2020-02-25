package org.tuxotpub.booksmanager.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.web.server.LocalManagementPort;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.tuxotpub.booksmanager.TestHelper;
import org.tuxotpub.booksmanager.entities.Author;
import org.tuxotpub.booksmanager.services.AuthorServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by tuxsamo.
 */

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthorControllerTestIT extends BaseControllerTestIT<Author, Long> {

    @LocalServerPort
    int serverPort;

    @LocalManagementPort
    int managementPort;

    @Autowired
    TestRestTemplate restTemplate;

    @MockBean
    private AuthorServiceImpl authorService;

    @BeforeEach
    public void setUp() {
        BASE_PATH = AuthorController.BASE_PATH;
        clazz = Author.class;
        List<Author> authors = new ArrayList<>();
        authors.add(TestHelper.GET_NEW_AUTHOR(1L));
        TestHelper.SETUP_ENTITY(authors.get(0), 1L);
        authors.add(TestHelper.GET_NEW_AUTHOR(2L));
        TestHelper.SETUP_ENTITY(authors.get(1), 2L);
        entities = authors;
    }

    @Test
    public void findById() {
        Author entity = entities.get(0);
        when(authorService.findById(entity.getEntityId())).thenReturn(entity);
        ResponseEntity<Author> response = restTemplate.exchange(BASE_PATH + "/" + entity.getEntityId(), HttpMethod.GET, null, clazz);
        assertThat( (response.getBody()).getEntityId() ).isEqualTo( entity.getEntityId() );
    }

    @Test
    public void save() {
        when(authorService.save(entities.get(0))).thenReturn(entities.get(0));
        HttpEntity<Author> request = new HttpEntity<>(entities.get(0));
        Author response = restTemplate.postForObject(BASE_PATH, request, clazz);
        auditToBaseEntityFromResponse(entities.get(0), response);
        assertThat( response.getEntityId() ).isNotNull();
    }


    @Test
    public void findAll() {
        when(authorService.findAll()).thenReturn(entities);
        ResponseEntity<Author[]> response = restTemplate.getForEntity(BASE_PATH, Author[].class);
        assertThat( response.getBody().length ).isEqualTo( entities.size());
    }

    @Test
    public void deleteById() {
        restTemplate.delete(BASE_PATH + "/" + entities.get(0).getEntityId() );
    }


    protected void auditToBaseEntityFromResponse(Author entity, Author response){
        entity.setEntityId(response.getEntityId());
        entity.setCreatedBy(response.getCreatedBy());
        entity.setCreatedOn(response.getCreatedOn());
        entity.setUpdatedBy(response.getUpdatedBy());
        entity.setCreatedOn(response.getCreatedOn());
    }
}
