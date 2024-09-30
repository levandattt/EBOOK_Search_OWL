package com.ake.owl_ebook_searching.controller;

import com.ake.owl_ebook_searching.payload.SparqlQueryReq;
import com.ake.owl_ebook_searching.payload.SparqlRawQueryRes;
import com.ake.owl_ebook_searching.service.SparqlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
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
