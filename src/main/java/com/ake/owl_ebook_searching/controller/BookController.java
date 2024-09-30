package com.ake.owl_ebook_searching.controller;

import com.ake.owl_ebook_searching.model.Book;
import com.ake.owl_ebook_searching.payload.QueryRes;
import com.ake.owl_ebook_searching.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/search")
//    public List<Book> searchBooks(@RequestParam String author) {
    public QueryRes<List<Book>> searchBooks(@RequestParam String author) {
        return bookService.searchBooksByAuthor(author);
    }
}
