package com.ebook_searching.ontology.model.Ontology;

import lombok.*;

import java.time.LocalDate;

@Data
public class OWLAuthor {

    private String uri;

    private String name;

    private String stageName;

    private LocalDate birthDate;

    private LocalDate deathDate;

    private String birthPlace;

    private String nationality;

    private String website;

    private String description;

    private String image;
}




