package org.tuxotpub.booksmanager.entities;

import lombok.*;
import org.hibernate.annotations.NaturalId;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "Publications")
@ToString(exclude = "Publications")
@Builder
@Entity @Audited
@NamedEntityGraphs({
        @NamedEntityGraph(name = "authorPublicationsGraph",
                attributeNodes = {@NamedAttributeNode("publications")}),
})
public class Author extends BaseEntity<Long> {

    public Author(Long id){
        super(id);
    }

    @Column(length = 255)
    private String name;

    @Column(length = 255)
    private String surname;

    @Column(length = 255) @NaturalId
    private String email;

    @ManyToMany(mappedBy = "authors", cascade = {CascadeType.PERSIST})//, CascadeType.MERGE})
    //@JsonIgnoreProperties("authors")
    private Set<Publication> publications = new HashSet<>();

    public Author addPublication(Publication publication){
        publication.getAuthors().add(this);
        publications.add(publication);
        return this;
    }
}
