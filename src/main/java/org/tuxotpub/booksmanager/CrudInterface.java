package org.tuxotpub.booksmanager;

import java.util.List;

public interface CrudInterface<T, ID> {

    T save(T entity);

    T update(T entity);

    List<T> saveAll(List<T> entities);

    T findById(ID id);

    boolean existsById(ID is);

    List<T> findAll();

    List<T> findAllById(List<ID> ids);

    long count();

    Boolean deleteById(ID entity);

    void delete(T entity);

    void deleteAll(List<T> entity);
}
