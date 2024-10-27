package com.ebook_searching.book.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "author_books", uniqueConstraints = {@UniqueConstraint(columnNames = {"author_id", "book_id"})})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorBooks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;
}

