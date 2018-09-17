package org.tuxotpub.booksmanager.api.v1.dtos;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.validator.constraints.ISBN;
import org.tuxotpub.booksmanager.entities.Author;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by tuxsamo.
 */
@Data @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(exclude = "authors") @ToString(exclude = "authors")
public abstract class ParchmentDTO {
    private Long id;

    //@ISBN
    private String isbn;

    @NotBlank(message = "Title name musn't be blank")
    @Size(min = 2, max = 255, message = "Title size must be between 2 and 255")
    private String title;

    @JsonIgnoreProperties("parchments")
    private Set<Author> authors = new HashSet<>();

    private String url;
}
