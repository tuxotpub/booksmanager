package org.tuxotpub.booksmanager;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by tuxsamo.
 */

@Slf4j
public abstract class BaseTest {
    private static AtomicLong elapsedTime = new AtomicLong();

    @BeforeAll
    public static void initBaseServiceTest(){
        elapsedTime.set(System.currentTimeMillis());
    }

    @AfterAll
    public static void afterBaseServiceTest(){
        elapsedTime.set( System.currentTimeMillis() - elapsedTime.get() );
        log.debug("Elapsed time: {}", elapsedTime);
    }
}
