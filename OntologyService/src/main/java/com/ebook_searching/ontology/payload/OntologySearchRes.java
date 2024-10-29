package com.ebook_searching.ontology.payload;

import com.ebook_searching.ontology.model.Ontology.OWLAuthor;
import com.ebook_searching.ontology.model.Ontology.OWLBook;
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
