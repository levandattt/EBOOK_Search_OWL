package com.ebook_searching.ontology.controller;

import com.ebook_searching.ontology.service.OntologyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ontology")
public class OntologyController {
    @Autowired
    private OntologyService ontologyService;

}
