package com.ebook_searching.ontology.repository;

import jakarta.annotation.PostConstruct;
import org.apache.jena.base.Sys;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.ResIterator;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.tdb.TDBFactory;
import org.apache.jena.util.FileManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Repository
public class OntologyRepository {
    @Value("${ontology.tdb2.directory}")
    private String TDB_DIRECTORY;

    public void loadOntologyFromFile(List<String> filePath) {
        Dataset dataset = TDBFactory.createDataset(TDB_DIRECTORY);
        dataset.begin(ReadWrite.WRITE);
        try {
            Model model = dataset.getDefaultModel();
            filePath.forEach(file -> {
                FileManager.get().readModel(model, file);
            });
            dataset.commit();
        } finally {
            dataset.end();
        }
    }

    public <T> T transaction(Function<Model, T> action) {
        Dataset dataset = TDBFactory.createDataset(TDB_DIRECTORY);
        dataset.begin(ReadWrite.READ);
        try {
            Model model = dataset.getDefaultModel();
            T result = action.apply(model);
            dataset.commit();
            return result;
        } catch (Exception e) {
            dataset.abort();
            throw e;
        } finally {
            dataset.end();
        }
    }
}