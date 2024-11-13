package com.ebook_searching.book.service;

import com.ebook_searching.book.dto.BookDetail;
import com.ebook_searching.book.model.OrderCriteria;
import com.ebook_searching.book.model.Pagination;
import com.ebook_searching.book.model.book.Book;
import com.ebook_searching.book.model.book.BookCriteria;
import com.ebook_searching.book.payload.ListBooksResponse;
import org.ebook_searching.proto.Event;

import java.util.List;

public interface BookService {
    List<Book> findAll();
    ListBooksResponse searchBooks(BookCriteria bookCriteria, Pagination pagination, OrderCriteria orderCriteria);
    void addBook(Event.AddBookEvent book);
    void updateBook(Event.AddBookEvent book);
    BookDetail findBookDetailById(Long id);

    void deleteBook(String uuid);
}

