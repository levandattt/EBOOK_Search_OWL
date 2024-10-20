package com.ebook_searching.ontology.service;

import com.ebook_searching.ontology.payload.AddClassReq;
import org.apache.jena.rdf.model.Model;
import org.ebook_searching.proto.Event;

import java.util.Map;

public interface OntologyService {
    public void loadOntology();
    public void saveBook(Event.AddBookEvent bookEvent);
//    public String addClass(AddClassReq addClassReq);
    public String getClasses();
}
