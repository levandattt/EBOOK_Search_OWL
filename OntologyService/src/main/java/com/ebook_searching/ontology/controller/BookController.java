package com.ebook_searching.ontology.controller;

import com.ebook_searching.ontology.model.Book;
import com.ebook_searching.ontology.payload.QueryRes;
import com.ebook_searching.ontology.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("")
    public QueryRes<List<Book>> searchBooks(@RequestParam String author) {
        return bookService.searchBooksByAuthor(author);
    }
}
