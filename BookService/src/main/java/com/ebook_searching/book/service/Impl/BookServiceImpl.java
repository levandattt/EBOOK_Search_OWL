package com.ebook_searching.book.service.Impl;

import com.ebook_searching.book.mapper.BookMapper;
import com.ebook_searching.book.model.OrderCriteria;
import com.ebook_searching.book.model.Pagination;
import com.ebook_searching.book.model.author.Author;
import com.ebook_searching.book.model.book.BookCriteria;
import com.ebook_searching.book.payload.ListBooksResponse;
import com.ebook_searching.book.repository.AuthorRepository;
import com.ebook_searching.book.repository.BookRepository;
import com.ebook_searching.book.service.BookService;
import com.ebook_searching.book.model.book.Book;
import org.ebook_searching.proto.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public ListBooksResponse searchBooks(BookCriteria bookCriteria, Pagination pagination, OrderCriteria orderCriteria) {
        // Prepare Pageable object using pagination and sorting
        Pageable pageable = PageRequest.of(
                pagination.getOffset(),
                pagination.getLimit(),
                Sort.by(Sort.Direction.fromString(orderCriteria.getOrderDirection()), orderCriteria.getOrderBy())
        );

        // Perform the search based on bookCriteria
        Page<Book> bookPage;
        if (bookCriteria.getGenreSlug() != null && !bookCriteria.getGenreSlug().isEmpty()) {
            bookPage = bookRepository.findByGenresContaining(bookCriteria.getGenreSlug().replaceAll("-", " "), pageable);
        } else {
            bookPage = bookRepository.findAll(pageable);
        }

        // Prepare ListBooksResponse
        ListBooksResponse response = ListBooksResponse.builder().
                numPages(bookPage.getTotalPages()).
                offset(pagination.getOffset()).
                limit(pagination.getLimit()).
                totalItems((int) bookPage.getTotalElements()).build();

        // Convert to response DTO
        List<Book> books = bookPage.getContent();
        if (books.size() == 1) {
            response.setBookDetail(bookMapper.toBookDetail(books.get(0)));
        } else {
            response.setData(books.stream().map(bookMapper::toBaseBook).collect(Collectors.toList()));
        }

        return response;
    }

    @Override
    @Transactional
    public void addBook(Event.AddBookEvent bookEvent) {
        // Convert the AddBookRequest to a Book entity
        Book book = bookMapper.toBook(bookEvent);

        book.setId(null);
        setAuthors(book, bookEvent.getAuthorsList());
        bookRepository.save(book);
    }

    @Transactional
    @Override
    public void updateBook(Event.AddBookEvent book) {
        // validate
        Optional<Book> optionalCustomer = bookRepository.findByUuid(book.getUuid());
        if (optionalCustomer.isEmpty()) {
            return;
        }
        Book existingBook = optionalCustomer.get();

        // update all the field
        bookMapper.updateBookFromRequest(existingBook, book);
        setAuthors(existingBook, book.getAuthorsList());
        bookRepository.save(existingBook);
    }

    private void setAuthors(Book book, List<Event.Author> authors) {
        Set<String> authorUUIDs = authors.stream().map(Event.Author::getUuid).collect(Collectors.toSet());

        Set<Author> attachedAuthors = authorRepository.findByUuidIn(authorUUIDs);
        if (attachedAuthors.isEmpty()) {
            return;
        }

        // Attach authors to the book and update both sides of the relationship
        book.updateAuthors(attachedAuthors);
    }
}
