package com.ebook_searching.ontology.service;

import com.ebook_searching.ontology.model.Book;
import com.ebook_searching.ontology.payload.QueryRes;

import java.util.List;

public interface BookService {
//    List<Book> searchBooksByAuthor(String authorName);
    QueryRes<List<Book>> searchBooksByAuthor(String authorName);
}

