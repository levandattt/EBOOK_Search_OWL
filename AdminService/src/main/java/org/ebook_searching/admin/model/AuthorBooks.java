package org.ebook_searching.admin.model;

import jakarta.persistence.*;
import lombok.*;

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

