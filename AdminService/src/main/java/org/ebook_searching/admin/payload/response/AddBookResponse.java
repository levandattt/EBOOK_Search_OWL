package org.ebook_searching.admin.payload.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Data
public class AddBookResponse {
    private Long id;
    private String title;

    private String genre;

    private LocalDate publishedAt;

    private String publisher;

    private String language;

    private BigDecimal avgRatings;

    private Long ratingsCount;

    private Set<Long> authorIds;
}
