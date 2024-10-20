package org.ebook_searching.admin.service.impl;

import org.ebook_searching.admin.exception.InvalidFieldsException;
import org.ebook_searching.admin.exception.RecordNotFoundException;
import org.ebook_searching.admin.mapper.BookMapper;
import org.ebook_searching.admin.model.Book;
import org.ebook_searching.admin.payload.request.AddBookRequest;
import org.ebook_searching.admin.payload.request.UpdateBookRequest;
import org.ebook_searching.admin.payload.response.AddBookResponse;
import org.ebook_searching.admin.payload.response.DeleteBookResponse;
import org.ebook_searching.admin.payload.response.GetBookResponse;
import org.ebook_searching.admin.payload.response.UpdateBookResponse;
import org.ebook_searching.admin.repository.BookRepository;
import org.ebook_searching.admin.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private BookRepository bookRepository;

    @Override
    public AddBookResponse addBook(AddBookRequest request) {
        // Convert the AddBookRequest to a Book entity
        Book book = bookMapper.toBook(request);

        // Save the request entity
        bookRepository.save(book);

        // Convert the saved Book entity to AddBookResponse
        return bookMapper.toAddBookResponse(book);
    }

    @Override
    public UpdateBookResponse updateBook(UpdateBookRequest request) {
        if (request.getId() == null) {
            throw InvalidFieldsException.fromFieldError("id", "Id là trường bắt buộc");
        }

        Book existingBook = findById(request.getId());
        if (request.getTitle() != null) {
            existingBook.setTitle(request.getTitle());
        }

        if (request.getGenre() != null) {
            existingBook.setGenre(request.getGenre());
        }

        if (request.getPublishedAt() != null) {
            existingBook.setPublishedAt(request.getPublishedAt());
        }

        if (request.getPublisher() != null) {
            existingBook.setPublisher(request.getTitle());
        }

        if (request.getLanguage() != null) {
            existingBook.setLanguage(request.getLanguage());
        }

        if (request.getAvgRatings() != null) {
            existingBook.setAvgRatings(request.getAvgRatings());
        }

        if (request.getRatingsCount() != null) {
            existingBook.setRatingsCount(request.getRatingsCount());
        }

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

    @Override
    public List<GetBookResponse> getAllBooks() {
        return bookRepository.findAll().stream().map(book -> bookMapper.toGetBookResponse(book)).toList();
    }
}
