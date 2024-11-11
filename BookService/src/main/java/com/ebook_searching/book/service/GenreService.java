package com.ebook_searching.book.service;

import com.ebook_searching.book.dto.GenreDetail;
import com.ebook_searching.book.model.OrderCriteria;
import com.ebook_searching.book.model.Pagination;
import com.ebook_searching.book.model.genre.Genre;
import com.ebook_searching.book.payload.PaginationResponse;
import org.ebook_searching.proto.Event;

import java.util.List;
import java.util.Set;

public interface GenreService {
    void addGenre(Event.Genre genre);
    PaginationResponse<GenreDetail> getAllGenres(Pagination pagination, OrderCriteria orderCriteria);
    void updateGenre(Event.Genre genre);
    void deleteGenre(String uuid);
}