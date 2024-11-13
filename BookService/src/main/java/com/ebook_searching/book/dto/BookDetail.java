package com.ebook_searching.book.dto;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class BookDetail {
    private Long id;
    private String uri;
    private String title;
    private double avgRating;
    private int ratingCount;
    private Long publicationTime;
    private int totalPages;
    private List<String> categories;
    private List<GenreDetail> genres;
    private String publisher;
    private List<AuthorDetail> authors;
    private String language;
    private String description;
    private String image;
    private Set<ReviewDetail> reviews;

}
