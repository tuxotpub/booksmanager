package org.tuxotpub.booksmanager.controllers.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.tuxotpub.booksmanager.api.v1.dtos.BookDTO;
import org.tuxotpub.booksmanager.api.v1.dtos.BooksDTO;
import org.tuxotpub.booksmanager.services.parchments.BookService;
import java.util.List;

/**
 * Created by tuxsamo.
 */

@RestController @RequestMapping(BookController.BASE_PATH)
@Api(tags = {"bookDescription"}) @PropertySource(ignoreResourceNotFound=true,value="classpath:swagger.properties")
public class BookController {

    public static final String BASE_PATH = "/api/v1/books/";
    public static final String FINDBYID_PATH = BASE_PATH + "findbyid/";
    private final BookService bookService;
    //private final ParchmentService<BookDTO, Book> bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @ApiOperation(value = "Create or Update book", notes="Hier you can create or update book")
    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO createBook(@RequestBody BookDTO bookDTO){
        return bookService.create(bookDTO);
    }

    @ApiOperation(value = "View List of Authors", notes="Hier is the list of all Authors")
    @GetMapping("all")
    public BooksDTO getAllBooks(){
        List<BookDTO> copyBooksDTO = bookService.getAll();
        return new BooksDTO(copyBooksDTO);
    }

    @ApiOperation(value = "Find book by id", notes="Hier you can find book by id")
    @GetMapping("findbyid/{id}")
    public BookDTO getBookById(@PathVariable Long id){
        return bookService.getById(id);
    }

    @ApiOperation(value = "Find book by isbnTest", notes="Hier you can find book by isbnTest")
    @GetMapping("findbyisbn/{isbn}")
    public BookDTO getBookByIsbn(@PathVariable String isbn){
        return bookService.getByIsbn(isbn);
    }

    @ApiOperation(value = "Update book by id", notes="Hier you can update book by id")
    @PutMapping("updatebyid/{id}")
    public BookDTO updateBook(@PathVariable Long id, @RequestBody BookDTO bookDTO){
        return bookService.updateById(id, bookDTO);
    }

    @ApiOperation(value = "Delete book by id", notes="Hier you can delete book by id")
    @DeleteMapping("deletebyid/{id}")
    public void deleteBook(@PathVariable Long id){
        bookService.deleteById(id);
    }
}
