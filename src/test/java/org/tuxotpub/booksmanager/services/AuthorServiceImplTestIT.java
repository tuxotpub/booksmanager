package org.tuxotpub.booksmanager.services;

import lombok.extern.slf4j.Slf4j;
import org.junit.*;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.tuxotpub.booksmanager.api.v1.dtos.AuthorDTO;
import org.tuxotpub.booksmanager.api.v1.mapper.AuthorMapper;
import org.tuxotpub.booksmanager.controllers.v1.AuthorController;
import org.tuxotpub.booksmanager.services.authors.AuthorService;
import org.tuxotpub.booksmanager.services.authors.AuthorServiceImpl;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.tuxotpub.booksmanager.bootstrap.BootstrapData.*;

/**
 * Created by tuxsamo.
 *
 * Integration test
 */

@Slf4j
public class AuthorServiceImplTestIT extends BaseServiceTestIT {

    private AuthorMapper authorMapper = AuthorMapper.INSTANCE;
    private AuthorService authorService;
    private List<AuthorDTO> authorDTOS = new ArrayList<>();

    @BeforeClass
    public static void initTest(){
        log.debug("Init test of : {}", AuthorServiceImplTestIT.class.getSimpleName());
    }

    @Before
    public void setUp() throws Exception {
        bootStrapData();
        authorService = new AuthorServiceImpl(authorMapper, authorRepository);
        AUTHORS.forEach(
                author -> {
                        AuthorDTO authorDTO = authorMapper.getAuthorDTO(author);
                        authorDTO.setUrl(AuthorController.FINDBYID_PATH + authorDTO.getId());
                        authorDTOS.add(authorDTO);
                });
    }

    @Test
    public void getAllAuthors() throws Exception {
        assertThat( authorService.getAllAuthors() ).containsExactlyInAnyOrderElementsOf( authorDTOS );
    }

    @Test
    public void getAuthorById() throws Exception {
        assertThat(authorService.getAuthorById( authorDTOS.get(0).getId() )).isEqualToComparingFieldByField( authorDTOS.get(0) );
    }

    @Test
    public void getAuthorByEmail() throws Exception {
        assertThat(authorService.getAuthorByEmail( authorDTOS.get(0).getEmail() )).isEqualToComparingFieldByField( authorDTOS.get(0) );
    }

    @Test
    public void createAuthor() throws Exception {
        AuthorDTO authorDTO = authorMapper.getAuthorDTO( buildAuthor( AUTHORS_SIZE ) );
        AuthorDTO savedDto = authorService.createAuthor(authorDTO);
        assertThat( savedDto ).isEqualToComparingFieldByField( authorService.getAuthorById( savedDto.getId() ) );
    }

    @Test
    public void updateAuthorById() throws Exception {
        Long id = authorDTOS.get( 0 ).getId();
        AuthorDTO toupdate = authorMapper.getAuthorDTO( buildAuthor( AUTHORS_SIZE + 1 ) );
        toupdate = authorService.updateAuthorById( id, toupdate );
        assertThat(toupdate).isEqualToComparingFieldByField( authorService.getAuthorById( id ) );
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deleteAuthorById() throws Exception {
        Long idToDelete = AUTHORS.get( 0 ).getId();
        authorService.deleteAuthorById( idToDelete );
        authorService.getAuthorById( idToDelete );
    }
}