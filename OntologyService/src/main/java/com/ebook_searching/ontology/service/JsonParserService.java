package com.ebook_searching.ontology.service;

import com.ebook_searching.ontology.model.Ontology.OWLAuthor;
import com.ebook_searching.ontology.model.Ontology.OWLBook;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public interface JsonParserService {
    public List<OWLBook> parseBooks(JsonNode root);
    public List<OWLAuthor> parseAuthors(JsonNode root);
}
