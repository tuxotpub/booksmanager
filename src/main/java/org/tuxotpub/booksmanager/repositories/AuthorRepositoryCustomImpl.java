package org.tuxotpub.booksmanager.repositories;

import org.springframework.stereotype.Repository;
import org.tuxotpub.booksmanager.entities.Author;
import org.tuxotpub.booksmanager.exceptions.ResourceNotFoundException;

import javax.persistence.EntityManager;
import java.util.Optional;

/**
 * Created by tuxsamo.
 */

@Repository
public class AuthorRepositoryCustomImpl extends EntityRepositoryImpl implements AuthorRepositoryCustom {

    private final EntityManager em;

    private static final String[] graphAttributes = new String[] {"publications"};

    public AuthorRepositoryCustomImpl(EntityManager em) {
        super(em);
        this.em = em;
    }

    /**
     * Delete {@link Author} and {@link Author#getPublications()} ()} belonging only to the deleted author (in bulk)
     *
     * @param  id   Id of the author to delete
     * @see         Author#getPublications() () ()
     * * @see         Author#getMagazines() ()
     */
    @Override
    public void deleteAuthorAndAllHisPublicationsById(Long entityId) {
        Author author = findAuthorViaDynamicGraphById(entityId).orElseThrow(ResourceNotFoundException::new);

        author.getPublications().forEach(publication -> {
            if (publication.getAuthors().size() == 1)
                em.remove(publication);
            else
                publication.getAuthors().remove(author);
        });

        em.remove(author);
    }

    /**
     * Find {@link Author} and {@link Author#getPublications()} ()} belonging to this deleted author
     * <p>
     * (avoiding {@link org.hibernate.LazyInitializationException} by using graphs)
     *
     * @param  id   Id of the author to to find
     * @return      Author
     * @see         {@link Author#getPublications()} ()}
     */
    //@Cacheable
    @Override
    public Optional<Author> findAuthorViaDynamicGraphById(Long entityId) {
        return Optional.empty();//super.<Author> findViaDynamicGraphById(entityId, Author.class, graphAttributes);
    }

}
