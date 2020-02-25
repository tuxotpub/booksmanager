package org.tuxotpub.booksmanager.controllers;

import org.springframework.web.bind.annotation.*;
import org.tuxotpub.booksmanager.CrudInterface;
import org.tuxotpub.booksmanager.entities.BaseEntity;
import org.tuxotpub.booksmanager.services.BaseService;

import javax.validation.Valid;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseController<T extends BaseEntity, ID> implements CrudInterface<T, ID> {

    private final Class<T> entityClass;
    private final BaseService<T, ID> baseService;

    public BaseController(BaseService<T, ID> baseService) {
        this.baseService = baseService;
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
    }

    @PostMapping
    public T save(@Valid @RequestBody T entity) {
        return baseService.save(entity);
    }

    @PutMapping
    public T update(@Valid @RequestBody T entity) {
        return baseService.update(entity);
    }

    @Override
    public List<T> saveAll(List<T> entities) {
        return new ArrayList<>();
    }

    @GetMapping("{id}")
    public T findById(@PathVariable ID id) {
        return baseService.findById(id);
    }

    @Override
    public boolean existsById(ID is) {
        return false;
    }

    @GetMapping
    public List<T> findAll() {
        return baseService.findAll();
    }

    @Override
    public List<T> findAllById(List<ID> ids) {
        return new ArrayList<>();
    }

    @Override
    public long count() {
        return 0;
    }

    @DeleteMapping("{id}")
    public Boolean deleteById(@PathVariable ID id) {
        return baseService.deleteById(id);
    }

    @Override
    public void delete(T entity) {
    }

    @Override
    public void deleteAll(List<T> entity) {

    }

    /*protected List<Link> getLinks(T entity) {
        return null;
        //todo upgrade to 2.2.4 Arrays.asList(
                //linkTo(this.getClass()).slash(entity.getEntityId()).withSelfRel(),
                //linkTo( methodOn( this.getClass() ).findAll() ).withSelfRel() );
    }*/
}

