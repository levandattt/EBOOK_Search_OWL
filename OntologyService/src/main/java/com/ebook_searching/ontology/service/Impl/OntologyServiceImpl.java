package com.ebook_searching.ontology.service.Impl;

import com.ebook_searching.ontology.constants.SpartQueryConstant;
import com.ebook_searching.ontology.model.Ontology.ObjectProperty;
import com.ebook_searching.ontology.repository.OntologyRepository;
import com.ebook_searching.ontology.service.OntologyService;
import com.ebook_searching.ontology.service.SparqlService;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Literal;
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
    private SparqlService sparqlService;

    @Value("${ontology.tbox.path}")
    private String tboxPath;

    @Value("${ontology.abox.path}")
    private String aboxPath;
    @Autowired
    private SparqlServiceImpl sparqlServiceImpl;

    @Override
    public void loadOntology() {
        //create list ontology file path
        List<String> filePath = new ArrayList<>();
        filePath.add(tboxPath);
        filePath.add(aboxPath);
        System.out.println("hihihihi");
        //load ontology from file
        ontologyRepository.loadOntologyFromFile(filePath);
    }

    @Override
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

    @Override
    public String query(List<String> keywords) {
        // Lowercase the list
        keywords.replaceAll(String::toLowerCase);

        // get class by class name
        List<String> classes = checkCorrectClass(keywords);

        // remove class from list
        List<String> lowerCaseListClass = new ArrayList<>(classes);
        classes.replaceAll(String::toLowerCase);
        keywords.removeAll(classes);

        // get class by Data property
        Map<String, String> classByProperty = getClassesByDataProperty(keywords);
        classes.addAll(classByProperty.keySet().stream().filter(key -> !classes.contains(key)).toList());

        System.out.println("classes: " + classes);
        System.out.println("classByProperty: " + classByProperty);


        //get class by object property
        List <ObjectProperty> objectProperties = getClassesByObjectProperty(lowerCaseListClass);
        System.out.println("objectProperties: " + objectProperties);

        //create sparql query
        return null;
    }

    public Map<String, String> getClassesByDataProperty(List<String> list) {
        String spartQueryClass = SpartQueryConstant.GET_CLASS_BY_DATAPROPERTIES(list);
        //check class
        return ontologyRepository.transaction(ReadWrite.READ, model -> {
            Map<String, String> classMap = new HashMap<>();
            Query query = QueryFactory.create(spartQueryClass);
            try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
                ResultSet results = qexec.execSelect();
                while (results.hasNext()) {
                    QuerySolution soln = results.nextSolution();
                    Literal classesLabel = soln.getLiteral("uniqueLabel");
                    Literal dataProperties = soln.getLiteral("value");
                    classMap.put(classesLabel.getString().toLowerCase(), dataProperties.getString().toLowerCase());
                }
            }
            return classMap;
        });
    }

    public List<String> checkCorrectClass(List<String> list) {
        String spartQueryClass = SpartQueryConstant.CHECK_LIST_CLASS(list);
        //check class
        return ontologyRepository.transaction(ReadWrite.READ, model -> {
            List<String> classLabels = new ArrayList<>();
            Query query = QueryFactory.create(spartQueryClass);
            try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
                ResultSet results = qexec.execSelect();
                while (results.hasNext()) {
                    QuerySolution soln = results.nextSolution();
                    Literal label = soln.getLiteral("label");
                    classLabels.add(label.getString());
                }
            }
            return classLabels;
        });
    }

    public String getObjectPropertiesBetweenClasses(String classA, String classB) {
        String spartQueryClass = SpartQueryConstant.GET_OBJECTPROPERTIES_BETWEEN_CLASSES(classA, classB);
        //check class
        return ontologyRepository.transaction(ReadWrite.READ, model -> {
            Query query = QueryFactory.create(spartQueryClass);
            try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
                ResultSet results = qexec.execSelect();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ResultSetFormatter.outputAsJSON(outputStream, results);
                String json = new String(outputStream.toByteArray());
                return json;
            }
        });
    }

    public List<ObjectProperty> getClassesByObjectProperty(List<String> classes) {
        String classA = classes.get(0);
        String classB = classes.get(1);
        String spartQueryClass = SpartQueryConstant.GET_OBJECTPROPERTIES_BETWEEN_CLASSES(classA, classB);
        //check class
        return ontologyRepository.transaction(ReadWrite.READ, model -> {
            List<ObjectProperty> classLabels = new ArrayList<>();
            Query query = QueryFactory.create(spartQueryClass);
            try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
                ResultSet results = qexec.execSelect();
                while (results.hasNext()) {
                    QuerySolution soln = results.nextSolution();
                    Literal name = soln.getLiteral("propertyName");
                    Literal domain = soln.getLiteral("domainName");
                    Literal range = soln.getLiteral("rangeName");
                    ObjectProperty objectProperty = new ObjectProperty();
                    objectProperty.setName(name.getString());
                    objectProperty.setDomain(domain.getString());
                    objectProperty.setRange(range.getString());
                    classLabels.add(objectProperty);
                }
            }
            return classLabels;
        });
    }
}


