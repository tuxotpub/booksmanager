package org.tuxotpub.booksmanager.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.*;
import org.hibernate.annotations.NaturalId;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "authors") @ToString(exclude = "authors")//todo prüfen
@EntityListeners({GenericListener.class})
@Entity @Inheritance(strategy = InheritanceType.JOINED)
//@Cacheable //@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "entityCache")
@NamedEntityGraphs({@NamedEntityGraph(name = "parchmentAuthorsGraph", attributeNodes = {@NamedAttributeNode("authors")})})
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Book.class, name = "book"),
        @JsonSubTypes.Type(value = Magazine.class, name = "magazine")
})
public abstract class Parchment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId(mutable = true)
    private String isbn;

    @Column(length = 255)
    private String title;

    //@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @ManyToMany(cascade = {CascadeType.PERSIST})//, CascadeType.MERGE})
    @JoinTable(name = "parchment_author", joinColumns = @JoinColumn(name = "parchment_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    @JsonIgnoreProperties("parchments")
    private Set<Author> authors = new HashSet<>();

    @Transient
    private final String type = this.getClass().getSimpleName();

    public Parchment(String isbn, String title){
        this.isbn = isbn;
        this.title = title;
    }

    public Parchment addAuthor(Author author){
        author.getParchments().add(this);
        authors.add(author);
        return this;
    }

    public String getType(){
        return type;
    }
}
