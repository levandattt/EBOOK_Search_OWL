package com.ebook_searching.ontology.controller;

import com.ebook_searching.ontology.payload.ListKeyWordReq;
import com.ebook_searching.ontology.service.OntologyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ontology")
public class OntologyController {
    @Autowired
    private OntologyService ontologyService;

    @GetMapping("/load")
    public void loadOntology() {
        ontologyService.loadOntology();
    }

    @GetMapping("/classes")
    public String getClasses() {
        return ontologyService.getClasses();
    }

    @PostMapping("/queries")
    public String query(@RequestBody ListKeyWordReq listKeyWordReq) {
        return ontologyService.query(listKeyWordReq.getKeywords());
    }
}
