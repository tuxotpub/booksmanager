package org.tuxotpub.booksmanager.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Audited
@MappedSuperclass
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "entityId")
public abstract class BaseEntity<ID> implements Serializable {

    public static final String SEQUENCE_GENERATOR = "default_generator";

    public BaseEntity(ID entityId){
        this.entityId = entityId;
    }

    @Id
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_GENERATOR)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    protected ID entityId;

    @CreationTimestamp
    @Column(name = "created_on", updatable = false)
    @JsonIgnore
    private LocalDateTime createdOn;

    @JsonIgnore
    @Column(name = "created_by", updatable = false)
    private String createdBy;

    @JsonIgnore
    @UpdateTimestamp
    @Column(name = "updated_on")
    private LocalDateTime updatedOn;

    @JsonIgnore
    @Column(name = "updated_by")
    private String updatedBy;
}
