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
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.tuxotpub.booksmanager.api.v1.dtos.AuthorDTO;
import org.tuxotpub.booksmanager.api.v1.dtos.AuthorsDTO;
import org.tuxotpub.booksmanager.api.v1.mapper.AuthorMapper;
import org.tuxotpub.booksmanager.bootstrap.BootstrapData;
import org.tuxotpub.booksmanager.services.authors.AuthorService;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.tuxotpub.booksmanager.bootstrap.BootstrapData.*;
import static org.tuxotpub.booksmanager.controllers.v1.AuthorController.BASE_PATH;
import static org.tuxotpub.booksmanager.controllers.v1.AuthorController.FINDBYID_PATH;

/**
 * Created by tuxsamo.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AuthorControllerTestIT {

    @Autowired
    private AuthorService authorService;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private BootstrapData bootstrap;

    @Autowired
    private AuthorMapper authorMapper;

    private List<AuthorDTO> authorDTOS = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        AUTHORS.forEach(
                author -> {
                    AuthorDTO authorDTO = authorMapper.getAuthorDTO(author);
                    authorDTO.setUrl(AuthorController.FINDBYID_PATH + authorDTO.getId());
                    authorDTOS.add(authorDTO);
                });
    }

    @Test
    public void t1getAllAuthors() {
        AuthorsDTO response = restTemplate.getForObject(BASE_PATH + "all", AuthorsDTO.class);
        assertThat( response.getAuthorDTOS() ).containsExactlyInAnyOrderElementsOf( authorDTOS );
    }

    @Test
    public void t2getAuthorById() {
        AuthorDTO response = restTemplate.getForObject(FINDBYID_PATH + authorDTOS.get(0).getId(), AuthorDTO.class);
        assertThat(response).isNotNull().isEqualToComparingFieldByField(authorDTOS.get(0));
    }

    @Test
    public void t3createAuthor() {
        AuthorDTO authorDTO = authorMapper.getAuthorDTO( buildAuthor( AUTHORS_SIZE ) );
        HttpEntity<AuthorDTO> request = new HttpEntity<>( authorDTO );
        AuthorDTO reponse = restTemplate.postForObject(BASE_PATH + "create", request, AuthorDTO.class);
        assertThat(reponse).isEqualToComparingFieldByField( authorService.getAuthorById( reponse.getId() ) );
        AUTHORS.add( authorMapper.getAuthor( reponse ) );
    }

    @Test
    public void t4updateAuthorById() {
        Long id = authorDTOS.get( AUTHORS_SIZE ).getId();
        AuthorDTO toupdate = authorMapper.getAuthorDTO( buildAuthor( AUTHORS_SIZE ) );
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType( MediaType.APPLICATION_JSON );
        HttpEntity<AuthorDTO> request = new HttpEntity<>( toupdate , headers);
        ResponseEntity<AuthorDTO> response = restTemplate.exchange(BASE_PATH + id, HttpMethod.PUT, request, AuthorDTO.class);
        assertThat( response.getBody() ).isEqualToComparingFieldByField( authorService.getAuthorById( id ) );
    }

    @Test(expected = ResourceNotFoundException.class)
    public void t5deleteAuthorById() {
        long todelete = authorDTOS.get( AUTHORS_SIZE ).getId();
        restTemplate.delete( BASE_PATH + todelete  );
        AUTHORS.remove( AUTHORS_SIZE );
        authorService.getAuthorById( todelete );
    }
}