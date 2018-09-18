package org.tuxotpub.booksmanager.controllers.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.tuxotpub.booksmanager.api.v1.dtos.AuthorDTO;
import org.tuxotpub.booksmanager.api.v1.dtos.AuthorsDTO;
import org.tuxotpub.booksmanager.services.authors.AuthorService;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by tuxsamo.
 */

@RestController
@RequestMapping(AuthorController.BASE_PATH)
@Api(tags = {"authorDescription"}) @PropertySource(ignoreResourceNotFound=true,value="classpath:swagger.properties")
public class AuthorController {

    public static final String BASE_PATH = "/api/authors/v1/";
    public static final String FINDBYID_PATH = BASE_PATH + "id/";
    public static final String FINDBYEMAIL_PATH = BASE_PATH + "email/";
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @ApiOperation(value = "Create or Update author", notes="Hier you can create or update author")
    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorDTO createAuthor(@Valid @RequestBody AuthorDTO authorDTO){
        return authorService.createAuthor(authorDTO);
    }

    @ApiOperation(value = "View List of Authors", notes="Hier is the list of all Authors")
    @GetMapping("all")
    public AuthorsDTO getAllAuthors(){
        List<AuthorDTO> copyAuthorsDTO = authorService.getAllAuthors();
        return new AuthorsDTO(copyAuthorsDTO);
    }

    @ApiOperation(value = "Find author by id", notes="Hier you can find author by id")
    @GetMapping("id/{id}")
    public AuthorDTO getAuthorById(@PathVariable Long id){
        return authorService.getAuthorById(id);
    }

    @ApiOperation(value = "Find author by email", notes="Hier you can find author by email")
    @GetMapping("email/{email}")
    public AuthorDTO getAuthorByEmail(@PathVariable String email){
        return authorService.getAuthorByEmail(email);
    }

    @ApiOperation(value = "Update author by id", notes="Hier you can update author by id")
    @PutMapping("{id}")
    public AuthorDTO updateAuthor(@PathVariable Long id, @Valid @RequestBody AuthorDTO authorDTO){
        return authorService.updateAuthorById(id, authorDTO);
    }

    @ApiOperation(value = "Delete author by id", notes="Hier you can delete author by id")
    @DeleteMapping("{id}")
    public void deleteAuthor(@PathVariable Long id){
        authorService.deleteAuthorById(id);

    }
}
