package org.ebook_searching.admin.model;

import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "books")
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column
    private Long publishedAt;

    @Column(length = 100)
    private String publisher;

    @Column
    private Integer totalPages;

    private String categories;

    @Column(length = 50)
    private String language;

    private String description;

    @Column(nullable = false, unique = true, updatable = false)
    private String uuid = UUID.randomUUID().toString();

    @Lob  // Use a LOB type for large data like Base64-encoded images
    @Column(columnDefinition = "LONGTEXT")  // Can handle larger data than TEXT
    private String image;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "author_books",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<Author> authors = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "book_genres",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genre> genres = new HashSet<>();


    public void updateGenres(Set<Genre> updatedGenres) {
        Iterator<Genre> iterator = this.genres.iterator();
        while (iterator.hasNext()) {
            Genre genre = iterator.next();
            if (!updatedGenres.contains(genre)) {
                iterator.remove();
                genre.removeBook(this);
            }
        }

        for (Genre genre : updatedGenres) {
            if (!genres.contains(genre)) {
                this.addGenre(genre);
            }
        }
    }

    public void addGenre(Genre genre) {
        this.genres.add(genre);
        genre.addBook(this);
    }

    public void removeGenre(Genre genre) {
        this.genres.remove(genre);
        genre.removeBook(this);
    }



    public void updateAuthors(Set<Author> updatedAuthors) {
        // Remove authors that are no longer associated
        for (Author author : authors) {
            if (!updatedAuthors.contains(author)) {
                this.removeAuthor(author);
            }
        }

        // Add new authors
        for (Author author : updatedAuthors) {
            if (!authors.contains(author)) {
                this.addAuthor(author);
            }
        }
    }

    public void addAuthor(Author author) {
        this.authors.add(author);
        author.addBook(this);
    }

    public void removeAuthor(Author author) {
        this.authors.remove(author);
        author.removeBook(this);
    }

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private Set<Review> reviews = new HashSet<>();
}

