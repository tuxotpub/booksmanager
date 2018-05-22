package org.tuxotpub.booksmanager.entities;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.persistence.*;

/**
 * Created by tuxsamo.
 */
@Slf4j
@Component
public class GenericListener {
    @PrePersist
    public void ObjectPrePersist(Object o) {
        log.debug("Listening Object Pre Persist {}: " + o.getClass().getSimpleName());
    }

    @PostPersist
    public void ObjectPostPersist(Object o) {
        log.debug("Listening Object Post Persist {}: " + o.getClass().getSimpleName());
    }

    @PostLoad
    public void ObjectPostLoad(Object o) {
    log.debug("Listening Object Post Load {}: " + o.getClass().getSimpleName());
    }

    @PreUpdate
    public void ObjectPreUpdate(Object o) {
        log.debug("Listening Object Pre Update {}: " + o.getClass().getSimpleName());
    }

    @PostUpdate
    public void ObjectPostUpdate(Object o) {
        log.debug("Listening Object Post Update {}: " + o.getClass().getSimpleName());
    }

    @PreRemove
    public void ObjectPreRemove(Object o) {
        log.debug("Listening Object Pre Remove {}: " + o.getClass().getSimpleName());
    }

    @PostRemove
    public void ObjectPostRemove(Object o) {
        log.debug("Listening Object Post Remove {}: " + o.getClass().getSimpleName());
    }
}
