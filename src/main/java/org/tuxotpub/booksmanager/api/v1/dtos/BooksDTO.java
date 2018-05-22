package org.tuxotpub.booksmanager.api.v1.dtos;

import lombok.*;

import java.util.List;

/**
 * Created by tuxsamo.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BooksDTO {
    private List<BookDTO> bookDTOS;
}
