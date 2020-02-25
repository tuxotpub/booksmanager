package org.tuxotpub.booksmanager.repositories;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.transaction.TestTransaction;
import org.tuxotpub.booksmanager.BaseTest;
import org.tuxotpub.booksmanager.entities.BaseEntity;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;
import static org.tuxotpub.booksmanager.TestHelper.*;

@AutoConfigureTestDatabase(replace = NONE)
@DataJpaTest
//@Execution(ExecutionMode.CONCURRENT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public abstract class AbstractRepositoriesTestIT<T extends BaseEntity<ID>, ID> extends BaseTest {

    @Autowired
    public BaseRepository<T, ID> baseRepository;

    protected Class<T> clazz;

    public AbstractRepositoriesTestIT(){}

    @Test @Disabled
    public void testTransaction() {
        assertFalse(TestTransaction.isFlaggedForRollback());
    }

    @Test @Order(CREATE)
    public void create() {
        T t1 = getEntity();
        t1.setEntityId(null);
        baseRepository.saveAndFlush( t1 );
        assertThat(t1.getEntityId()).isNotNull();
    }

    @Test @Order(UPDATE)
    public void update() {
        T t1 = getEntity();
        updateEntity(t1);
        baseRepository.saveAndFlush(t1);
        assertThat(t1.getUpdatedOn()).isAfter(getEntity().getUpdatedOn());
    }

    @Test @Order(AFTER_UPDATE)
    public void get() {
        T t1 = baseRepository.findById((ID) getEntity().getEntityId() ).get();
        assertThat(t1.getEntityId()).isEqualTo(getEntity().getEntityId());
    }

    @Test @Order(EXIST)
    public void exits() {
        assertThat( baseRepository.existsById((ID) getEntity().getEntityId()) ).isEqualTo(true);
    }

    @Test @Order(EXIST)
    public void getAll(){
        List<T> list = baseRepository.findAll();
        assertFalse(list.isEmpty());
    }

    @Test @Order(DELETE)
    public void delete() {
        baseRepository.deleteById((ID) getEntity().getEntityId() );
    }

    @Test @Order(NOT_EXIST)
    public void exits_not_found() {
        assertThat( baseRepository.existsById((ID) getEntity().getEntityId() ) ).isFalse();
    }

    @Test @Order(NOT_EXIST)
    public void get_not_found() {
        Assertions.assertThrows( NoSuchElementException.class, ()->baseRepository.findById( (ID) getEntity().getEntityId() ).get() );
    }

    protected abstract T getEntity();
    protected abstract void updateEntity(T t);
}