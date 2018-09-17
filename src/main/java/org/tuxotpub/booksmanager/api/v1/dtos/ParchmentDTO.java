package org.tuxotpub.booksmanager.api.v1.dtos;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.validator.constraints.ISBN;
import org.hibernate.validator.constraints.URL;
import org.tuxotpub.booksmanager.entities.Author;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by tuxsamo.
 */
@Data @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(exclude = "authors") @ToString(exclude = "authors")
public abstract class ParchmentDTO {
    private Long id;

    @ISBN
    private String isbn;

    @NotBlank @Column(length = 255)
    private String title;

    @JsonIgnoreProperties("parchments")
    private Set<Author> authors = new HashSet<>();

    @NotBlank @URL
    private String url;
}
