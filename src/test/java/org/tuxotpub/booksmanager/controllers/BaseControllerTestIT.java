package org.tuxotpub.booksmanager.controllers;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.tuxotpub.booksmanager.BaseTest;
import org.tuxotpub.booksmanager.entities.BaseEntity;

import java.util.ArrayList;
import java.util.List;

@Execution(ExecutionMode.CONCURRENT)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public abstract class BaseControllerTestIT<T extends BaseEntity<ID>, ID> extends BaseTest {

    protected static String BASE_PATH;

    protected List<T> entities = new ArrayList<>();

    @Autowired
    protected TestRestTemplate restTemplate;

    protected Class<T> clazz;

    @Test
    public void findAll() {
    }

    @Test
    public void deleteById() {
        restTemplate.delete(BASE_PATH + "/" + entities.get(0).getEntityId() );
    }


    protected void auditToBaseEntityFromResponse(T entity,T response){
        entity.setEntityId(response.getEntityId());
        entity.setCreatedBy(response.getCreatedBy());
        entity.setCreatedOn(response.getCreatedOn());
        entity.setUpdatedBy(response.getUpdatedBy());
        entity.setCreatedOn(response.getCreatedOn());
    }
}