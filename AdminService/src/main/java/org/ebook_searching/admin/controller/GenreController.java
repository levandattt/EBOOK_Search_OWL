package org.ebook_searching.admin.controller;

import org.checkerframework.checker.units.qual.g;
import org.ebook_searching.admin.dto.GenreDetail;
import org.ebook_searching.admin.payload.PaginationResponse;
import org.ebook_searching.admin.payload.request.AddAuthorRequest;
import org.ebook_searching.admin.payload.request.AddGenreRequest;
import org.ebook_searching.admin.payload.request.UpdateAuthorRequest;
import org.ebook_searching.admin.payload.request.UpdateGenreRequest;
import org.ebook_searching.admin.payload.response.*;
import org.ebook_searching.admin.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public PaginationResponse<GetGenreResponse> getPaginatedGenres() {
        List<GetGenreResponse> genres = genreService.getAllGenres();
        PaginationResponse<GetGenreResponse> res = new PaginationResponse();
        res.setData(genres);
        res.setLimit(10);
        res.setOffset(0);
        res.setNumPages(1);
        res.setTotalItems(4);
        return res;
    }

    @PutMapping
    public UpdateGenreResponse updateGenre(@Valid @RequestBody UpdateGenreRequest req) {
        return genreService.updateGenre(req);
    }

}
