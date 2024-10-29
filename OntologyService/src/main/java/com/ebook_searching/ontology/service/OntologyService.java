package com.ebook_searching.ontology.service;

import org.ebook_searching.proto.Event;

import java.util.List;

public interface OntologyService {
    public void loadOntology();
    public void saveBook(Event.AddBookEvent bookEvent);
    public String getClasses();
    public String query(List<String> list);

    void saveAuthor(Event.Author message);
}
