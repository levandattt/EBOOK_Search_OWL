package org.ebook_searching.admin.service;

import org.ebook_searching.admin.model.Book;
import org.ebook_searching.admin.payload.request.AddBookRequest;
import org.ebook_searching.admin.payload.request.DeleteBookRequest;
import org.ebook_searching.admin.payload.request.UpdateBookRequest;
import org.ebook_searching.admin.payload.response.AddBookResponse;
import org.ebook_searching.admin.payload.response.DeleteBookResponse;
import org.ebook_searching.admin.payload.response.UpdateBookResponse;

public interface BookService {
    AddBookResponse addBook(AddBookRequest book);
    UpdateBookResponse updateBook(UpdateBookRequest book);
    DeleteBookResponse deleteBook(DeleteBookRequest deleteBookRequest);
}
