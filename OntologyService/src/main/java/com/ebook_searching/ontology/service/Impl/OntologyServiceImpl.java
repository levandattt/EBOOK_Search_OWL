package com.ebook_searching.ontology.service.Impl;

import com.ebook_searching.ontology.constants.SpartQueryConstant;
import com.ebook_searching.ontology.model.Ontology.OWLClassProperty;
import com.ebook_searching.ontology.model.Ontology.OWLIndividual;
import com.ebook_searching.ontology.model.Ontology.OWLObjectProperty;
import com.ebook_searching.ontology.repository.OntologyRepository;
import com.ebook_searching.ontology.service.OntologyService;
import com.ebook_searching.ontology.service.SparqlService;
import com.ebook_searching.ontology.model.Ontology.OWLObjectProperty;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.ebook_searching.proto.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class OntologyServiceImpl implements OntologyService {
    @Autowired
    private OntologyRepository ontologyRepository;
    private SparqlService sparqlService;

    @Value("${ontology.ebook}")
    private String ebookPath;

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
//        filePath.add(tboxPath);
//        filePath.add(aboxPath);
        filePath.add(ebookPath);
        System.out.println("hihihihi");
        //load ontology from file
        ontologyRepository.loadOntologyFromFile(filePath);
    }

    @Override
    public void saveBook(Event.AddBookEvent bookEvent) {
        ontologyRepository.saveBook(bookEvent);
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
            return json;
        });
    }

    @Override
    public String query(List<String> keywords) {
        String sparqlQueryString = queryBuilder(keywords);
        if (sparqlQueryString.isEmpty()){
            return "No result";
        }
        return ontologyRepository.transaction(ReadWrite.READ, model -> {
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

    public String queryBuilder(List<String> keywords){
        try {
            //GET DATA BEFORE PROCESS
            // Lowercase the list
            keywords.replaceAll(String::toLowerCase);

            // Remove duplicates

            List<String> classList = new ArrayList<>();

            // GET CLASS
            List<OWLClassProperty> classes = getClasses(keywords);

            // Remove keywords that are classes
            classes.forEach((item) -> {
                if (keywords.contains(item.getLabel().toLowerCase())) {
                    keywords.remove(item.getLabel().toLowerCase());
                }
                classList.add(item.getClassName());
            });

            // GET CLASS BY DATA PROPERTY
            List<OWLIndividual> individuals = getClassesByDataProperty(keywords);
            individuals.forEach((item) -> {
                if (keywords.contains(item.getLabel().toLowerCase())) {
                    keywords.remove(item.getLabel().toLowerCase());
                }
                if (!classList.contains(item.getClassName())) {
                    classList.add(item.getClassName());
                }
            });

            System.out.println("classList: " + classList);


            //GET OBJECT PROPERTIES BETWEEN CLASSES
            List <OWLObjectProperty> objectProperties;
            if (classList.size()>=2){
                objectProperties = getClassesByObjectProperty(classList);
            }else {
                objectProperties = new ArrayList<>();
            }
            System.out.println("objectProperties: " + objectProperties);



            if (classes.size()>0 && individuals.size()==0 ){
                return  SpartQueryConstant.QUERY_SINGLE_CLASS(classList);
            }
            if (individuals.size()>0 && objectProperties.size()>0){
                String query = SpartQueryConstant.QUERY_BY_OBJECTPROPERTY_N_DATAPROPERTY_N_CLASS(classList, objectProperties, individuals);
                System.out.println("query: " + query);
                return query;
            }
//            if (mapClassIndividual.size()<=0 && objectProperties.size()>0){
//                return  SpartQueryConstant.QUERY_BY_OBJECTPROPERTY_BETWEEN_CLASSES(objectProperties);
//            }
//            if (mapClassIndividual.size()> 0 && objectProperties.size()>0){
//                return  SpartQueryConstant.QUERY_BY_OBJECTPROPERTY_BETWEEN_INDIVIDUAL_AND_CLASS(objectProperties, classes, mapClassIndividual);
//            }
//            if (classes.size()>0 && objectProperties.size()==0 && mapClassIndividual.size()>0){
//                return  SpartQueryConstant.QUERY_SINGLE_INDIVIDUAL(mapClassIndividual);
//            }
            return "";
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    public List<OWLIndividual> getClassesByDataProperty(List<String> list) {
        String spartQueryClass = SpartQueryConstant.GET_CLASS_BY_DATAPROPERTIES(list);
        //check class
        return ontologyRepository.transaction(ReadWrite.READ, model -> {
            List<OWLIndividual> individuals = new ArrayList<>();
            Query query = QueryFactory.create(spartQueryClass);
            try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
                ResultSet results = qexec.execSelect();
                while (results.hasNext()) {
                    QuerySolution soln = results.nextSolution();
                    OWLIndividual individual = new OWLIndividual();
                    individual.setIndividualName(soln.getLiteral("individualName").getString());
                    individual.setIndividualURI(soln.getResource("individualURI").getURI());
                    individual.setClassURI(soln.getResource("classURI").getURI());
                    individual.setClassName(soln.getLiteral("className").getString());
                    individual.setLabel(soln.getLiteral("label").getString());
                    individuals.add(individual);
                }
            }
            return individuals;
        });
    }

    public List<OWLClassProperty> getClasses(List<String> list) {
        String spartQueryClass = SpartQueryConstant.CHECK_LIST_CLASS(list);

        //check class
        return ontologyRepository.transaction(ReadWrite.READ, model -> {
            List<OWLClassProperty> classLabels = new ArrayList<>();
            Query query = QueryFactory.create(spartQueryClass);
            try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
                ResultSet results = qexec.execSelect();
                while (results.hasNext()) {
                    QuerySolution soln = results.nextSolution();
                    Literal label = soln.getLiteral("label");
                    Resource classURI = soln.getResource("uri");
                    Literal classID = soln.getLiteral("className");
                    OWLClassProperty classProperty = new OWLClassProperty();
                    classProperty.setLabel(label.getString());
                    classProperty.setClassURI(classURI.getURI());
                    classProperty.setClassName(classID.getString());
                    classLabels.add(classProperty);
                }
            }
            return classLabels;
        });
    }

    public List<OWLObjectProperty> getClassesByObjectProperty(List<String> classes) {
        String classA = classes.get(0);
        String classB = classes.get(1);
        String spartQueryClass = SpartQueryConstant.GET_OBJECTPROPERTIES_BETWEEN_CLASSES(classes);
        //check class
        return ontologyRepository.transaction(ReadWrite.READ, model -> {
            List<OWLObjectProperty> classLabels = new ArrayList<>();
            Query query = QueryFactory.create(spartQueryClass);
            try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
                ResultSet results = qexec.execSelect();
                while (results.hasNext()) {
                    QuerySolution soln = results.nextSolution();
                    Literal name = soln.getLiteral("propertyName");
                    Literal domain = soln.getLiteral("domainName");
                    Literal range = soln.getLiteral("rangeName");
                    OWLObjectProperty objectProperty = new OWLObjectProperty();
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

