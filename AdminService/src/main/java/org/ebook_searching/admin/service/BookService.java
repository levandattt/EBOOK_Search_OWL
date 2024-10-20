package org.ebook_searching.admin.service;

import org.ebook_searching.admin.model.Book;
import org.ebook_searching.admin.payload.request.AddBookRequest;
import org.ebook_searching.admin.payload.request.UpdateBookRequest;
import org.ebook_searching.admin.payload.response.AddBookResponse;
import org.ebook_searching.admin.payload.response.DeleteBookResponse;
import org.ebook_searching.admin.payload.response.GetBookResponse;
import org.ebook_searching.admin.payload.response.UpdateBookResponse;

import java.util.List;

public interface BookService {
    AddBookResponse addBook(AddBookRequest book);
    UpdateBookResponse updateBook(UpdateBookRequest book);
    DeleteBookResponse deleteBook(Long id);
    Book findById(Long id);
    List<GetBookResponse> getAllBooks();
}
