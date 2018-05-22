package org.tuxotpub.booksmanager.services;

import org.tuxotpub.booksmanager.api.v1.dtos.ParchmentDTO;
import java.util.List;

/**
 * Created by tuxsamo.
 */

public interface ParchmentService<T extends ParchmentDTO>{

    List<T> getAll();

    T getById(Long id);

    T getOne(Long id);

    T getByIsbn(String isbn);

    T create(T parchmentDTO);

    T updateById(Long id, T perchmentDTO);

    void deleteById(Long id);
}

