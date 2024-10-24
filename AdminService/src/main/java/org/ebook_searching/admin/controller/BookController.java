package org.ebook_searching.admin.controller;

import javax.validation.Valid;
import org.ebook_searching.admin.mapper.EventMapper;
import org.ebook_searching.admin.model.Book;
import org.ebook_searching.admin.payload.request.AddBookRequest;
import org.ebook_searching.admin.payload.request.UpdateBookRequest;
import org.ebook_searching.admin.payload.response.AddBookResponse;
import org.ebook_searching.admin.payload.response.DeleteBookResponse;
import org.ebook_searching.admin.payload.response.GetBookResponse;
import org.ebook_searching.admin.payload.response.UpdateBookResponse;
import org.ebook_searching.admin.service.BookService;
import org.ebook_searching.proto.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

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
    public List<GetBookResponse> getAllBooks() {
        return bookService.getAllBooks();
    }
}
