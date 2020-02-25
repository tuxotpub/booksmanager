package org.tuxotpub.booksmanager.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tuxotpub.booksmanager.entities.Publication;
import org.tuxotpub.booksmanager.exceptions.ResourceNotFoundException;
import org.tuxotpub.booksmanager.repositories.PublicationRepository;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
public class PublicationServiceImpl
        extends BaseServiceImpl<Publication, Long> implements PublicationService {

    protected final PublicationRepository publicationRepository;

    public PublicationServiceImpl(PublicationRepository publicationRepository) {
        super(publicationRepository);
        this.publicationRepository = publicationRepository;
    }

    @Override
    public Publication findByIsbn(String isbn) {
        return publicationRepository.findByIsbn(isbn).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Publication> findByDescription(String description) {
        return publicationRepository.findPublicationsByDescription(description);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Publication> getByReleaseDate(LocalDate releaseDate) {
        return publicationRepository.findPublicationsByReleaseDate(releaseDate);
    }
}
