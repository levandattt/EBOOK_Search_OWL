package com.ebook_searching.book.adapter.ontology_client;

import lombok.Data;

import java.util.List;

@Data
public class OntologySearchRes {
    private int numPages;
    private int offset;
    private int limit;
    private int totalItems;
    private List<OWLBook> data;
    private OWLAuthor author;
}
