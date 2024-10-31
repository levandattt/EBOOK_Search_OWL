package com.ebook_searching.ontology.service;

import com.ebook_searching.ontology.model.Ontology.OWLBook;
import com.ebook_searching.ontology.model.Ontology.OWLQueryResult;
import com.ebook_searching.ontology.payload.AddClassReq;
import com.ebook_searching.ontology.payload.OntologySearchReq;
import org.apache.jena.rdf.model.Model;
import org.ebook_searching.proto.Event;

import java.util.List;

public interface OntologyService {
    public void loadOntology();
    public void saveBook(Event.AddBookEvent bookEvent);
    public String getClasses();
    public OWLQueryResult search(OntologySearchReq ontologySearchReq);
    OWLQueryResult query(List<String> keywords, OntologySearchReq ontologySearchReq);

    void saveAuthor(Event.Author message);
}
