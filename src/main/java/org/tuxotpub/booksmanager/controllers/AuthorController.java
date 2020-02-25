package org.tuxotpub.booksmanager.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tuxotpub.booksmanager.entities.Author;
import org.tuxotpub.booksmanager.services.AuthorService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(AuthorController.BASE_PATH)
public class AuthorController extends BaseController<Author, Long> {

    public static final String BASE_PATH = "/api/authors";
    public static final String FINDBYEMAIL_PATH = BASE_PATH + "/email";
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        super(authorService);
        this.authorService = authorService;
    }

    @GetMapping(value = "/email/{email}")
    public Author getByEmail(@PathVariable String email){
        return authorService.getByEmail(email);
    }

    @Override
    public List<Author> saveAll(List<Author> entities) {
        return new ArrayList<>();
    }

    @Override
    public boolean existsById(Long is) {
        return false;
    }

    @Override
    public List<Author> findAllById(List<Long> longs) {
        return new ArrayList<>();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void delete(Author entity) {

    }

    @Override
    public void deleteAll(List<Author> entity) {

    }
}
