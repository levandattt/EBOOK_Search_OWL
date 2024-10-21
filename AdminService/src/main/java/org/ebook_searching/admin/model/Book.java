package org.ebook_searching.admin.model;

import javax.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title="hihi";

    @Column(length = 100)
    private String genre;

    @Column(name = "published_at")
    private LocalDate publishedAt = LocalDate.now();

    @Column(length = 100)
    private String publisher;

    @Column(length = 50)
    private String language;

    @Column(name = "avg_ratings", precision = 2, scale = 1)
    private BigDecimal avgRatings;

    @Column(name = "ratings_count")
    private Long ratingsCount;

    @Column(name = "createdat", nullable = false, updatable = false)
    @Generated
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updatedat")
    @Generated
    private LocalDateTime updatedAt = LocalDateTime.now();

    @ManyToMany(mappedBy = "books")
    private Set<Author> authors;

//    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<Review> reviews;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}

