package org.ebook_searching.admin.repository;

import org.ebook_searching.admin.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
