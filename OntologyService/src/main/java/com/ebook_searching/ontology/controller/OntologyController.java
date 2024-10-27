package com.ebook_searching.ontology.controller;

import com.ebook_searching.ontology.model.Ontology.OWLBook;
import com.ebook_searching.ontology.model.Ontology.OWLQueryResult;
import com.ebook_searching.ontology.payload.ListKeyWordReq;
import com.ebook_searching.ontology.service.OntologyService;
import com.ebook_searching.ontology.service.SentenceAnalyzerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ontology")
public class OntologyController {
    @Autowired
    private OntologyService ontologyService;
    @Autowired
    private SentenceAnalyzerService sentenceAnalyzerService;

    @GetMapping("/load")
    public void loadOntology() {
        ontologyService.loadOntology();
    }

    @GetMapping("/classes")
    public String getClasses() {
        return ontologyService.getClasses();
    }

    @PostMapping("/queries")
    public OWLQueryResult query(@RequestBody ListKeyWordReq listKeyWordReq) {
        return ontologyService.query(listKeyWordReq.getKeywords());
    }

    @GetMapping("/search")
    public OWLQueryResult search(@RequestParam("keyword") String keyword) {
        return ontologyService.search(keyword);
    }

}
