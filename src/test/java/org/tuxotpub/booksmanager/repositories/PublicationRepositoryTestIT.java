package org.tuxotpub.booksmanager.repositories;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.tuxotpub.booksmanager.BaseTest;
import org.tuxotpub.booksmanager.entities.Publication;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;
import static org.tuxotpub.booksmanager.TestHelper.*;

/**
 * Created by tuxsamo.
 *
 * Integration test
 */

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = NONE)
@DataJpaTest
@Transactional @Commit
public class PublicationRepositoryTestIT extends BaseTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    protected PublicationRepository publicationRepository;

    private List<Publication> publications = new ArrayList<>();

    @BeforeEach
    public void setUp() throws Exception {
        publications.add( GET_NEW_PUBLICATION(1L) );
        SETUP_ENTITY(publications.get(0), 1L);
        publications.add( GET_NEW_PUBLICATION(2L) );
        SETUP_ENTITY(publications.get(1), 2L);
    }

    @Test @Order(CREATE)
    public void save() {
        Publication publication = publicationRepository.save( GET_NEW_PUBLICATION(10L) );
        assertThat( publication.getEntityId() ).isNotEqualTo(null);
    }

    @Test @Order(UPDATE)
    public void update() {
        assertThat( publicationRepository.save( publications.get(0) )).isEqualTo( publications.get(0) );
    }

    @Test @Order(EXIST)
    public void findById() {
        Publication publication = entityManager.persist(GET_NEW_PUBLICATION(10L));
        assertThat( publicationRepository.findById( publication.getEntityId() )).isEqualTo( publication );
    }

    @Test @Order(EXIST)
    public void findAll() {
        entityManager.persist(GET_NEW_AUTHOR(10L));
        entityManager.persist(GET_NEW_AUTHOR(11L));
        assertThat( publicationRepository.findAll().size()).isEqualTo( 2 );
    }

    @Test @Order(DELETE)
    public void deleteById() {
        Publication publication = entityManager.persist(GET_NEW_PUBLICATION(10L));
        publicationRepository.deleteById(publication.getEntityId());
    }
}