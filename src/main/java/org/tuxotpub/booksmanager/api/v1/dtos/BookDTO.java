package org.tuxotpub.booksmanager.api.v1.dtos;

import lombok.*;
import org.tuxotpub.booksmanager.entities.Author;
import java.util.HashSet;

/**
 * Created by tuxsamo.
 */

@Data @NoArgsConstructor @AllArgsConstructor
@ToString(callSuper = true)
public class BookDTO extends ParchmentDTO {
    private String description;

    public BookDTO(long id, String isbn, String title, String description, HashSet<Author> authors, String url) {
        super( id, isbn, title, authors, url );
        this.description = description;
    }
}
