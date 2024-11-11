package com.ebook_searching.book.controller;

import com.ebook_searching.book.dto.GenreDetail;
import com.ebook_searching.book.model.OrderCriteria;
import com.ebook_searching.book.model.Pagination;
import com.ebook_searching.book.model.genre.Genre;
import com.ebook_searching.book.payload.PaginationResponse;
import com.ebook_searching.book.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/genres")
public class GenresController {
    @Autowired
    private GenreService genreService;

    @GetMapping("")
    public PaginationResponse<GenreDetail> getPaginatedGenres(
            @RequestParam(required = false, defaultValue = "10") Integer limit,
            @RequestParam(required = false, defaultValue = "0")
            @Min(value = 0, message = "The starting number must be non-negative") int offset,
            @RequestParam(required = false, defaultValue = "id") String orderBy,
            @RequestParam(required = false, defaultValue = "asc") String orderDirection
    ) {
        return genreService.getAllGenres(
                Pagination.builder().limit(limit).offset(offset).build(),
                OrderCriteria.builder().orderBy(orderBy).orderDirection(orderDirection).build()
        );
        }
}
