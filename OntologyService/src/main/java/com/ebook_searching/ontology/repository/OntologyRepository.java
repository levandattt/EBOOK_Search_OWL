package com.ebook_searching.ontology.repository;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.tdb.TDBFactory;
import org.apache.jena.util.FileManager;
import org.ebook_searching.common.mapper.DateMapper;
import org.ebook_searching.proto.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.function.Function;

@Repository
public class OntologyRepository {
    @Value("${ontology.tdb2.directory}")
    private  String TDB_DIRECTORY;

    @Value("${domain}")
    private String domain;

    @Autowired
    private DateMapper dateMapper;

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

    public void saveBook(Event.AddBookEvent book) {
        Dataset dataset = TDBFactory.createDataset(TDB_DIRECTORY);
        dataset.begin(ReadWrite.WRITE);
        try {
            Model model = dataset.getDefaultModel(); // Get default RDF model

            // Create the book resource
            Resource bookOntology = model.createResource("http://example.org/book/" + book.getId());
            bookOntology.addProperty(model.createProperty("http://example.org/title"), book.getTitle())
                    .addProperty(model.createProperty("http://example.org/genre"), book.getGenre())
                    .addProperty(model.createProperty("http://example.org/publisher"), book.getPublisher())
                    .addProperty(model.createProperty("http://example.org/language"), book.getLanguage())
                    .addProperty(model.createProperty("http://example.org/avgRatings"),
                            String.valueOf(book.getAvgRatings()))
                    .addProperty(model.createProperty("http://example.org/ratingsCount"),
                            String.valueOf(book.getRatingsCount()))
                    .addProperty(model.createProperty("http://example.org/publishedAt"), String.valueOf(dateMapper.map(book.getPublishedAt())));

//            // Add authors as properties
//            Property authorProperty = model.createProperty("http://example.org/author");
//            for (Long authorId : authorIds) {
//                bookOntology.addProperty(authorProperty, model.createResource("http://example.org/author/" + authorId));
//            }

            // Commit the transaction
            dataset.commit();
        } catch (Exception e) {
            dataset.abort(); // Rollback in case of failure
            throw new RuntimeException("Failed to save book to the ontology", e);
        } finally {
            dataset.end(); // End the transaction
        }
    }

    public  <T> T transaction(ReadWrite readWriteMode, Function<Model, T> action) {
        Dataset dataset = TDBFactory.createDataset(TDB_DIRECTORY);
        dataset.begin(readWriteMode);
        try {
            Model model = dataset.getDefaultModel();
            // Tạo lý luận OWL
            Reasoner reasoner = ReasonerRegistry.getOWLReasoner();

            // Tạo mô hình suy luận dựa trên lý luận và mô hình RDF hiện có
            InfModel infModel = ModelFactory.createInfModel(reasoner, model);
            //model dungf reasoner
            T result = action.apply(infModel);
            //model khum dùng reasoner
//            T result = action.apply(model);
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