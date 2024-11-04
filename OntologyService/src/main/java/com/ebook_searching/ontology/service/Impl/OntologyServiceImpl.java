package com.ebook_searching.ontology.service.Impl;

import com.ebook_searching.ontology.constants.SpartQueryConstant;
import com.ebook_searching.ontology.model.Ontology.*;
import com.ebook_searching.ontology.payload.OntologySearchReq;
import com.ebook_searching.ontology.repository.OntologyRepository;
import com.ebook_searching.ontology.service.*;
import com.ebook_searching.ontology.model.Ontology.OWLObjectProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Resource;
import org.ebook_searching.proto.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;


@Service
public class OntologyServiceImpl implements OntologyService {
    @Autowired
    private OntologyRepository ontologyRepository;

    @Autowired
    private JsonParserService parserService ;

    private SparqlService sparqlService;

    @Value("${ontology.ebook}")
    private String ebookPath;

    @Value("${ontology.tbox.path}")
    private String tboxPath;

    @Value("${ontology.abox.path}")
    private String aboxPath;
    @Autowired
    private SparqlServiceImpl sparqlServiceImpl;
    @Autowired
    private SentenceService sentenceService;


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
    public OWLQueryResult search(OntologySearchReq ontologySearchReq) {
        try {
            List<String> keywords = sentenceService.analyzeSentence(ontologySearchReq.getKeyword());
            OWLQueryResult result = query(keywords, ontologySearchReq);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
       return null;
    }


    @Override
    public OWLQueryResult query(List<String> keywords, OntologySearchReq ontologySearchReq) {
        String sparqlQueryString = queryBuilder(keywords, ontologySearchReq);
        if (sparqlQueryString == null) {
            return null;
        }

        OWLQueryResult result  = ontologyRepository.transaction(ReadWrite.READ, model -> {
            Query query = QueryFactory.create(sparqlQueryString);
            QueryExecution qexec = QueryExecutionFactory.create(query, model);
            ResultSet results = qexec.execSelect();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ResultSetFormatter.outputAsJSON(outputStream, results);
            String jsonString = new String(outputStream.toByteArray());
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode root = objectMapper.readTree(jsonString);
                List<OWLAuthor> authors = parserService.parseAuthors(root);
                List<OWLBook> books = parserService.parseBooks(root);
                OWLQueryResult queryResult = new OWLQueryResult();
                queryResult.setBooks(books);
                queryResult.setAuthors(authors);
                return queryResult;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return null;
            }
        });
        return result;
    }

    @Override
    public void saveAuthor(Event.Author message) {
        ontologyRepository.saveAuthor(message);
    }

    @Override
    public void updateAuthor(Event.Author message) {
        ontologyRepository.updateAuthor(message);
    }

    @Override
    public void updateBook(Event.AddBookEvent message) {
        ontologyRepository.updateBook(message);
    }

    @Override
    public void deleteAuthor(Event.Author author) {
        ontologyRepository.deleteAuthor(author);
    }

    @Override
    public void deleteBook(Event.AddBookEvent book) {
        ontologyRepository.deleteBook(book);
    }

    public String queryBuilder(List<String>keywords, OntologySearchReq ontologySearchReq) {
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

            //GET OBJECT PROPERTIES BETWEEN CLASSES
            List <OWLObjectProperty> objectProperties;
            if (classList.size()>=2){
                objectProperties = getClassesByObjectProperty(classList);
            }else {
                objectProperties = new ArrayList<>();
            }

            //get object properties by label
            List<OWLObjectProperty> objectPropertiesByLabel = new ArrayList<>();
            if (keywords.size()>0){
                objectPropertiesByLabel = getObjectPropertiesByLabel(keywords);
            }

            //ONTOLOGY CONDITION
            if (objectPropertiesByLabel.size()==1){
                if(classes.size()==1 && individuals.size()==1){
                    OWLClassProperty classA = classes.get(0);
                    OWLIndividual classB = individuals.get(0);
                    OWLObjectProperty objectPropertyQuery = new OWLObjectProperty();
                    if(objectPropertiesByLabel.get(0).getDomain().equals(classA.getClassName()) && objectPropertiesByLabel.get(0).getRange().equals(classB.getClassName())){
                        objectPropertyQuery.setDomain(classA.getClassName());
                        objectPropertyQuery.setRange(classB.getIndividualName());
                        objectPropertyQuery.setName(objectPropertiesByLabel.get(0).getName());
                    }
                    return SpartQueryConstant.QUERY_INDIVIDUAL_OF_DOMAIN_BY_OBJECTPROPERTY(objectPropertyQuery);
                }
            }
            if ((!(classes.size()>0) && individuals.size()==1 && objectProperties.size()==0)){
                System.out.println("CONDITION 1");
                return SpartQueryConstant.QUERY_SINGLE_INDIVIDUAL(individuals.get(0));
            }
            if (classes.size()>0 && individuals.size()==0 ){
                System.out.println("CONDITION 2");
                return  SpartQueryConstant.QUERY_SINGLE_CLASS(classList);

            }
            if (individuals.size()>0 && objectProperties.size()>0){
                System.out.println("CONDITION 3");
                return  SpartQueryConstant.QUERY_BY_OBJECTPROPERTY_N_DATAPROPERTY_N_CLASS(classList, objectProperties, individuals);
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
//            return "";
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public List<OWLObjectProperty> getObjectPropertiesByLabel(List<String> keywords){
        String spartQueryClass = SpartQueryConstant.GET_OBJECTPROPERTIES_BY_LABEL(keywords);
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

