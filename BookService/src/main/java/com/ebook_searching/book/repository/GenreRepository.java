package com.ebook_searching.book.repository;

import com.ebook_searching.book.model.genre.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    Set<Genre> findByIdIn(Set<Long> ids);
    Optional<Genre> findByUuidOrName(String uuid, String name);
    Optional<Genre> findByUuid(String uuid);
    Set<Genre> findByUuidIn(Set<String> uuids);
}