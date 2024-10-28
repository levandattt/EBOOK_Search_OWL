package com.ebook_searching.ontology.controller;

import com.ebook_searching.ontology.model.Ontology.OWLBook;
import com.ebook_searching.ontology.model.Ontology.OWLQueryResult;
import com.ebook_searching.ontology.payload.ListKeyWordReq;
import com.ebook_searching.ontology.payload.OntologySearchReq;
import com.ebook_searching.ontology.service.OntologyService;
import com.ebook_searching.ontology.service.SentenceAnalyzerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @GetMapping("/search")
    public OWLQueryResult search(@Valid @ModelAttribute OntologySearchReq ontologySearchReq) {
        return ontologyService.search(ontologySearchReq);
    }

}
