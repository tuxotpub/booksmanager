package org.tuxotpub.booksmanager.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.tuxotpub.booksmanager.TestHelper;
import org.tuxotpub.booksmanager.entities.Publication;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by tuxsamo.
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PublicationControllerTestIT extends BaseControllerTestIT<Publication, Long> {

    //@MockBean
    //private PublicationServiceImpl publicationService;

    @BeforeEach
    public void setUp() {
        BASE_PATH = PublicationController.BASE_PATH;
        clazz = Publication.class;
        List<Publication> publications = new ArrayList<>();
        publications.add(TestHelper.GET_NEW_PUBLICATION(1L));
        TestHelper.SETUP_ENTITY(publications.get(0), 1L);
        publications.add(TestHelper.GET_NEW_PUBLICATION(2L));
        TestHelper.SETUP_ENTITY(publications.get(1), 2L);
        entities = publications;
    }

    @Test
    public void findById() {
        Publication entity = entities.get(0);
        //when(publicationService.findById(entity.getEntityId())).thenReturn(entity);
        ResponseEntity<Publication> response = restTemplate.getForEntity(BASE_PATH + "/" + entity.getEntityId(), Publication.class);
        auditToBaseEntityFromResponse(entity, response.getBody());
        assertThat( response.getBody() ).isEqualToComparingFieldByField( entity );
    }

    @Test
    public void save() {
        entities.get(0).setEntityId(null);
        //when(publicationService.save(entities.get(0))).thenReturn(entities.get(0));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType( MediaType.APPLICATION_JSON);
        HttpEntity<Publication> request = new HttpEntity<>(entities.get(0), headers);
        Publication response = restTemplate.postForObject(BASE_PATH, request, clazz);
        auditToBaseEntityFromResponse(entities.get(0), response);
        assertThat( response.getIsbn() ).isEqualTo( entities.get(0).getIsbn() );
    }

    @Test
    public void findAll() {
        //when(publicationService.findAll()).thenReturn(entities);
        ResponseEntity<Publication[]> response = restTemplate.getForEntity(BASE_PATH, Publication[].class);
        assertThat( response.getBody().length ).isEqualTo( entities.size());
    }

    @Test
    public void deleteById() {
        restTemplate.delete(BASE_PATH + "/" + entities.get(0).getEntityId() );
    }

    protected void auditToBaseEntityFromResponse(Publication entity, Publication response){
        entity.setEntityId(response.getEntityId());
        entity.setCreatedBy(response.getCreatedBy());
        entity.setCreatedOn(response.getCreatedOn());
        entity.setUpdatedBy(response.getUpdatedBy());
        entity.setCreatedOn(response.getCreatedOn());
    }
}
