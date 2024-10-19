package com.ebook_searching.ontology.service;

import org.apache.jena.rdf.model.Model;

import java.util.Map;

public interface OntologyService {
    public void loadOntology();
    public void addClass(String className);
    public String getClasses();
}
