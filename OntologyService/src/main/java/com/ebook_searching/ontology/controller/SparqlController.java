package com.ebook_searching.ontology.controller;

import com.ebook_searching.ontology.service.SparqlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/sparql")
public class SparqlController {
    @Autowired
    private SparqlService sparqlService;

//    @PostMapping("/query")
//    public SparqlRawQueryRes query(@RequestParam String query) {
//        Map<String, Object> result = sparqlService.executeSparqlQuery(query);
//        return SparqlRawQueryRes.builder()
//                .rawQueryRes(query)
//                .rawQueryData(result)
//                .build();
//    }

    @PostMapping("/query")
    public Map<String, Object> query(@RequestParam String query) {
        Map<String, Object> result = sparqlService.executeSparqlQuery(query);
        return result;
    }

}
