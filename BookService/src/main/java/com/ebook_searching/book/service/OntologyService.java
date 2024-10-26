package com.ebook_searching.book.service;

import org.apache.jena.rdf.model.Model;
import org.ebook_searching.proto.Event;

import java.util.List;

public interface OntologyService {
    public void loadOntology();
    public void saveBook(Event.AddBookEvent bookEvent);
//    public String addClass(AddClassReq addClassReq);
    //    public String addClass(AddClassReq addClassReq);
    public String getClasses();
    public String query(List<String> list);
}
