package org.tuxotpub.booksmanager.api.v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.tuxotpub.booksmanager.api.v1.dtos.AuthorDTO;
import org.tuxotpub.booksmanager.entities.Author;

/**
 * Created by tuxsamo.
 */

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

    AuthorDTO getAuthorDTO(Author author);

    Author getAuthor(AuthorDTO authorDTO);
}