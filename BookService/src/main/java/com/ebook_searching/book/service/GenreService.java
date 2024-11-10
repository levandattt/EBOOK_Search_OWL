package com.ebook_searching.book.service;

import com.ebook_searching.book.model.genre.Genre;
import org.ebook_searching.proto.Event;

import java.util.List;
import java.util.Set;

public interface GenreService {
    void addGenre(Event.Genre genre);
//    UpdateGenreResponse updateGenre(UpdateGenreRequest genre);
//    //    DeleteGenreResponse deleteGenre(Long id);
//    Genre findById(Long id);
    List<Genre> getAllGenres();
//
//    GenreDetail findGenreDetailById(Long id);
}