package com.ebook_searching.book.service;

import org.apache.jena.query.ResultSet;

public interface SparqlService {
    String executeSparqlQuery(String sparqlQueryString);
}
