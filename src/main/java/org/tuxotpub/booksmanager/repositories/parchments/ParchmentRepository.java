package org.tuxotpub.booksmanager.repositories.parchments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.tuxotpub.booksmanager.entities.Parchment;

import java.util.List;
import java.util.Optional;

/**
 * Created by tuxsamo.
 */

@Repository
public interface ParchmentRepository<T extends Parchment> extends JpaRepository<T, Long> {

    @Query("select p from #{#entityName} as p where p.isbn = ?1 ")
    Optional<T> findByIsbnEager(String isbn);

    @Query("SELECT p FROM #{#entityName} p LEFT JOIN FETCH p.authors WHERE p.id = ?1 ")
    Optional<T> findWithAuthorsByIdEager(Long id);

    @Query("SELECT p FROM #{#entityName} p LEFT JOIN FETCH p.authors")
    List<T> findAllEager();
}
