package com.ebook_searching.ontology.service.Impl;

import com.ebook_searching.ontology.constants.SpartQueryConstant;
import com.ebook_searching.ontology.model.Ontology.OWLAuthor;
import com.ebook_searching.ontology.model.Ontology.OWLBook;
import com.ebook_searching.ontology.model.Ontology.OWLIndividual;
import com.ebook_searching.ontology.model.Ontology.OWLQueryResult;
import com.ebook_searching.ontology.repository.OntologyRepository;
import com.ebook_searching.ontology.service.SentenceAnalyzerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SentenceAnalyzerServiceImpl implements SentenceAnalyzerService {
    @Autowired
    private OntologyRepository ontologyRepository;

    @Override
    public List<String> readListFromFile(String filePath) {
        List<String> dataList = new ArrayList<>();
        File file = new File(filePath);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                dataList.add(line.trim().toLowerCase());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dataList;
    }

    @Override
    public List<String> analyzeSentence(String sentence1) {
        String filePath = "OntologyService/src/main/resources/data/SentenceAnalyzeData/OntologyKeyword.txt";
        //SAVE KEYWORD TO FILE
        getKeywordFromSentence();
        List<String> dataList = readListFromFile(filePath);
        List<String> keywordList = new ArrayList<>();
        String sentence = sentence1.concat(" ");
        String[] words = sentence.toLowerCase().split("\\s+");

        for (int i = 0; i < words.length; i++) {
            for (int length = words.length - i; length > 0; length--) {
                String phrase = String.join(" ", Arrays.copyOfRange(words, i, i + length));
                if (dataList.contains(phrase)) {
                    keywordList.add(phrase);
                    break;
                }
            }
        }
        return keywordList;
    }

    public Set<String> getKeywordOfClassNLabel() {
        //Class + DatatypeProperty
        return ontologyRepository.transaction(ReadWrite.READ, model -> {
            String spartQueryClass = SpartQueryConstant.GET_KEYWORDS_OF_CLASS_N_LABEL();
            Query query = QueryFactory.create(spartQueryClass);
            Set<String> keywords = new HashSet<>();
            try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
                ResultSet results = qexec.execSelect();
                while (results.hasNext()) {
                    QuerySolution soln = results.nextSolution();
                    String className = soln.getLiteral("className").getString();
                    String labels = soln.getLiteral("labels").getString();
                    List<String> labelsSplit = Arrays.asList(labels.split("\\|\\|"));
                    keywords.add(className);
                    keywords.addAll(labelsSplit);
                }
            }
            return keywords;
        });
    }

    public Set<String> getKeywrordOfObjectProperty() {
        //ObjectProperty
        return ontologyRepository.transaction(ReadWrite.READ, model -> {
            String spartQueryObjectProperty = SpartQueryConstant.GET_KEYWORDS_OF_OBJECT_PROPERTY();
            Query query = QueryFactory.create(spartQueryObjectProperty);
            Set<String> keywords = new HashSet<>();
            try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
                ResultSet results = qexec.execSelect();
                while (results.hasNext()) {
                    QuerySolution soln = results.nextSolution();
                    // Kiểm tra null trước khi lấy giá trị
                    if (soln.contains("objectPropertyName") && soln.getLiteral("objectPropertyName") != null) {
                        String objectProperty = soln.getLiteral("objectPropertyName").getString();
                        keywords.add(objectProperty);
                    }

                    if (soln.contains("labels") && soln.getLiteral("labels") != null) {
                        String labels = soln.getLiteral("labels").getString();
                        List<String> labelSet = Arrays.asList(labels.split("\\|\\|"));
                        keywords.addAll(labelSet);
                    }
                }
            }
            return keywords;
        });
    }

    public Set<String> getKeywordOfIndividual() {
        //Individual
        return ontologyRepository.transaction(ReadWrite.READ, model -> {
            String spartQueryIndividual = SpartQueryConstant.GET_KEYWORDS_OF_INDIVIDUAL_N_DATA();
            Query query = QueryFactory.create(spartQueryIndividual);
            Set<String> keywords = new HashSet<>();
            try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
                ResultSet results = qexec.execSelect();
                while (results.hasNext()) {
                    QuerySolution soln = results.nextSolution();
                    // Kiểm tra null trước khi lấy giá trị
                    if (soln.contains("individualName") && soln.getLiteral("individualName") != null) {
                        String objectProperty = soln.getLiteral("individualName").getString();
                        keywords.add(objectProperty);
                    }

                    if (soln.contains("properties") && soln.getLiteral("properties") != null) {
                        String properties = soln.getLiteral("properties").getString();
                        Set<String> labelSet = Arrays.stream(properties.split("\\|\\|"))
                                .map(String::trim)
                                .flatMap(pair -> Arrays.stream(pair.split("=")))
                                .map(String::trim)
                                .collect(Collectors.toSet());
                        keywords.addAll(labelSet);
                    }
                }
            }
            return keywords;
        });

    }

    public void getKeywordFromSentence() {
        Set<String> keywords = new HashSet<>();
        //Class + DatatypeProperty
        keywords.addAll(getKeywordOfClassNLabel());
        //ObjectProperty
        keywords.addAll(getKeywrordOfObjectProperty());
        //Individual
        keywords.addAll(getKeywordOfIndividual());
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("OntologyService/src/main/resources/data/SentenceAnalyzeData/OntologyKeyword.txt"))) {
            writer.write(keywords.stream().collect(Collectors.joining(System.lineSeparator())));
        } catch (IOException e) { e.printStackTrace(); }
    }

}
