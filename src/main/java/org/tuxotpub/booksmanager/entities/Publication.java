package org.tuxotpub.booksmanager.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.NaturalId;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(exclude = "authors") @ToString(exclude = "authors")
@Entity @Audited @Builder
public class Publication extends BaseEntity<Long>  {

    public Publication(Long entityId){
        this.entityId = entityId;
    }

    @NaturalId(mutable = true)
    private String isbn;

    @Column(length = 255)
    private String title;

    private String description;

    @Column(name = "release_date")
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate releaseDate;

    private String type;

    @ManyToMany(cascade = {CascadeType.PERSIST})
    @JoinTable(name = "publication_author", joinColumns = @JoinColumn(name = "publication_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<Author> authors = new HashSet<>();
}
