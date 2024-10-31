package com.ebook_searching.book.service;

import org.ebook_searching.proto.Event;

import java.util.List;

public interface AuthorService {
    void addAuthor(Event.Author author);
    void updateAuthor(Event.Author book);
}
