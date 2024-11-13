package org.ebook_searching.admin.payload.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.util.List;
import java.util.Set;

@Data
public class AddBookRequest {

    @NotNull(message = "Title is required")
    @NotBlank(message = "Title cannot be blank")
    @Length(max = 255, message = "Title cannot exceed 255 characters")
    private String title;

    @NotNull(message = "Genres IDs are required")
    @Size(min = 1, message = "At least one genre is required")
    private Set<Long> genreIds;

    @NotNull(message = "Published date is required")
    @Positive(message = "Published date must be a positive Unix timestamp")
    private Long publishedAt;

    @NotNull(message = "Publisher is required")
    @NotBlank(message = "Publisher cannot be blank")
    @Length(min = 5, max = 100, message = "Publisher name must be between 5 and 100 characters")
    private String publisher;

    @NotNull(message = "Total pages are required")
    @Min(value = 1, message = "Total pages must be at least 1")
    private Integer totalPages;

    @NotNull(message = "Categories are required")
    @Size(min = 1, message = "At least one category is required")
    private List<@NotBlank(message = "Category cannot be blank") String> categories;

    @NotNull(message = "Language is required")
    @NotBlank(message = "Language cannot be blank")
    @Length(max = 50, message = "Language cannot exceed 50 characters")
    private String language;

    private String description;

    private String image;  // Base64 or URL

    @NotNull(message = "Author IDs are required")
    @Size(min = 1, message = "At least one author is required")
    private Set<@Positive(message = "Author ID must be positive") Long> authorIds;
}
