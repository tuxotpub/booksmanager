package org.tuxotpub.booksmanager.repositories;

import org.springframework.data.repository.NoRepositoryBean;

/**
 * Created by tuxsamo.
 */

@NoRepositoryBean
public interface EntityRepository {

    //<T> Optional<T> findViaDynamicGraphById(Long id, Class<T> clazz, String[] graphAttributes);
}
