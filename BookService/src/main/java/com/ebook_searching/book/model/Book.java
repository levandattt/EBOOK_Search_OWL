package com.ebook_searching.book.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "books")
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @ElementCollection
    private List<String> genres;

    @Column
    private Long publishedAt;

    @Column(length = 100)
    private String publisher;

    @Column
    private Integer totalPages;

    @ElementCollection
    private List<String> categories;

    @Column(length = 50)
    private String language;

    @Column(precision = 2, scale = 1)
    private BigDecimal avgRating;

    @Column
    private Long ratingCount;


    private String description;

    private String image;  // Base64 image or URL

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToMany(mappedBy = "books")
    private Set<Author> authors;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;  // No @JoinColumn here, managed by Review class
}

