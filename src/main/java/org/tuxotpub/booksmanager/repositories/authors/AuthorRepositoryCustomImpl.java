package org.tuxotpub.booksmanager.repositories.authors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Repository;
import org.tuxotpub.booksmanager.entities.Author;
import org.tuxotpub.booksmanager.repositories.EntityRepositoryImpl;

import javax.persistence.EntityManager;
import java.util.Optional;

/**
 * Created by tuxsamo.
 */

@Repository
public class AuthorRepositoryCustomImpl
        extends EntityRepositoryImpl
        implements AuthorRepositoryCustom {

    private final EntityManager em;

    //as compositon!
    //@Autowired private final EntityRepositoryImpl<Author> entityRepository;

    private static final String[] graphAttributes = new String[] {"parchments"};

    public AuthorRepositoryCustomImpl(EntityManager em) {
        super(em);
        this.em = em;
    }

    /**
     * Delete {@link Author} and {@link Author#parchments} belonging only to the deleted author (in bulk)
     *
     * @param  id   Id of the author to delete
     * @see         Author#parchments
     */
    @Override
    public void deleteAuthorAndAllHisParchmentsById(Long id) {
        Author author = Optional.ofNullable(em.find(Author.class, id)).orElseThrow(ResourceNotFoundException::new);

        author.getParchments().forEach(parchment -> {
            if (parchment.getAuthors().size() == 1)
                em.remove(parchment);
            else
                parchment.getAuthors().remove(author);
        });

        em.remove(author);
    }

    /**
     * Find {@link Author} and {@link Author#parchments} belonging to this deleted author
     * <p>
     * (avoiding {@link org.hibernate.LazyInitializationException} by using graphs)
     *
     * @param  id   Id of the author to to find
     * @return      Author
     * @see         Author#parchments
     */
    //@Cacheable
    @Override
    public Optional<Author> findAuthorViaDynamicGraphById(Long id) {
        return super.<Author> findViaDynamicGraphById(id, Author.class, graphAttributes);
    }

}
