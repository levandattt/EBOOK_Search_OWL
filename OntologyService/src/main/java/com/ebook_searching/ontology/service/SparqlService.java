package com.ebook_searching.ontology.service;

import org.apache.jena.query.ResultSet;

import java.util.Map;

public interface SparqlService {
    Map<String, Object>  executeSparqlQuery(String sparqlQueryString);
}
