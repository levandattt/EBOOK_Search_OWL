package org.ebook_searching.admin.payload.request;

import lombok.Data;
import org.jetbrains.annotations.Nullable;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
public class UpdateBookRequest {

    @NotNull(message = "Book ID is required")
    private Long id;

    @Nullable
    @Size(max = 255, message = "Title cannot exceed 255 characters")
    private String title;

    @Nullable
    private Set<@Positive(message = "Genre ID must be positive") Long> genreIds;

    @Nullable
    @Positive(message = "Published date must be a positive Unix timestamp")
    private Long publishedAt;  // Unix timestamp

    @Nullable
    @Size(min = 5, max = 100, message = "Publisher name must be between 5 and 100 characters")
    private String publisher;

    @Nullable
    @Min(value = 1, message = "Total pages must be at least 1")
    private Integer totalPages;

    @Nullable
    private List<@NotBlank(message = "Category cannot be blank") String> categories;

    @Nullable
    @Size(max = 50, message = "Language cannot exceed 50 characters")
    private String language;

    @Nullable
    private String description;

    @Nullable
    private String image;  // Base64 image or URL

    @Nullable
    private Set<@Positive(message = "Author ID must be positive") Long> authorIds;
}