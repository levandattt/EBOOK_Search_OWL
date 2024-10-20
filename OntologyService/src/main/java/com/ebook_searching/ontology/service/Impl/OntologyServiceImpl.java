package com.ebook_searching.ontology.service.Impl;

import com.ebook_searching.ontology.constants.SpartQueryConstant;
import com.ebook_searching.ontology.payload.AddClassReq;
import com.ebook_searching.ontology.repository.OntologyRepository;
import com.ebook_searching.ontology.service.OntologyService;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

    public String addClass(AddClassReq addClassReq) {
        return ontologyRepository.transaction(ReadWrite.WRITE, model -> {
            String namespace = model.getNsPrefixURI("");
            System.out.print(namespace);
            Resource newClass = model.createResource(namespace + addClassReq.getClassName());
            System.out.println("newClass: ");
            System.out.println(newClass);
            return null;
        });
    }

    public String getClasses() {
        return ontologyRepository.transaction(ReadWrite.READ, model -> {
            String sparqlQueryString = SpartQueryConstant.RETRIEVES_ALL_CLASSES;
            Query query = QueryFactory.create(sparqlQueryString);
            QueryExecution qexec = QueryExecutionFactory.create(query, model);
            ResultSet results = qexec.execSelect();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ResultSetFormatter.outputAsJSON(outputStream, results);
            String json = new String(outputStream.toByteArray());
            System.out.println("json: " + json);
            return json;
        });
    }
}
