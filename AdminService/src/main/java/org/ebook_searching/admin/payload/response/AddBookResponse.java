package org.ebook_searching.admin.payload.response;

import lombok.Data;
import org.ebook_searching.admin.dto.AuthorDetail;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
public class AddBookResponse {

    private Long id;
    private String title;

    private List<String> genres;  // List of genres

    private Long publishedAt;  // Unix timestamp

    private String publisher;

    private Integer totalPages;  // Total pages

    private List<String> categories;  // List of categories

    private String language;

    private String description;

    private String image;  // Base64 image or URL

    private List<AuthorDetail> authors;  // IDs of associated authors
}