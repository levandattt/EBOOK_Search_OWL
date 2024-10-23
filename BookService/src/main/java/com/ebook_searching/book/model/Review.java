package com.ebook_searching.book.model;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String image;  // Base64 image

    private String reviewer;

    @Column(columnDefinition = "TEXT")
    private String content;

    private Long time;  // Unix timestamp of the review

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)  // Proper join column here
    private Book book;  // Many reviews can belong to one book
}