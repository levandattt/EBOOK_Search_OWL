package com.ebook_searching.ontology.model.Ontology;


import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class OWLBook {
    private String uri;

    private String title="hihi";

    private long publicationTime;

    private String publisher;

    private double avgRatings;

    private int reviewCount;

    private int ratingsCount;

    private int totalPages;

    private String genre;

    private String description;

    private String language;

}

