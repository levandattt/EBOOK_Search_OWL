package org.ebook_searching.admin.service;

import org.ebook_searching.admin.model.Book;
import org.ebook_searching.admin.payload.request.AddBookRequest;
import org.ebook_searching.admin.payload.response.AddBookResponse;

public interface BookService {
    AddBookResponse addBook(AddBookRequest book);
}
