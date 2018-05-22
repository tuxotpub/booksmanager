package org.tuxotpub.booksmanager.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.tuxotpub.booksmanager.api.v1.dtos.ParchmentDTO;
import org.tuxotpub.booksmanager.entities.Parchment;
import org.tuxotpub.booksmanager.repositories.parchments.ParchmentRepository;

import java.util.*;

@Slf4j
//@Service
@Transactional(readOnly = true)
public abstract class ParchmentServiceImpl<T extends ParchmentDTO, U extends Parchment> implements ParchmentService<T>{

    protected final ParchmentRepository<U> parchmentRepository;

    public ParchmentServiceImpl( ParchmentRepository parchmentRepository) {
        this.parchmentRepository = parchmentRepository;
    }

    @Override
    public List<T> getAll() {
        List<T> parchmentDTOs = new ArrayList<>();
        parchmentRepository.findAll().forEach(
                parchment -> {
                    T parchmentDTO = (T) getDTO(parchment);
                    parchmentDTO.setUrl(getPath() + parchmentDTO.getId());
                    parchmentDTOs.add(parchmentDTO);
                }
        );
        return parchmentDTOs;
    }

    @Override
    public T getById(Long id) {
        Optional<U> parchment = parchmentRepository.findWithAuthorsByIdEager(id);
        return (T) parchment.map( this::getDTO )
                .map(parchmentDTO -> {
                    parchmentDTO.setUrl(getPath() + parchmentDTO.getId());
                    return parchmentDTO;
                })
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public T getByIsbn(String isbn) {
        Optional<U> parchment = parchmentRepository.findByIsbnEager(isbn);
        return (T) parchment.map(this::getDTO)
                .map(parchmentDTO -> {
                    parchmentDTO.setUrl(getPath() + parchmentDTO.getId());
                    return parchmentDTO;
                })
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public T getOne(Long id){
        return (T) getDTO( parchmentRepository.findById( id ).orElseThrow( ResourceNotFoundException::new ) );
    }

    @Override
    @Transactional
    public T create(T parchmentDTO) {
        return (T) saveAndReturnDto( (U) getEntity( parchmentDTO ) );
    }

    @Override
    @Transactional
    public T updateById(Long id, T parchmentDTO) {
        return parchmentRepository.findWithAuthorsByIdEager(id).map(parchment -> {
            U toUpdate = mergeParchment( getEntity(parchmentDTO), parchment );
            return saveAndReturnDto( (U) toUpdate );
        }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        parchmentRepository.deleteById(id);
    }

    private T saveAndReturnDto(U parchment) {
        U saved = parchmentRepository.save(parchment);
        T savedDTO = (T) getDTO( saved );
        savedDTO.setUrl(getPath() + savedDTO.getId());
        return savedDTO;
    }

    protected U mergeParchment(U source, U destination){
        if ( !StringUtils.isEmpty( source.getIsbn() ) ) destination.setIsbn( source.getIsbn() );
        if ( !StringUtils.isEmpty( source.getTitle() ) ) destination.setTitle( source.getTitle() );
        return destination;
    }

    protected abstract String getPath();

    protected abstract T getDTO(U parchment);

    protected abstract U getEntity(T parchment);
}
