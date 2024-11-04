package com.ebook_searching.ontology.model.Ontology;


import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class OWLBook {
    private int id;

    private String uri;

    private String title="hihi";

    private double avgRating;

    private int ratingCount;

    private long publicationTime;

    private int totalPages;
    private String uuid;

    private String genre;

    private String publisher;

    private int reviewCount;

    private String language;

    private String description;
}

