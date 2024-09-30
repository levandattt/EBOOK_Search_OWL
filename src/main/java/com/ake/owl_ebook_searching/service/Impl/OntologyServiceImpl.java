package com.ake.owl_ebook_searching.service.Impl;

import com.ake.owl_ebook_searching.service.OntologyService;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class OntologyServiceImpl implements OntologyService {

    @Value("${ontology.tbox.path}")
    private String tboxPath;

    @Value("${ontology.abox.path}")
    private String aboxPath;

    private Model model;

    @PostConstruct
    public void init() {
        model = ModelFactory.createDefaultModel();
        model.read(tboxPath);
        model.read(aboxPath);
    }

    @Override
    public Model getModel() {
        return model;
    }
}
