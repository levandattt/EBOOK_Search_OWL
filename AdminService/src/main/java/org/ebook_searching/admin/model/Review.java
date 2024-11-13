package org.ebook_searching.admin.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "reviews")
@Getter
@Setter
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob  // Use a LOB type for large data like Base64-encoded images
    @Column(columnDefinition = "LONGTEXT")  // Can handle larger data than TEXT
    private String image; // Base64 image

    private String reviewer;

    @Column(nullable = false, unique = true, updatable = false)
    private String uuid = UUID.randomUUID().toString();

    @Column(columnDefinition = "TEXT")
    private String content;

    private Long time;  // Unix timestamp of the review

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)  // Proper join column here
    private Book book;  // Many reviews can belong to one book
}