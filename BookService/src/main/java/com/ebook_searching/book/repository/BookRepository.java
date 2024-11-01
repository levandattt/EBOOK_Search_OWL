package com.ebook_searching.book.repository;

import com.ebook_searching.book.model.book.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Page<Book> findByTitleContainingOrGenresContaining(String titleKeyword, String genreKeyword, Pageable pageable);

    Optional<Book> findByUuid(String uuid);
}
