package org.tuxotpub.booksmanager.repositories.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tuxotpub.booksmanager.entities.security.User;

/**
 * Created by tuxsamo.
 */

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}