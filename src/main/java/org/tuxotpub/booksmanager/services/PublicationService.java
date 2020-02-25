package org.tuxotpub.booksmanager.services;

import org.tuxotpub.booksmanager.entities.Publication;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by tuxsamo.
 */

public interface PublicationService extends BaseService<Publication, Long> {

    Publication findByIsbn(String isbn);

    List<Publication> findByDescription(String description);

    List<Publication> getByReleaseDate(LocalDate releaseDate);

}

