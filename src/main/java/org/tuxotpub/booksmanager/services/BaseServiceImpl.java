package org.tuxotpub.booksmanager.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tuxotpub.booksmanager.CrudInterface;
import org.tuxotpub.booksmanager.entities.BaseEntity;
import org.tuxotpub.booksmanager.exceptions.ResourceNotFoundException;
import org.tuxotpub.booksmanager.repositories.BaseRepository;

import java.util.List;

@Service
@Transactional
public class BaseServiceImpl<T extends BaseEntity, ID> implements CrudInterface<T, ID> {

    private final BaseRepository<T, ID> baseRepository;

    public BaseServiceImpl(BaseRepository<T, ID> baseRepository) {
        this.baseRepository = baseRepository;
    }

    @Override
    public T save(T entity) {
        if (entity.getEntityId() != null) throw new IllegalArgumentException("Id must be null! Id value found:" + entity.getEntityId());
        return baseRepository.save(entity);
    }

    @Override
    public T update(T entity) {
        baseRepository.findById((ID) entity.getEntityId()).orElseThrow(ResourceNotFoundException::new);
        return baseRepository.save(entity);
    }

    @Override
    public List<T> saveAll(List<T> entities) {
        return baseRepository.saveAll(entities);
    }

    @Transactional(readOnly = true)
    @Override
    public T findById(ID id) {
        return baseRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existsById(ID id) {
        return baseRepository.existsById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<T> findAll() {
        return baseRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<T> findAllById(List<ID> ids) {
        return baseRepository.findAllById(ids);
    }

    @Override
    public long count() {
        return baseRepository.count();
    }

    @Override
    public Boolean deleteById(ID id) {
        baseRepository.deleteById(id);
        return true;
    }

    @Override
    public void delete(T entity) {
        baseRepository.delete(entity);
    }

    @Override
    public void deleteAll(List<T> entity) {
        baseRepository.deleteAll(entity);
    }
}
