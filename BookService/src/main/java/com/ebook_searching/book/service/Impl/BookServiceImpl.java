package com.ebook_searching.book.service.Impl;

import com.ebook_searching.book.mapper.BookMapper;
import com.ebook_searching.book.model.Author;
import com.ebook_searching.book.repository.AuthorRepository;
import com.ebook_searching.book.repository.BookRepository;
import com.ebook_searching.book.service.BookService;
import com.ebook_searching.book.model.Book;
import org.ebook_searching.proto.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookMapper bookMapper;

    @Override
    public List<Book> findAll() {
        return List.of();
    }

    @Override
    public void addBook(Event.AddBookEvent bookEvent) {
        // Convert the AddBookRequest to a Book entity
        Book book = bookMapper.toBook(bookEvent);

        book.setId(null);
        setAuthors(book, bookEvent.getAuthorsList());
        bookRepository.save(book);
    }

    @Override
    public void updateBook(Event.AddBookEvent book) {
        // validate
        Optional<Book> optionalCustomer = bookRepository.findById(book.getId());
        if (optionalCustomer.isEmpty()) {
            return ;
        }
        Book existingBook = optionalCustomer.get();

        // update all the field
        bookMapper.updateBookFromRequest(existingBook, book);
        setAuthors(existingBook, book.getAuthorsList());
        bookRepository.save(existingBook);
    }

    private void setAuthors(Book book, List<Event.Author> authors) {
        Set<Long> authorIds = authors.stream().map(Event.Author::getId).collect(Collectors.toSet());

        Set<Author> attachedAuthors = authorRepository.findByIdIn(authorIds);
        if (attachedAuthors.isEmpty()) {
            return ;
        }

        // Attach authors to the book and update both sides of the relationship
        book.updateAuthors(attachedAuthors);
    }
}
