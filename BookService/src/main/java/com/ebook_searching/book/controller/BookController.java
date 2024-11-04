package com.ebook_searching.book.controller;

import com.ebook_searching.book.adapter.ontology_client.*;
import com.ebook_searching.book.dto.BookDetail;
import com.ebook_searching.book.model.OrderCriteria;
import com.ebook_searching.book.model.Pagination;
import com.ebook_searching.book.model.author.Author;
import com.ebook_searching.book.model.book.BookCriteria;
import com.ebook_searching.book.payload.ListBooksResponse;
import com.ebook_searching.book.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BookController {
    @Autowired
    private BookService bookService;

    @Autowired
    private OntologyClient ontologyClient;

    @Value("${app.api.default-page-size}")
    private Integer defaultPageSize;

    @GetMapping("/search")
    public ListBooksResponse searchBooks(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String genreSlug,
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false, defaultValue = "0")
            @Min(value = 0, message = "Số bắt đầu phải là số không âm") int offset,
            @RequestParam(required = false, defaultValue = "id") String orderBy,
            @RequestParam(required = false, defaultValue = "asc") String orderDirection
    ) {
        if (limit == null) {
            limit = defaultPageSize;
        }

        // call ontology service for keyword-based searching
        if (keyword != null && !keyword.isEmpty()) {
            return ontologyClient.search(OntologySearchParams.builder().keyword(keyword).build());
        } else {
            // search in db
            return bookService.searchBooks(
                    BookCriteria.builder().genreSlug(genreSlug).build(),
                    Pagination.builder().limit(limit).offset(offset).build(),
                    OrderCriteria.builder().orderBy(orderBy).orderDirection(orderDirection).build()
            );
        }

    }

    @GetMapping("/details/{id}")
    public BookDetail getBookDetail(@PathVariable Long id) {
        return bookService.findBookDetailById(id);
    }
}
