package org.tuxotpub.booksmanager.entities;

import lombok.*;

import javax.persistence.Entity;
import java.time.LocalDate;
import java.util.HashSet;

@Data
@NoArgsConstructor @AllArgsConstructor
@Entity
public class Magazine extends Parchment {

    private LocalDate releaseDate;

    public Magazine(Long id, String isbn, String title, LocalDate releaseDate) {
        super(id, isbn, title, new HashSet<Author>());
        this.releaseDate = releaseDate;
    }
}