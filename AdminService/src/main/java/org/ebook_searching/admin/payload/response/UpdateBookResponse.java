package org.ebook_searching.admin.payload.response;

import lombok.Data;
import org.ebook_searching.admin.dto.AuthorDetail;
import org.ebook_searching.admin.model.Genre;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Data
public class UpdateBookResponse {

    private Long id;
    private String title;

    private Set<Genre> genres;  // List of genres

    private Long publishedAt;  // Unix timestamp representation

    private String publisher;

    private Integer totalPages;  // Total pages of the book

    private List<String> categories;  // List of categories

    private String language;

    private String description;

    private String image;  // Base64-encoded image or URL

    private Set<AuthorDetail> authors;  // Associated author IDs
}