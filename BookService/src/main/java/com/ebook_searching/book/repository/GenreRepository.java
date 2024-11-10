package com.ebook_searching.book.repository;

import com.ebook_searching.book.model.genre.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    Set<Genre> findByIdIn(Set<Long> ids);
//    Genre findByName(String name);
}