package org.tuxotpub.booksmanager.entities.security;

/**
 * Created by tuxsamo.
 */

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
public class User {

    @Id
    @GeneratedValue()
    private long id;

    @NotEmpty(message = "username is required")
    @Column(unique = true)
    private String username;

    @NotEmpty(message = "password is required")
    private String password;

    protected User() {}

    public User(String userName, String password) {
        this.username = userName;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
