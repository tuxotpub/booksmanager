package org.tuxotpub.booksmanager.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tuxotpub.booksmanager.entities.Publication;
import org.tuxotpub.booksmanager.services.PublicationService;

/**
 * Created by tuxsamo.
 */

@RestController
@RequestMapping(PublicationController.BASE_PATH)
public class PublicationController extends BaseController<Publication, Long> {

    public static final String BASE_PATH = "/api/books";
    public static final String FINDBYISBN_PATH = "/isbn";

    private final PublicationService publicationService;

    public PublicationController(PublicationService publicationService) {
        super(publicationService);
        this.publicationService = publicationService;
    }

    @GetMapping("/isbn/{isbn}")
    public Publication findByIsbn(@PathVariable String isbn){
        return publicationService.findByIsbn(isbn);
    }
}
