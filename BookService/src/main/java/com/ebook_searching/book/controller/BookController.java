package com.ebook_searching.book.controller;

import com.ebook_searching.book.dto.BookDetail;
import com.ebook_searching.book.model.Book;
import com.ebook_searching.book.payload.ListBooksResponse;
import com.ebook_searching.book.payload.PaginationResponse;
import com.ebook_searching.book.service.BookService;
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
    public ListBooksResponse searchBooks(@RequestParam(required = false) String keyword) {
        // call ontology service for keyword-based searching
        if (keyword != null && !keyword.isEmpty()) {

        } else {
            // search in db
        }

        // update
        return null;
    }
}
