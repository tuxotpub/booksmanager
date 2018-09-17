package org.tuxotpub.booksmanager.api.v1.dtos;

import lombok.*;
import org.tuxotpub.booksmanager.entities.Author;

import java.time.LocalDate;
import java.util.HashSet;

/**
 * Created by tuxsamo.
 */

@Data @NoArgsConstructor @AllArgsConstructor
@ToString(callSuper = true)
public class MagazineDTO extends ParchmentDTO {
    private LocalDate releaseDate;

    public MagazineDTO(long id, String isbn, String title, LocalDate releaseDate, HashSet<Author> authors, String url) {
        super( id, isbn, title, authors, url );
        this.releaseDate = releaseDate;
    }
}
