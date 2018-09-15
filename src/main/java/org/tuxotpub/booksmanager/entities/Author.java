package org.tuxotpub.booksmanager.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "parchments")
@ToString(exclude = "parchments")
@EntityListeners({GenericListener.class})
@Entity
//@Cacheable @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "entityCache")
@NamedEntityGraphs({
        @NamedEntityGraph(name = "authorParchmentsGraph",
                attributeNodes = {@NamedAttributeNode("parchments")})})
public class Author {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String surname;

    private String email;

    @ManyToMany(mappedBy = "authors", cascade = {CascadeType.PERSIST})//, CascadeType.MERGE})
    @JsonIgnoreProperties("authors")
    private Set<Parchment> parchments = new HashSet<>();

    public Author(String name, String surname, String email){
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    public Author addParchment(Parchment parchment){
        parchment.getAuthors().add(this);
        parchments.add(parchment);
        return this;
    }
}
