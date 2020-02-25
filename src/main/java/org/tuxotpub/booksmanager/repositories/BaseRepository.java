package org.tuxotpub.booksmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tuxotpub.booksmanager.entities.BaseEntity;

public interface BaseRepository<T extends BaseEntity, ID> extends JpaRepository<T, ID> {
}
