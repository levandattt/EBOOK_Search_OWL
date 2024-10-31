package com.ebook_searching.book.repository;

import com.ebook_searching.book.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
