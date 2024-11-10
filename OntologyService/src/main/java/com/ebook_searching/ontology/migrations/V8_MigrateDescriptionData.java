package com.ebook_searching.ontology.migrations;

import com.ebook_searching.ontology.constants.ClassNames;
import com.ebook_searching.ontology.utils.URIBuilder;
import org.apache.jena.query.Dataset;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.tdb.TDBFactory;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;

@Component
public class V8_MigrateDescriptionData implements MigrationManager.Migration {
    @Value("${ontology.tdb2.directory}")
    private String TDB_DIRECTORY;

    @Value("${ontology.domain}")
    private String baseUri;

    @Autowired
    private URIBuilder uriBuilder;

    public V8_MigrateDescriptionData() {
    }

    public void up() {
        Dataset dataset = TDBFactory.createDataset(TDB_DIRECTORY);

        Model model = dataset.getDefaultModel();

        // Create Ebook Class
        Resource book = model.getResource(uriBuilder.buildIndividualURI(baseUri, "Cong nghe tri thuc", "73"));
        Property descriptionProperty = model.getProperty(uriBuilder.buildClassPropertyURI(baseUri, "description"));
        book.addProperty(descriptionProperty, "this book is about the personal life of Quang Chu");

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
        return 8;
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
