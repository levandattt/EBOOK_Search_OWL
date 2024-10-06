package org.ebook_searching.admin.payload.request;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Data
public class AddBookRequest {

    private String title;

    private String genre;

    private LocalDate publishedAt;

    private String publisher;

    private String language;

    private BigDecimal avgRatings;

    private Long ratingsCount;

    // You can also include authors if needed
    private Set<Long> authorIds;
}
