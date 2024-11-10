package com.ebook_searching.book.controller;

import com.ebook_searching.book.dto.AuthorDetail;
import com.ebook_searching.book.dto.Genres;
import com.ebook_searching.book.payload.PaginationResponse;
import com.ebook_searching.book.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/genres")
public class GenresController {
    @Autowired
    private AuthorService authorService;

    @GetMapping("")
    public PaginationResponse<Genres> getAllGenres() {
        List<Genres> genres = new ArrayList<>();
        genres.add(Genres.builder().id(1L).name("Personal").image("https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1555455680i/45157028.jpg").build());
        genres.add(Genres.builder().id(2L).name("Science").image("https://static.vecteezy.com/system/resources/previews/000/138/520/original/free-science-vector-elements.jpg").build());
        genres.add(Genres.builder().id(3L).name("Math").image("https://th.bing.com/th/id/R.2347a18647824f8d0844dbde72710749?rik=%2bBugQ4ZbxUKGkQ&pid=ImgRaw&r=0").build());
        genres.add(Genres.builder().id(4L).name("Literature").image("https://th.bing.com/th/id/R.95e061ac6a1730b083d02b52e2922b89?rik=TMn6e7VdEACsSw&pid=ImgRaw&r=0").build());

        PaginationResponse<Genres> res = new PaginationResponse<>();
        res.setData(genres);
        res.setLimit(10);
        res.setOffset(0);
        res.setNumPages(1);
        res.setTotalItems(4);
        return res;
    }
}
