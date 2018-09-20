package org.tuxotpub.booksmanager.controllers.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.PropertySource;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.tuxotpub.booksmanager.api.v1.dtos.AuthorDTO;
import org.tuxotpub.booksmanager.services.authors.AuthorService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by tuxsamo.
 */

@RestController
@RequestMapping(AuthorController.BASE_PATH)
@Api(tags = {"authorDescription"}) @PropertySource(ignoreResourceNotFound=true,value="classpath:swagger.properties")
public class AuthorController {

    public static final String BASE_PATH = "/api/authors/v1";
    public static final String FINDBYID_PATH = BASE_PATH + "/id/";
    public static final String FINDBYEMAIL_PATH = BASE_PATH + "/email/";
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @ApiOperation(value = "Create or Update author", notes="Hier you can create or update author")
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorDTO createAuthor(@Valid @RequestBody AuthorDTO authorDTO){
        return authorService.createAuthor(authorDTO);
    }

    @ApiOperation(value = "View List of Authors", notes="Hier is the list of all Authors")
    @GetMapping( produces = MediaTypes.HAL_JSON_UTF8_VALUE)
    public Resources<?> getAllAuthors(){
        List<Resource<AuthorDTO>> authorResources = new ArrayList<>();
        authorService.getAllAuthors().forEach(
                authorDTO -> { authorResources.add(new Resource<>( authorDTO, getLinks( authorDTO ) ) );
                } );
        Link link =linkTo(AuthorController.class).withSelfRel();
        return new Resources<>(authorResources,link);
    }

    @ApiOperation(value = "Find author by id", notes="Hier you can find author by id")
    @GetMapping(value = "/id/{id}", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
    public Resource<AuthorDTO> getAuthorById(@PathVariable Long id){
        AuthorDTO authorDTO = authorService.getAuthorById(id);
        return new Resource<>(authorDTO, getLinks( authorDTO ));
    }

    @ApiOperation(value = "Find author by email", notes="Hier you can find author by email")
    @GetMapping(value = "/email/{email}", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
    public Resource<AuthorDTO> getAuthorByEmail(@PathVariable String email){
        AuthorDTO authorDTO = authorService.getAuthorByEmail(email);
        return new Resource<>(authorDTO, getLinks( authorDTO ));
    }

    @ApiOperation(value = "Update author by id", notes="Hier you can update author by id")
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
    public AuthorDTO updateAuthor(@PathVariable Long id, @Valid @RequestBody AuthorDTO authorDTO){
        return authorService.updateAuthorById(id, authorDTO);
    }

    @ApiOperation(value = "Delete author by id", notes="Hier you can delete author by id")
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
    public void deleteAuthor(@PathVariable Long id){
        authorService.deleteAuthorById(id);

    }

    private List<Link> getLinks(AuthorDTO authorDTO) {
        return Arrays.asList(linkTo(methodOn(AuthorController.class).getAuthorById(authorDTO.getId())).withSelfRel(),
                linkTo(methodOn(AuthorController.class).getAuthorByEmail(authorDTO.getEmail())).withSelfRel(),
                linkTo(methodOn(AuthorController.class).getAllAuthors()).withRel(BASE_PATH));
    }
}
