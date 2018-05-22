package org.tuxotpub.booksmanager.mapper.v1;

import org.junit.Test;
import org.tuxotpub.booksmanager.api.v1.dtos.AuthorDTO;
import org.tuxotpub.booksmanager.api.v1.mapper.AuthorMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.tuxotpub.booksmanager.TestHelper.*;
import static org.tuxotpub.booksmanager.controllers.v1.AuthorController.*;

public class AuthorMapperTest {

    private final AuthorMapper authorMapper = AuthorMapper.INSTANCE;

    @Test
    public void authorToAuthorDTO() throws Exception {
        AuthorDTO authorDTOTest = authorMapper.getAuthorDTO(AUTHOR1);
        authorDTOTest.setUrl( FINDBYID_PATH + AUTHOR1.getId() );
        assertThat(authorDTOTest).isEqualToComparingFieldByField(AUTHORDTO1);
    }

}