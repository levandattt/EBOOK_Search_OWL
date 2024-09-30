package com.ake.owl_ebook_searching.service.Impl;

import com.ake.owl_ebook_searching.model.Book;
import com.ake.owl_ebook_searching.service.OntologyService;
import com.ake.owl_ebook_searching.service.SparqlService;
import com.ake.owl_ebook_searching.util.QueryMapper;
import org.apache.jena.base.Sys;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SparqlServiceImpl implements SparqlService {
    @Autowired
    private OntologyService ontologyService;

    public Map<String, Object>  executeSparqlQuery(String sparqlQueryString) {
        Model model = ontologyService.getModel();
        Query query = QueryFactory.create(sparqlQueryString);
        Map<String, Object> resultMap = new HashMap<>();
        try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
            ResultSet results = qexec.execSelect();

            while (results.hasNext()) {
                QuerySolution soln = results.nextSolution();

                // Map QuerySolution to Map
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
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return resultMap;
    }
}
