package org.tuxotpub.booksmanager.repositories.authors;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.tuxotpub.booksmanager.entities.Author;
import org.tuxotpub.booksmanager.repositories.EntityRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long>
        , EntityRepository
        , AuthorRepositoryCustom {

    /**
     * Find {@link Author} and {@link Author#parchments} belonging to this deleted author
     * <p>
     * (avoiding {@link org.hibernate.LazyInitializationException})
     *
     * @param  id   Id of the author to to find
     * @return      Author
     * @see         Author#parchments
     */

    @Query("SELECT c FROM Author c LEFT JOIN FETCH c.parchments WHERE c.id = ?1 ")
    Optional<Author> findAuthorAndParchmentsById(Long id);

    /**
     * Find {@link Author}s and {@link Author#parchments} belonging to this deleted author
     * <p>
     * (avoiding {@link org.hibernate.LazyInitializationException})
     *
     * @return      Author
     * @see         Author#parchments
     */
    @Query("SELECT c FROM Author c LEFT JOIN FETCH c.parchments")
    List<Author> findAllAuthorsAndParchments();

    Optional<Author> findByEmail(String email);
}
