package org.ebook_searching.admin.controller;

import org.checkerframework.checker.units.qual.g;
import org.ebook_searching.admin.dto.GenreDetail;
import org.ebook_searching.admin.model.OrderCriteria;
import org.ebook_searching.admin.model.Pagination;
import org.ebook_searching.admin.payload.PaginationResponse;
import org.ebook_searching.admin.payload.request.AddAuthorRequest;
import org.ebook_searching.admin.payload.request.AddGenreRequest;
import org.ebook_searching.admin.payload.request.UpdateAuthorRequest;
import org.ebook_searching.admin.payload.request.UpdateGenreRequest;
import org.ebook_searching.admin.payload.response.*;
import org.ebook_searching.admin.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RequestMapping("/api/genres")
@RestController
public class GenreController{

    @Autowired
    private GenreService genreService;

    @PostMapping
    public AddGenreResponse addGenre(@Valid @RequestBody AddGenreRequest genre) {
        return genreService.addGenre(genre);
    }

    @GetMapping
    public PaginationResponse<GetGenreResponse> getPaginatedGenres(
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

    @PutMapping
    public UpdateGenreResponse updateGenre(@Valid @RequestBody UpdateGenreRequest req) {
        return genreService.updateGenre(req);
    }

    @DeleteMapping("/{id}")
    public DeleteGenreResponse deleteGenre(@Valid @PathVariable("id") Long id) {
        return genreService.deleteGenre(id);
    }
}
