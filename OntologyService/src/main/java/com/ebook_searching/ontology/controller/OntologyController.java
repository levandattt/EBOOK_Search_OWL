package com.ebook_searching.ontology.controller;

import com.ebook_searching.ontology.model.Ontology.OWLBook;
import com.ebook_searching.ontology.model.Ontology.OWLQueryResult;
import com.ebook_searching.ontology.payload.ListKeyWordReq;
import com.ebook_searching.ontology.payload.OntologySearchReq;
import com.ebook_searching.ontology.payload.OntologySearchRes;
import com.ebook_searching.ontology.service.OntologyService;
import com.ebook_searching.ontology.service.SentenceAnalyzerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping
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
    public ResponseEntity<OntologySearchRes>  query(@RequestBody ListKeyWordReq listKeyWordReq) {
        OntologySearchReq ontologySearchReq = new OntologySearchReq();
        OWLQueryResult result = ontologyService.query(listKeyWordReq.getKeywords(), ontologySearchReq);
        OntologySearchRes res = new OntologySearchRes();
        res.setNumPages(1);
        res.setOffset(0);
        res.setLimit(10);
        if(result != null){
            if(result.getBooks()!=null){
                res.setTotalItems(result.getBooks().size());
                res.setData(result.getBooks());
            }
            if(result.getAuthors() != null){
                if(result.getAuthors().size() > 0){
                    res.setAuthor(result.getAuthors().get(0));
                }
            }
        }
        return new ResponseEntity<OntologySearchRes>(res, HttpStatus.OK);
    }

    @GetMapping("/api/search")
    public ResponseEntity<OntologySearchRes> search(@Valid @ModelAttribute OntologySearchReq ontologySearchReq) {
        OWLQueryResult result = ontologyService.search(ontologySearchReq);
        OntologySearchRes res = new OntologySearchRes();
        res.setNumPages(1);
        res.setOffset(0);
        res.setLimit(10);
        if(result != null){
            if(result.getBooks()!=null){
                res.setTotalItems(result.getBooks().size());
                res.setData(result.getBooks());
            }
            if(result.getAuthors() != null){
                if(result.getAuthors().size() > 0){
                    res.setAuthor(result.getAuthors().get(0));
                }

            }
        }
        return new ResponseEntity<OntologySearchRes>(res, HttpStatus.OK);
    }
}




