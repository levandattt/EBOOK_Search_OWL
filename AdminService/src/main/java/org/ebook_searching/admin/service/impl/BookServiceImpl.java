package org.ebook_searching.admin.service.impl;

import org.ebook_searching.admin.dto.BookDetail;
import org.ebook_searching.admin.model.Genre;
import org.ebook_searching.admin.repository.GenreRepository;
import org.ebook_searching.common.exception.InvalidFieldsException;
import org.ebook_searching.common.exception.RecordNotFoundException;
import org.ebook_searching.admin.mapper.BookMapper;
import org.ebook_searching.admin.mapper.EventMapper;
import org.ebook_searching.admin.model.Author;
import org.ebook_searching.admin.model.Book;
import org.ebook_searching.admin.payload.request.AddBookRequest;
import org.ebook_searching.admin.payload.request.UpdateBookRequest;
import org.ebook_searching.admin.payload.response.AddBookResponse;
import org.ebook_searching.admin.payload.response.DeleteBookResponse;
import org.ebook_searching.admin.payload.response.GetBookResponse;
import org.ebook_searching.admin.payload.response.UpdateBookResponse;
import org.ebook_searching.admin.repository.AuthorRepository;
import org.ebook_searching.admin.repository.BookRepository;
import org.ebook_searching.admin.service.BookService;
import org.ebook_searching.common.exception.InvalidFieldsException;
import org.ebook_searching.common.exception.RecordNotFoundException;
import org.ebook_searching.proto.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class BookServiceImpl implements BookService {
    @Value(value = "${spring.kafka.consumer.add-book-topic}")
    private String addBookTopic;

    @Value(value = "${spring.kafka.consumer.update-book-topic}")
    private String updateBookTopic;

    @Value(value = "${spring.kafka.consumer.delete-book-topic}")
    private String deleteBookTopic;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private KafkaTemplate<String, Event.AddBookEvent> addBookEventPublisher;

    @Autowired
    private EventMapper eventMapper;
    @Autowired
    private GenreRepository genreRepository;

    @Override
    public AddBookResponse addBook(AddBookRequest request) {
        // Convert the AddBookRequest to a Book entity
        Book book = bookMapper.toBook(request);

        book.setId(null);
        setAuthors(book, request.getAuthorIds());
        setGenres(book, request.getGenresIds());

        bookRepository.save(book);

        addBookEventPublisher.send(addBookTopic,
                eventMapper.toBookEvent(book));

        // Convert the saved Book entity to AddBookResponse
        return bookMapper.toAddBookResponse(book);
    }

    @Override
    public UpdateBookResponse updateBook(UpdateBookRequest request) {
        // validate
        Book existingBook = findById(request.getId());

        String oldTitle = existingBook.getTitle();
        // update all the field
        bookMapper.updateBookFromRequest(existingBook, request);
        setAuthors(existingBook, request.getAuthorIds());
        setGenres(existingBook, request.getGenresIds());
        bookRepository.save(existingBook);

        addBookEventPublisher.send(updateBookTopic,
                eventMapper.toUpdateBookEvent(existingBook, oldTitle));

        return bookMapper.toUpdateBookResponse(existingBook);
    }

    @Override
    public DeleteBookResponse deleteBook(Long id) {
        Book bookSelected = findById(id);
        addBookEventPublisher.send(deleteBookTopic,
                eventMapper.toBookEvent(bookSelected));

        bookRepository.deleteById(bookSelected.getId());
        DeleteBookResponse response = new DeleteBookResponse();

        response.setId(id);
        return response;
    }

    @Override
    public Book findById(Long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isEmpty()) {
            throw new RecordNotFoundException("Không tồn tại cuốn sách này");
        } else return optionalBook.get();
    }

    private void setAuthors(Book book, Set<Long> authorIds){
        Set<Author> attachedAuthors = authorRepository.findByIdIn(new HashSet<>(authorIds));
        if (attachedAuthors.isEmpty()) {
            throw InvalidFieldsException.fromFieldError("authorIds", "AuthorIds invalid");
        }

        // Attach authors to the book and update both sides of the relationship
        book.updateAuthors(attachedAuthors);
    }

    private void setGenres(Book book, Set<Long> genreIds){
        Set<Genre> attachedGenres = genreRepository.findByIdIn(new HashSet<>(genreIds));
        if (attachedGenres.isEmpty()) {
            throw InvalidFieldsException.fromFieldError("genreIds", "GenreIds invalid");
        }
    }

    @Override
    public List<GetBookResponse> getAllBooks() {
        return bookRepository.findAll().stream().map(book -> bookMapper.toGetBookResponse(book)).toList();
    }

    @Override
    public BookDetail findBookDetailById(Long id) {
        Book book = findById(id);
        return bookMapper.toBookDetail(book);
    }
}
