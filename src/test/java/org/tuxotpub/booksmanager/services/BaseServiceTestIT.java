package org.tuxotpub.booksmanager.services;

import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.tuxotpub.booksmanager.bootstrap.BootstrapData;
import org.tuxotpub.booksmanager.repositories.authors.AuthorRepository;
import org.tuxotpub.booksmanager.repositories.parchments.BookRepository;
import org.tuxotpub.booksmanager.repositories.parchments.MagazineRepository;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by tuxsamo.
 */

@RunWith(SpringRunner.class)
@DataJpaTest
@Slf4j
public abstract class BaseServiceTestIT {

    private static AtomicLong elapsedTime = new AtomicLong();

    @Autowired
    protected AuthorRepository authorRepository;

    @Autowired
    protected BookRepository bookRepository;

    @Autowired
    protected MagazineRepository magazineRepository;

    @BeforeClass
    public static void initBaseServiceTest(){
        elapsedTime.set(System.currentTimeMillis());
    }

    public void bootStrapData() throws Exception {
        BootstrapData bootstrap = new BootstrapData(authorRepository, bookRepository, magazineRepository);
        bootstrap.run();
    }

    @AfterClass
    public static void afterBaseServiceTest(){
        elapsedTime.set( System.currentTimeMillis() - elapsedTime.get() );
        log.debug("Elapsed time: {}", elapsedTime);
    }

}
