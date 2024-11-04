package com.ebook_searching.book.repository;

import com.ebook_searching.book.model.author.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Set<Author> findByIdIn(Set<Long> ids);
    Optional<Author> findByUuid(String uuid);
    Set<Author> findByUuidIn(Set<String> uuids);
}
