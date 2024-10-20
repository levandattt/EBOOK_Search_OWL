package org.ebook_searching.admin.model;

import javax.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(nullable = false, length = 100)
    private String reviewer;

    @Column(nullable = false)
    private int rating;

    @Column(columnDefinition = "TEXT")
    private String review;

    @Column(name = "review_date", nullable = false, columnDefinition = "DATE DEFAULT CURRENT_DATE")
    private LocalDate reviewDate;
}

