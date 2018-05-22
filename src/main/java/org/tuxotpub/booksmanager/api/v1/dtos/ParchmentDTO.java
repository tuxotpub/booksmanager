package org.tuxotpub.booksmanager.api.v1.dtos;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.tuxotpub.booksmanager.entities.Author;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by tuxsamo.
 */
@Data @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(exclude = "authors") @ToString(exclude = "authors")
public abstract class ParchmentDTO implements Serializable {
    private Long id;
    private String isbn;
    private String title;
    @JsonIgnoreProperties("parchments")
    private Set<Author> authors = new HashSet<>();
    private String url;
}
