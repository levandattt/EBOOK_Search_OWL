package org.ebook_searching.admin.payload.request;

import lombok.Data;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Data
public class UpdateBookRequest {

    @Nullable
    private String title;

    @Nullable
    private String genre;

    @Nullable
    private LocalDate publishedAt;

    @Nullable
    private String publisher;

    @Nullable
    private String language;

    @Nullable
    private BigDecimal avgRatings;

    @Nullable
    private Long ratingsCount;

    @Nullable
    private Set<Long> authorIds;
}
