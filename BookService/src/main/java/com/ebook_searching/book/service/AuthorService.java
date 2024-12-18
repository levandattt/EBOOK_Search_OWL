package com.ebook_searching.book.service;

import com.ebook_searching.book.dto.AuthorDetail;
import org.ebook_searching.proto.Event;

import java.util.List;

public interface AuthorService {
    void addAuthor(Event.Author author);
    void updateAuthor(Event.Author book);
    AuthorDetail findAuthorDetailById(Long id);
    void deleteAuthor(String uuid);
}
