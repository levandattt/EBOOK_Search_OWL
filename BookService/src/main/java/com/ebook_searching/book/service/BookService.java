package com.ebook_searching.book.service;

import com.ebook_searching.book.model.Book;
import org.ebook_searching.proto.Event;

import java.util.List;

public interface BookService {
    List<Book> findAll();
    void addBook(Event.AddBookEvent book);
    void updateBook(Event.AddBookEvent book);
}

