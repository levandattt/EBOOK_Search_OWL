package com.ebook_searching.book.adapter.ontology_client;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class OWLBook {
    private int id;

    private String uri;

    private String title;

    private double avgRating;

    private int ratingCount;

    private long publicationTime;

    private int totalPages;

    private String genre;

    private String publisher;

    private int reviewCount;

    private String language;

    private String description;
}

