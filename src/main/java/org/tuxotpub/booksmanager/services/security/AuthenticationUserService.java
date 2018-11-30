package org.tuxotpub.booksmanager.services.security;

import org.springframework.beans.factory.annotation.Autowired;
/*import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;*/
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tuxotpub.booksmanager.entities.security.AuthenticatedUser;
import org.tuxotpub.booksmanager.entities.security.User;
import org.tuxotpub.booksmanager.repositories.security.UserRepository;

/**
 * Created by tuxsamo.
 */

@Service
@Transactional(readOnly = true)
public class AuthenticationUserService{ /* implements UserDetailsService {

    private UserRepository userRepository;

    public AuthenticationUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("The user " + username + " does not exist");
        }
        return new AuthenticatedUser(user);
    }*/
}