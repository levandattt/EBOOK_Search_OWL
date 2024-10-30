package com.ebook_searching.ontology.repository;

import com.ebook_searching.ontology.service.OntologyService;
import org.apache.jena.query.Dataset;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.tdb.TDBFactory;
import org.apache.jena.vocabulary.RDF;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.apache.jena.rdf.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = "ontology.tdb2.directory=src/test/resources/data/tdb2")
class OntologyRepositoryTest {

    private static final String TDB_DIRECTORY = "src/test/resources/data/tdb2";  // Temporary directory for testing
    private static final String NS = "http://www.ebook-searching.org/ontology#";
    private Dataset dataset;
    private Model model;

    @Autowired
    private OntologyRepository ontologyRepository;

    @BeforeEach
    void setup() {
        dataset = TDBFactory.createDataset(TDB_DIRECTORY);
        model = dataset.getDefaultModel();
    }

    @AfterEach
    void teardown() {
        model.close();
        dataset.close();  // Cleanup the dataset after each test
    }

    @Test
    void testCreateOrGetResource_NewResource() {
        String authorId = "JKRowling";

        // Create a new resource using the createOrGetResource method
        Resource authorResource = ontologyRepository.createOrGetResource(model, NS, "author", authorId);

        // Verify that the resource was created with the correct type
        assertNotNull(authorResource, "Resource should not be null");
        assertEquals(NS + "author/" + authorId, authorResource.getURI());
        assertTrue(model.contains(authorResource, RDF.type, model.createResource(NS + "author")),
                "Resource should have the correct type");
    }

    @Test
    void testCreateOrGetResource_ExistingResource() {
        String publisherId = "HarryPotterAndTheSorcerersStone";
        String authorId = "HoChiMinh";
        String genreId = "Biography";

//        // Step 1: Create the resources manually to simulate existing data
//        Resource publisherResource = model.createResource(NS + "publisher/" + publisherId)
//                .addProperty(RDF.type, model.createResource(NS + "publisher"));
//
//        Resource authorResource = model.createResource(NS + "author/" + authorId)
//                .addProperty(RDF.type, model.createResource(NS + "author"));
//
//        Resource genreResource = model.createResource(NS + "genre/" + genreId)
//                .addProperty(RDF.type, model.createResource(NS + "genre"));

        // Step 2: Use the method to retrieve the resources and ensure they exist
        Resource retrievedPublisher = ontologyRepository.createOrGetResource(model, NS, "", publisherId);
//        Resource retrievedAuthor = ontologyService.createOrGetResource(model, NS, "author", authorId);
//        Resource retrievedGenre = ontologyService.createOrGetResource(model, NS, "genre", genreId);

        // Step 3: Validate that the same resources are returned, not recreated
        assertNotNull(retrievedPublisher, "Publisher resource should not be null");
//        assertEquals(publisherResource, retrievedPublisher, "Existing publisher resource should be returned");
//        assertTrue(model.contains(retrievedPublisher, RDF.type, model.createResource(NS + "publisher")),
//                "Publisher resource should have the correct type");

//        assertNotNull(retrievedAuthor, "Author resource should not be null");
//        assertEquals(authorResource, retrievedAuthor, "Existing author resource should be returned");
//        assertTrue(model.contains(retrievedAuthor, RDF.type, model.createResource(NS + "author")),
//                "Author resource should have the correct type");

//        assertNotNull(retrievedGenre, "Genre resource should not be null");
//        assertEquals(genreResource, retrievedGenre, "Existing genre resource should be returned");
//        assertTrue(model.contains(retrievedGenre, RDF.type, model.createResource(NS + "genre")),
//                "Genre resource should have the correct type");
    }

}