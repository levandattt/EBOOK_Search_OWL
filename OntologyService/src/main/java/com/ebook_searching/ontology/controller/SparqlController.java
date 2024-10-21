package com.ebook_searching.ontology.controller;

import com.ebook_searching.ontology.service.SparqlService;
import org.apache.jena.query.ResultSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/sparql")
public class SparqlController {
    @Autowired
    private SparqlService sparqlService;

    @PostMapping("/query")
    public String query(@RequestParam String query) {
        try {
            String result = sparqlService.executeSparqlQuery(query);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
