package com.ebook_searching.ontology.service;

import com.ebook_searching.ontology.payload.AddClassReq;
import org.apache.jena.rdf.model.Model;

import java.util.Map;

public interface OntologyService {
    public void loadOntology();
    public String addClass(AddClassReq addClassReq);
    public String getClasses();
}
