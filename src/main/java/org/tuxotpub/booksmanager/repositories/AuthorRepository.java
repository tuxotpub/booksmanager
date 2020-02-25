package org.tuxotpub.booksmanager.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.tuxotpub.booksmanager.entities.Author;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends BaseRepository<Author, Long>
        , EntityRepository
        , AuthorRepositoryCustom {

    /**
     * Find {@link Author} and {@link Author#getPublications()} ()} ()} belonging to this deleted author
     * <p>
     * (avoiding {@link org.hibernate.LazyInitializationException})
     *
     * @param  id   Id of the author to to find
     * @return      Author
     * @see         Author#getPublications() () ()
     */

    @Query("SELECT c FROM Author c LEFT JOIN FETCH c.publications WHERE c.entityId = ?1 ")
    Optional<Author> findAuthorAndPublicationsById(Long id);

    /**
     * Find {@link Author}s and {@link Author#getPublications()} ()} ()} belonging to this deleted author
     * <p>
     * (avoiding {@link org.hibernate.LazyInitializationException})
     *
     * @return      Author
     * @see         Author#getPublications() () ()
     */
    @Query("SELECT c FROM Author c LEFT JOIN FETCH c.publications")
    List<Author> findAllAuthorsAndPublications();

    Optional<Author> getByEmail(String email);
}
