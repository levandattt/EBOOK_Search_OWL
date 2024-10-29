package org.ebook_searching.admin.service.impl;

import org.ebook_searching.admin.exception.InvalidFieldsException;
import org.ebook_searching.admin.exception.RecordNotFoundException;
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

    @Override
    public AddBookResponse addBook(AddBookRequest request) {
        // Convert the AddBookRequest to a Book entity
        Book book = bookMapper.toBook(request);

        book.setId(null);
        setAuthors(book, request.getAuthorIds());
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

        // update all the field
        bookMapper.updateBookFromRequest(existingBook, request);
        setAuthors(existingBook, request.getAuthorIds());
        bookRepository.save(existingBook);

        // TODO: publish event

        return bookMapper.toUpdateBookResponse(existingBook);
    }

    @Override
    public DeleteBookResponse deleteBook(Long id) {
        Book bookSelected = findById(id);
        bookRepository.deleteById(bookSelected.getId());
        DeleteBookResponse response = new DeleteBookResponse();
        response.setId(id);
        return response;
    }

    @Override
    public Book findById(Long id) {
        Optional<Book> optionalCustomer = bookRepository.findById(id);
        if (optionalCustomer.isEmpty()) {
            throw new RecordNotFoundException("Không tồn tại cuốn sách này");
        } else return optionalCustomer.get();
    }

    private void setAuthors(Book book, Set<Long> authorIds){
        Set<Author> attachedAuthors = authorRepository.findByIdIn(new HashSet<>(authorIds));
        if (attachedAuthors.isEmpty()) {
            throw InvalidFieldsException.fromFieldError("authorIds", "AuthorIds invalid");
        }

        // Attach authors to the book and update both sides of the relationship
        book.updateAuthors(attachedAuthors);
    }

    @Override
    public List<GetBookResponse> getAllBooks() {
        return bookRepository.findAll().stream().map(book -> bookMapper.toGetBookResponse(book)).toList();
    }
}
