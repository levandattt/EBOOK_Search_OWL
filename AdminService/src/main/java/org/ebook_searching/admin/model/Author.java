package org.ebook_searching.admin.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "authors")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "birthdate")
    private LocalDate birthDate;

    @Column(name = "deathdate")
    private LocalDate deathDate;

    @Column(length = 255)
    private String birthplace;

    @Column(length = 100)
    private String nationality;

    @Column(name = "createdat", nullable = false, updatable = false)
    @Generated
    private LocalDateTime createdAt;

    @Column(name = "updatedat")
    @Generated
    private LocalDateTime updatedAt;

    @ManyToMany
    @JoinTable(
            name = "author_books",
            joinColumns = @JoinColumn(name = "author_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private Set<Book> books;
}

