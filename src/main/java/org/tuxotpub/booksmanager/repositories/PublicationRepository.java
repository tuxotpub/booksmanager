package org.tuxotpub.booksmanager.repositories;

import org.springframework.data.jpa.repository.Query;
import org.tuxotpub.booksmanager.entities.Publication;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Created by tuxsamo.
 */

public interface PublicationRepository extends BaseRepository<Publication, Long> {

    Optional<Publication> findByIsbn(String isbn);

    List<Publication> findByIsbnContainsOrderByIsbn(String isbn);

    @Query("select p from #{#entityName} as p where p.isbn = ?1 ")
    Optional<Publication> findByIsbnEager(String isbn);

    @Query("SELECT p FROM #{#entityName} p LEFT JOIN FETCH p.authors WHERE p.entityId = ?1 ")
    Optional<Publication> findWithAuthorsByIdEager(Long id);

    @Query("SELECT p FROM #{#entityName} p LEFT JOIN FETCH p.authors")
    List<Publication> findAllEager();

    List<Publication> findPublicationsByDescription(String description);

    List<Publication> findPublicationsByDescriptionContainsOrderByIsbn(String description);

    List<Publication> findPublicationsByReleaseDateBetweenOrderByReleaseDate(LocalDate from, LocalDate to);

    List<Publication> findPublicationsByReleaseDate(LocalDate date);


}
