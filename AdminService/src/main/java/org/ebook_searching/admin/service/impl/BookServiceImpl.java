package org.ebook_searching.admin.service.impl;

import org.ebook_searching.admin.mapper.BookMapper;
import org.ebook_searching.admin.model.Book;
import org.ebook_searching.admin.payload.request.AddBookRequest;
import org.ebook_searching.admin.payload.request.DeleteBookRequest;
import org.ebook_searching.admin.payload.request.UpdateBookRequest;
import org.ebook_searching.admin.payload.response.AddBookResponse;
import org.ebook_searching.admin.payload.response.DeleteBookResponse;
import org.ebook_searching.admin.payload.response.UpdateBookResponse;
import org.ebook_searching.admin.repository.BookRepository;
import org.ebook_searching.admin.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

//        Book book = new Book();
//        if (request.getTitle() != null) {
//            book.setTitle(request.getTitle());
//        }
//
//        if (request.getLanguage() != null) {
//            book.setLanguage(request.getLanguage());
//        }
//
//        book.setLanguage(request.getLanguage());
//        book.setGenre(request.getGenre());
//        book.setPublisher(request.getPublisher());
//        book.setAvgRatings(request.getAvgRatings());
//        book.setRatingsCount(request.getRatingsCount());
//        book.setPublishedAt(request.getPublishedAt());

        // Save the request entity
        Book savedBook = bookRepository.save(book);

        // Convert the saved Book entity to AddBookResponse
        return null;
    }

    @Override
    public UpdateBookResponse updateBook(UpdateBookRequest book) {


        return null;
    }

    @Override
    public DeleteBookResponse deleteBook(DeleteBookRequest deleteBookRequest) {
        return null;
    }

}
