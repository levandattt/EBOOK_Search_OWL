package com.ebook_searching.ontology.service.Impl;

import com.ebook_searching.ontology.service.OntologyService;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class OntologyServiceImpl implements OntologyService {
    @Autowired
    private OntologyRepository ontologyRepository;

    @Value("${ontology.tbox.path}")
    private String tboxPath;

    @Value("${ontology.abox.path}")
    private String aboxPath;

    public void loadOntology() {
        //create list ontology file path
        List<String> filePath = new ArrayList<>();
        filePath.add(tboxPath);
        filePath.add(aboxPath);

        //load ontology from file
        ontologyRepository.loadOntologyFromFile(filePath);
    }

}
