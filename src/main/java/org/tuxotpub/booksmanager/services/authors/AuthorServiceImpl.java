package org.tuxotpub.booksmanager.services.authors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.tuxotpub.booksmanager.api.v1.dtos.AuthorDTO;
import org.tuxotpub.booksmanager.api.v1.mapper.AuthorMapper;
import org.tuxotpub.booksmanager.controllers.v1.AuthorController;
import org.tuxotpub.booksmanager.entities.Author;
import org.tuxotpub.booksmanager.repositories.authors.AuthorRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
public class AuthorServiceImpl implements AuthorService {

    private final AuthorMapper authorMapper;
    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorMapper authorMapper, AuthorRepository authorRepository) {
        this.authorMapper = authorMapper;
        this.authorRepository = authorRepository;
    }

    @Override
    public List<AuthorDTO> getAllAuthors() {
        List<AuthorDTO> authorDTOS = new ArrayList<>();
        authorRepository.findAll().forEach(
            author -> {
                AuthorDTO authorDTO = authorMapper.getAuthorDTO(author);
                authorDTO.setUrl(AuthorController.FINDBYID_PATH + authorDTO.getId());
                authorDTOS.add(authorDTO);
            }
        );
        return authorDTOS;
    }

    @Override
    public AuthorDTO getAuthorById(Long id) {
        Optional<Author> author = authorRepository.findAuthorViaDynamicGraphById(id);
                return author.map(authorMapper::getAuthorDTO)
                .map(authorDTO -> {
                    authorDTO.setUrl(AuthorController.FINDBYID_PATH + authorDTO.getId());
                    return authorDTO;
                })
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public AuthorDTO getAuthorByEmail(String email) {
        Optional<Author> author = authorRepository.findByEmail(email);
        return author.map(authorMapper::getAuthorDTO)
                .map(authorDTO -> {
                    authorDTO.setUrl(AuthorController.FINDBYID_PATH + authorDTO.getId());
                    return authorDTO;
                })
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    @Transactional
    public AuthorDTO createAuthor(AuthorDTO authorDTO) {
        return saveAndReturnAuthorDto(authorMapper.getAuthor(authorDTO));
    }

    @Override
    @Transactional
    public AuthorDTO updateAuthorById(Long id, AuthorDTO authorDTO) {
        return authorRepository.findAuthorViaDynamicGraphById(id).map(author -> {
            Author toUpdate = mergeAuthor( authorMapper.getAuthor(authorDTO), author );
            return saveAndReturnAuthorDto( toUpdate );
        }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    @Transactional
    public void deleteAuthorById(Long id) {
        authorRepository.deleteAuthorAndAllHisParchmentsById(id);
    }


    private AuthorDTO saveAndReturnAuthorDto(Author author) {
        Author savedAuthor = authorRepository.save(author);
        AuthorDTO savedAuthorDTO = authorMapper.getAuthorDTO(savedAuthor);
        savedAuthorDTO.setUrl(AuthorController.FINDBYID_PATH + savedAuthorDTO.getId());
        return savedAuthorDTO;
    }

    protected Author mergeAuthor(Author source, Author destination) {
        if ( !StringUtils.isEmpty( destination.getEmail() ) ) source.setEmail( destination.getEmail() );
        if ( !StringUtils.isEmpty( destination.getName() ) ) source.setName( destination.getName() );
        if ( !StringUtils.isEmpty( destination.getSurname() ) ) source.setSurname( destination.getSurname() );
        return destination;
    }
}
