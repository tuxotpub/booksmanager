package org.tuxotpub.booksmanager.services.parchments;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.tuxotpub.booksmanager.api.v1.dtos.BookDTO;
import org.tuxotpub.booksmanager.api.v1.mapper.BookMapper;
import org.tuxotpub.booksmanager.entities.Book;
import org.tuxotpub.booksmanager.repositories.parchments.BookRepository;
import org.tuxotpub.booksmanager.services.ParchmentServiceImpl;
import java.util.List;
import java.util.stream.Collectors;

import static org.tuxotpub.booksmanager.controllers.v1.BookController.FINDBYID_PATH;

/**
 * Created by tuxsamo.
 */
@Service
public class BookServiceImpl extends ParchmentServiceImpl<BookDTO, Book> implements BookService {

    private final BookMapper bookMapper;
    private final BookRepository bookRepository;

    public BookServiceImpl(BookMapper bookMapper, BookRepository bookRepository) {
        super(bookRepository);
        this.bookMapper = bookMapper;
        this.bookRepository = bookRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDTO> findByDescription(String description) {
        return bookRepository.findBooksByDescription(description).stream()
                .map(bookMapper::getBookDTO).collect(Collectors.toList());
    }

    @Override
    protected Book mergeParchment(Book source, Book destination) {
        if ( !StringUtils.isEmpty( source.getDescription() ) ) destination.setDescription( source.getDescription() );
        return super.mergeParchment(source, destination);
    }

    @Override
    public String getPath() {
        return FINDBYID_PATH;
    }

    @Override
    protected BookDTO getDTO(Book book) {
        return  bookMapper.getBookDTO(book);
    }

    @Override
    protected Book getEntity(BookDTO bookTDO) {
        return bookMapper.getBook( bookTDO );
    }
}
