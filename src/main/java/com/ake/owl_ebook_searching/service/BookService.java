package com.ake.owl_ebook_searching.service;

import com.ake.owl_ebook_searching.model.Book;
import com.ake.owl_ebook_searching.payload.QueryRes;

import java.util.List;

public interface BookService {
//    List<Book> searchBooksByAuthor(String authorName);
    QueryRes<List<Book>> searchBooksByAuthor(String authorName);
}

