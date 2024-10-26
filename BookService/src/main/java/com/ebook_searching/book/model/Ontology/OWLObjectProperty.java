package com.ebook_searching.book.model.Ontology;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class OWLObjectProperty {
    private String name;
    private String domain;
    private String range;
}
