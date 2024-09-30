package com.ake.owl_ebook_searching.service.Impl;

import com.ake.owl_ebook_searching.service.BookService;
import com.ake.owl_ebook_searching.model.Book;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private final String rdfFile = "src/main/resources/data/ebook.owl";

    @Override
    public List<Book> searchBooksByAuthor(String authorName) {
        List<Book> books = new ArrayList<>();
        Model model = ModelFactory.createDefaultModel();
        model.read(rdfFile);

        String sparqlQueryString = "PREFIX ex: <http://www.example.org/ebook#> " +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +  // thêm prefix rdfs
                "SELECT ?title ?author ?genre ?isbn " +
                "WHERE { " +
                "?book a ex:Ebook . " +
                "?book ex:isbn ?isbn . " +
                "?book ex:writtenBy ?author . " +
                "?book ex:belongsToGenre ?genre . " +
                "?author rdfs:label ?authorName . " +
                "FILTER (str(?authorName) = \"" + authorName + "\") " +
                "OPTIONAL { ?book rdfs:label ?title . } " +
                "}";
        Query query = QueryFactory.create(sparqlQueryString);
        try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                QuerySolution soln = results.nextSolution();
                Book book = new Book();

                // Kiểm tra null trước khi lấy giá trị
                if (soln.contains("title") && soln.getLiteral("title") != null) {
                    book.setTitle(soln.getLiteral("title").getString());
                }

                if (soln.contains("author") && soln.getResource("author") != null) {
                    book.setAuthor(soln.getResource("author").getURI());
                }

                if (soln.contains("genre") && soln.getResource("genre") != null) {
                    book.setGenre(soln.getResource("genre").getURI());
                }

                if (soln.contains("isbn") && soln.getLiteral("isbn") != null) {
                    book.setIsbn(soln.getLiteral("isbn").getString());
                }

                books.add(book);
            }
        }

        return books;
    }
}
