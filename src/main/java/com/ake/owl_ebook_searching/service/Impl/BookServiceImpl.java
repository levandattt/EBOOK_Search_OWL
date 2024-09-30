package com.ake.owl_ebook_searching.service.Impl;

import com.ake.owl_ebook_searching.constant.SpartQueryConstant;
import com.ake.owl_ebook_searching.payload.QueryRes;
import com.ake.owl_ebook_searching.service.BookService;
import com.ake.owl_ebook_searching.model.Book;
import com.ake.owl_ebook_searching.service.OntologyService;
import com.ake.owl_ebook_searching.util.QueryMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.jena.base.Sys;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private  OntologyService ontologyService;

    @Override
//    public List<Book> searchBooksByAuthor(String authorName) {
    public QueryRes<List<Book>> searchBooksByAuthor(String authorName) {

        List<Book> books = new ArrayList<>();
        Map<String, Object> resultMap = new HashMap<>();

        Model model = ontologyService.getModel();

        String sparqlQueryString = SpartQueryConstant.SEARCH_BOOK_BY_AUTHOR(authorName);

        Query query = QueryFactory.create(sparqlQueryString);
        try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
            ResultSet results = qexec.execSelect();

            while (results.hasNext()) {
                QuerySolution soln = results.nextSolution();
                // Map QuerySolution to Book object
                Book book = QueryMapper.mapToObject(soln, Book.class);
                books.add(book);

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

        return QueryRes.<List<Book>>builder()
                .data(books)
                .rawQueryData(resultMap)
                .build();
    }
}
