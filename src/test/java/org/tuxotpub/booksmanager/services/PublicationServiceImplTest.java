package org.tuxotpub.booksmanager.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.tuxotpub.booksmanager.TestHelper;
import org.tuxotpub.booksmanager.entities.Publication;
import org.tuxotpub.booksmanager.repositories.PublicationRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

/**
 * Created by tuxsamo.
 *
 * Assertj and mockito BDD style testing
 */

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith({SpringExtension.class})
public class PublicationServiceImplTest {

    @Mock
    private PublicationRepository publicationRepository;

    @InjectMocks
    private PublicationServiceImpl publicationService;
    private List<Publication> publications = new ArrayList<>();

    @BeforeEach
    public void setUp() throws Exception {
        //MockitoAnnotations.initMocks(this);
        //publicationService = new PublicationServiceImpl(publicationRepository);

        publications.add( TestHelper.GET_NEW_PUBLICATION(1L) );
        TestHelper.SETUP_ENTITY(publications.get(0), 1L);
        publications.add( TestHelper.GET_NEW_PUBLICATION(2L) );
        TestHelper.SETUP_ENTITY(publications.get(1), 2L);
    }

    @Test
    public void save() {
        when(publicationRepository.findById(anyLong())).thenReturn(Optional.of(publications.get(0)));
        when(publicationRepository.save(any(Publication.class))).thenReturn(publications.get(0));
        Publication publication = publicationService.update(publications.get(0));
        assertThat(publication.getEntityId()).isEqualTo(publications.get(0).getEntityId());
    }

    @Test
    public void update() {
        when(publicationRepository.findById(anyLong())).thenReturn(Optional.of(publications.get(0)));
        when(publicationRepository.save(any(Publication.class))).thenReturn(publications.get(0));
        Publication publication = publicationService.update(publications.get(0));
        assertThat(publication.getEntityId()).isEqualTo(publications.get(0).getEntityId());
    }

    @Test
    public void findById() {
        when(publicationRepository.findById(anyLong())).thenReturn(Optional.of(publications.get(0)));
        Publication publication = publicationService.findById(1L);
        assertThat(publication.getEntityId()).isEqualTo(publications.get(0).getEntityId());
    }

    @Test
    public void findAll() {
        when(publicationRepository.findAll()).thenReturn(publications);
        List<Publication> bookTest  = publicationService.findAll();
        assertThat(bookTest.size()).isEqualTo(publications.size());
    }

    @Test
    public void deleteById() {
        publicationService.deleteById(publications.get(0).getEntityId());
    }
}
