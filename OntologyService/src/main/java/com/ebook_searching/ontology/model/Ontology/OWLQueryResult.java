package com.ebook_searching.ontology.model.Ontology;

import lombok.Data;

import java.util.List;

@Data
public class OWLQueryResult {
    List<OWLAuthor> authors;
    List<OWLBook> books;
}
