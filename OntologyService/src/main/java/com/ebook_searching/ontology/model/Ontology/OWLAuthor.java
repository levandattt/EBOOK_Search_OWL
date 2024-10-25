package com.ebook_searching.ontology.model.Ontology;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Getter
@Setter
public class OWLAuthor {

    private Long id;

    private String uri;

    private String name;

    private LocalDate birthDate;

    private LocalDate deathDate;

    private String birthPlace;

    private String nationality;

    private String website;

    private String description;

    private String imageLink;
}




