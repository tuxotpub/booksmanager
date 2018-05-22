package org.tuxotpub.booksmanager.api.v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.tuxotpub.booksmanager.api.v1.dtos.BookDTO;
import org.tuxotpub.booksmanager.entities.Book;

/**
 * Created by tuxsamo.
 */

@Mapper(componentModel = "spring")
public interface BookMapper{

    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    BookDTO getBookDTO(Book book);

    Book getBook(BookDTO bookDTO);
}
