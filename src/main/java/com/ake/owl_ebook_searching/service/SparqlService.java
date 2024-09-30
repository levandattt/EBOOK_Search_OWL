package com.ake.owl_ebook_searching.service;

import org.apache.jena.query.ResultSet;

import java.util.Map;

public interface SparqlService {
    Map<String, Object>  executeSparqlQuery(String sparqlQueryString);
}
