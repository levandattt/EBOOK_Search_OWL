package com.ebook_searching.ontology.service.Impl;

import com.ebook_searching.ontology.repository.OntologyRepository;
import com.ebook_searching.ontology.service.SparqlService;
import org.apache.jena.query.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class SparqlServiceImpl implements SparqlService {
    @Autowired
    private  OntologyRepository ontologyRepository;

    public String executeSparqlQuery(String sparqlQueryString) {
        return ontologyRepository.transaction(ReadWrite.READ, model -> {
            Query query = QueryFactory.create(sparqlQueryString);
            QueryExecution qexec = QueryExecutionFactory.create(query, model);
            ResultSet results = qexec.execSelect();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ResultSetFormatter.outputAsJSON(outputStream, results);
            String json = new String(outputStream.toByteArray());
            return json;
        });
    }
}
