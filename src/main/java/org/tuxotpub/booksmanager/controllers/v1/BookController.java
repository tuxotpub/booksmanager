package org.tuxotpub.booksmanager.controllers.v1;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tuxotpub.booksmanager.api.v1.dtos.BookDTO;
import org.tuxotpub.booksmanager.services.parchments.BookService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by tuxsamo.
 */

@RestController @RequestMapping(BookController.BASE_PATH)
@Api(tags = {"bookDescription"}) @PropertySource(ignoreResourceNotFound=true,value="classpath:swagger.properties")
public class BookController {

    public static final String BASE_PATH = "/api/books/v1";
    public static final String FINDBYID_PATH = BASE_PATH + "/id/";
    public static final String FINDBYISBN_PATH = BASE_PATH + "/isbn/";
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @ApiOperation(value = "Create or Update book", notes="Hier you can create or update book")
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO createBook(@Valid @RequestBody BookDTO bookDTO){
        return bookService.create(bookDTO);
    }

    @ApiOperation(value = "View List of Authors", notes="Hier is the list of all Authors")
    @GetMapping(produces = MediaTypes.HAL_JSON_UTF8_VALUE)
    public Resources<?> getAllBooks(){
        List<Resource<BookDTO>> bookResources = new ArrayList<>();
        bookService.getAll().forEach(
                bookDTO -> { bookResources.add(new Resource<>( bookDTO, getLinks( bookDTO ) ) );
                } );
        Link link =linkTo(BookController.class).withSelfRel();
        return new Resources<Resource<BookDTO>>(bookResources,link);
    }

    @ApiOperation(value = "Find book by id", notes="Hier you can find book by id")
    @GetMapping(value = "/id/{id}", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
    public Resource<BookDTO> getBookById(@PathVariable Long id){
        BookDTO bookDTO = bookService.getById(id);
        return new Resource<>( bookDTO, getLinks( bookDTO ) );
    }

    @ApiOperation(value = "Find book by isbnTest", notes="Hier you can find book by isbnTest")
    @GetMapping(value = "/isbn/{isbn}", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
    public Resource<BookDTO> getBookByIsbn(@PathVariable String isbn){
        BookDTO bookDTO = bookService.getByIsbn(isbn);
        return new Resource<>( bookDTO, getLinks( bookDTO ));
    }

    @ApiOperation(value = "Update book by id", notes="Hier you can update book by id")
    @PutMapping("/{id}")
    public BookDTO updateBook(@PathVariable Long id, @Valid @RequestBody BookDTO bookDTO){
        return bookService.updateById(id, bookDTO);
    }

    @ApiOperation(value = "Delete book by id", notes="Hier you can delete book by id")
    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id){
        bookService.deleteById(id);
        //return ResponseEntity.noContent().build();
    }

    private List<Link> getLinks(BookDTO bookDTO) {
        return Arrays.asList(linkTo(methodOn(BookController.class).getBookById(bookDTO.getId())).withSelfRel(),
                linkTo(methodOn(BookController.class).getBookByIsbn(bookDTO.getIsbn())).withSelfRel(),
                linkTo(methodOn(BookController.class).getAllBooks()).withRel(BASE_PATH));
    }
}
