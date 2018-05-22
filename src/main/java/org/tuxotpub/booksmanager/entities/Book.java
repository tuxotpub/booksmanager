package org.tuxotpub.booksmanager.entities;

import lombok.*;
import javax.persistence.Entity;
import java.util.HashSet;

@Data
@NoArgsConstructor @AllArgsConstructor
@Entity
public class Book extends Parchment {

    private String description;

    public Book(Long id, String isbn, String title, String description) {
        super(id, isbn, title, new HashSet<Author>());
        this.description = description;
    }
}
