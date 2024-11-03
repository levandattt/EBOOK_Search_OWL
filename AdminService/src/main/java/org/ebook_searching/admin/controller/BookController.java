package org.ebook_searching.admin.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.ebook_searching.admin.dto.BaseBook;
import org.ebook_searching.admin.dto.BookDetail;
import org.ebook_searching.admin.payload.PageResponse;
import org.ebook_searching.admin.payload.request.AddBookRequest;
import org.ebook_searching.admin.payload.request.UpdateBookRequest;
import org.ebook_searching.admin.payload.response.AddBookResponse;
import org.ebook_searching.admin.payload.response.DeleteBookResponse;
import org.ebook_searching.admin.payload.response.GetBookResponse;
import org.ebook_searching.admin.payload.response.UpdateBookResponse;
import org.ebook_searching.admin.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping
    public AddBookResponse addABook(@Valid @RequestBody AddBookRequest book) {
        return bookService.addBook(book);
    }

    @PutMapping
    public UpdateBookResponse updateBook(@Valid @RequestBody UpdateBookRequest req) {
        return bookService.updateBook(req);
    }

    @DeleteMapping("/{id}")
    public DeleteBookResponse deleteBook(@Valid @PathVariable("id") Long id) {
        return bookService.deleteBook(id);
    }

    @GetMapping
    public List<GetBookResponse> getPaginatedBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public BookDetail getBookDetail(@PathVariable Long id) {
        return bookService.findBookDetailById(id);
    }
}
