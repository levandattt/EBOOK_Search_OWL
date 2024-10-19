package com.ebook_searching.ontology.service.Impl;

import com.ebook_searching.ontology.repository.OntologyRepository;
import com.ebook_searching.ontology.service.SparqlService;
import org.apache.jena.query.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SparqlServiceImpl implements SparqlService {
    @Autowired
    private OntologyRepository ontologyRepository;

    public Map<String, Object>  executeSparqlQuery(String sparqlQueryString) {
        return ontologyRepository.transaction(model -> {
            Query query = QueryFactory.create(sparqlQueryString);
            QueryExecution qexec = QueryExecutionFactory.create(query, model);
            ResultSet results = qexec.execSelect();
            List<String> classes = new ArrayList<>();

            Map<String, Object> resultMap = new HashMap<>();
            while (results.hasNext()) {
                QuerySolution soln = results.nextSolution();
                if (soln.contains("class")) {
                    classes.add(soln.getResource("class").getURI());
                }
                Map<String, String> data = new HashMap<>();
                soln.varNames().forEachRemaining(varName -> {
                    if (soln.get(varName).isLiteral()) {
                        data.put(varName, soln.getLiteral(varName).getString());
                    } else if (soln.get(varName).isResource()) {
                        data.put(varName, soln.getResource(varName).getURI());
                    }
                });
                resultMap.put("data", data);
            }
            return resultMap;
        });
//        try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
//            ResultSet results = qexec.execSelect();
//
//            while (results.hasNext()) {
//                QuerySolution soln = results.nextSolution();
//
//                // Map QuerySolution to Map
//                Map<String, String> data = new HashMap<>();
//                soln.varNames().forEachRemaining(varName -> {
//                    if (soln.get(varName).isLiteral()) {
//                        data.put(varName, soln.getLiteral(varName).getString());
//                    } else if (soln.get(varName).isResource()) {
//                        data.put(varName, soln.getResource(varName).getURI());
//                    }
//                });
//                resultMap.put("data", data);
//            }
//        }catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
    }
}
