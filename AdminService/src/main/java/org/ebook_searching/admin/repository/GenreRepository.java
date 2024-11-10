package org.ebook_searching.admin.repository;

import org.ebook_searching.admin.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    Set<Genre> findByIdIn(Set<Long> ids);
    Genre findByName(String name);
}