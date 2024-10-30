package com.ebook_searching.ontology.migrations;

import com.ebook_searching.ontology.constants.ClassNames;
import com.ebook_searching.ontology.utils.URIBuilder;
import org.apache.jena.query.Dataset;
import org.apache.jena.rdf.model.*;
import org.apache.jena.tdb.TDBFactory;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.apache.jena.vocabulary.XSD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;

@Component
public class V1_InitOntology implements MigrationManager.Migration {
    @Value("${ontology.tdb2.directory}")
    private String TDB_DIRECTORY;

    @Value("${ontology.domain}")
    private String baseUri;

    @Autowired
    private URIBuilder uriBuilder;

    public V1_InitOntology() {
    }

    public void up() {
        Dataset dataset = TDBFactory.createDataset(TDB_DIRECTORY);

        Model model = dataset.getDefaultModel();
        // should only use for v1 only
        clearModel(model);

        // Define the ontology itself
        model.createResource(baseUri, OWL.Ontology);

        // Create Ebook Class
        Resource ebookClass = model.createResource(uriBuilder.buildClassURI(baseUri, ClassNames.BOOK), OWL.Class);
        // Add multiple labels in different languages to the "Ebook" class
        ebookClass.addProperty(RDFS.label, model.createLiteral("Ebook", "en"));
        ebookClass.addProperty(RDFS.label, model.createLiteral("Book", "en"));
        ebookClass.addProperty(RDFS.label, model.createLiteral("Sách", "vi"));
        ebookClass.addProperty(RDFS.label, model.createLiteral("Sách Điện Tử", "vi"));

        // Create the class resource URI for "Ebook"
        Resource authorClass = model.createResource(uriBuilder.buildClassURI(baseUri, ClassNames.AUTHOR), OWL.Class);
        // Add multiple labels in different languages to the "Ebook" class
        authorClass.addProperty(RDFS.label, model.createLiteral("Author", "en"));
        authorClass.addProperty(RDFS.label, model.createLiteral("Tác giả", "en"));

        // Create the class resource URI for "Ebook"
        Resource genreClass = model.createResource(uriBuilder.buildClassURI(baseUri, ClassNames.GENRE), OWL.Class);
        // Add multiple labels in different languages to the "Ebook" class
        genreClass.addProperty(RDFS.label, model.createLiteral("Genre", "en"));
        genreClass.addProperty(RDFS.label, model.createLiteral("Thể loại", "vi"));

        // Create the "writtenBy" object property
        Property writtenBy = model.createProperty(uriBuilder.buildClassPropertyURI(baseUri, "writtenBy"));
        writtenBy.addProperty(RDF.type, OWL.ObjectProperty);
        writtenBy.addProperty(RDFS.domain, ebookClass);
        writtenBy.addProperty(RDFS.range, authorClass);

        // Create the "hasWritten" inverse property
        Property hasWritten = model.createProperty(uriBuilder.buildClassPropertyURI(baseUri, "hasWritten"));
        hasWritten.addProperty(RDF.type, OWL.ObjectProperty);
        hasWritten.addProperty(OWL.inverseOf, writtenBy);

        // Add inverse relationship
        writtenBy.addProperty(OWL.inverseOf, hasWritten);


        // Create the "belongsToGenre" object property
        Property belongsToGenre = model.createProperty(uriBuilder.buildClassPropertyURI(baseUri, "belongsToGenre"));
        belongsToGenre.addProperty(RDF.type, OWL.ObjectProperty);
        belongsToGenre.addProperty(RDFS.domain, ebookClass);
        belongsToGenre.addProperty(RDFS.range, genreClass);

        // Create the "containsBooks" inverse property
        Property containsBooks = model.createProperty(uriBuilder.buildClassPropertyURI(baseUri, "containsBooks"));
        containsBooks.addProperty(RDF.type, OWL.ObjectProperty);
        containsBooks.addProperty(OWL.inverseOf, belongsToGenre);

        // Add inverse relationship
        belongsToGenre.addProperty(OWL.inverseOf, containsBooks);

        // Create the "hasGenre" object property
        Property hasGenre = model.createProperty(uriBuilder.buildClassPropertyURI(baseUri, "hasGenre"));
        hasGenre.addProperty(RDF.type, OWL.ObjectProperty);
        hasGenre.addProperty(RDFS.domain, authorClass);
        hasGenre.addProperty(RDFS.range, genreClass);

        // Create the "genreOf" inverse property
        Property genreOf = model.createProperty(uriBuilder.buildClassPropertyURI(baseUri, "genreOf"));
        genreOf.addProperty(RDF.type, OWL.ObjectProperty);
        genreOf.addProperty(OWL.inverseOf, hasGenre);

        // Add inverse relationship
        hasGenre.addProperty(OWL.inverseOf, genreOf);

        // Create datatype properties for the "Book" class
        createDatatypeProperty(model, "title", ebookClass, XSD.xstring);
        createDatatypeProperty(model, "avgRating", ebookClass, XSD.xfloat);
        createDatatypeProperty(model, "ratingCount", ebookClass, XSD.xint);
        createDatatypeProperty(model, "reviewCount", ebookClass, XSD.xint);
        createDatatypeProperty(model, "category", ebookClass, XSD.xstring);
        createDatatypeProperty(model, "publisher", ebookClass, XSD.xstring);
        createDatatypeProperty(model, "language", ebookClass, XSD.xstring);
        createDatatypeProperty(model, "description", ebookClass, XSD.xstring);
        createDatatypeProperty(model, "publicationTime", ebookClass, XSD.date);
        createDatatypeProperty(model, "totalPages", ebookClass, XSD.xint);

        // Create datatype properties for the "Author" class
        createDatatypeProperty(model, "authorName", authorClass, XSD.xstring);
        createDatatypeProperty(model, "authorStageName", authorClass, XSD.xstring);
        createDatatypeProperty(model, "authorNationality", authorClass, XSD.xstring);
        createDatatypeProperty(model, "authorBirthDate", authorClass, XSD.date);
        createDatatypeProperty(model, "authorBirthPlace", authorClass, XSD.xstring);
        createDatatypeProperty(model, "authorDeathDate", authorClass, XSD.date);
        createDatatypeProperty(model, "authorWebsite", authorClass, XSD.xstring);
        createDatatypeProperty(model, "authorDescription", authorClass, XSD.xstring);
        createDatatypeProperty(model, "authorImage", authorClass, XSD.xstring);

        // Create datatype properties for the "Genre" class
        createDatatypeProperty(model, "genreName", genreClass, XSD.xstring);
        createDatatypeProperty(model, "genreDescription", genreClass, XSD.xstring);
        // Optionally write model to output
        writeModelToFile(model);
    }

    private void createDatatypeProperty(Model model, String propertyName, Resource domain, Resource range) {
        Property property = model.createProperty(uriBuilder.buildClassPropertyURI(baseUri, propertyName));
        property.addProperty(RDF.type, OWL.DatatypeProperty);
        property.addProperty(RDFS.domain, domain);
        property.addProperty(RDFS.range, range);
    }


    @Override
    public int getVersion() {
        return 1;
    }

    private void clearModel(Model model) {
        // Remove all statements from the model to ensure a clean ontology
        model.removeAll();
    }

    public void down() {
//        String baseUri = "http://www.ebook-searching.org/ontology#";
//        Resource ebookClass = model.getResource(baseUri + "Ebook");
//        model.removeAll(ebookClass, null, null);
    }


    private void writeModelToFile(Model model) {
        try (FileOutputStream out = new FileOutputStream("ontology.owl")) {
            model.write(out, "RDF/XML");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
