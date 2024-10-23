package org.ebook_searching.admin.repository;

import org.ebook_searching.admin.model.Author;
import org.ebook_searching.admin.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Set<Author> findByIdIn(Set<Long> ids);
}
