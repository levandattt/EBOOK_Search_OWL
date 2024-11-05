package com.ebook_searching.ontology.migrations;

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
public class V4_AddAllLabels implements MigrationManager.Migration {
    @Value("${ontology.tdb2.directory}")
    private String TDB_DIRECTORY;

    @Value("${ontology.domain}")
    private String baseUri;

    @Autowired
    private URIBuilder uriBuilder;

    public V4_AddAllLabels() {
    }

    public void up() {
        Dataset dataset = TDBFactory.createDataset(TDB_DIRECTORY);

        Model model = dataset.getDefaultModel();

        // Create Ebook Class
        Property publishedBy = model.getProperty(uriBuilder.buildClassPropertyURI(baseUri, "publishedBy"));

        publishedBy.addProperty(RDFS.label, model.createLiteral("of publisher", "en"));
        publishedBy.addProperty(RDFS.label, model.createLiteral("of", "en"));
        publishedBy.addProperty(RDFS.label, model.createLiteral("của nhà xuất bản", "vi"));

        // Create Ebook Class
        Property publicationTime = model.getProperty(uriBuilder.buildClassPropertyURI(baseUri, "publicationTime"));
        publicationTime.addProperty(RDFS.label, model.createLiteral("published at", "en"));
        publicationTime.addProperty(RDFS.label, model.createLiteral("published in", "en"));
        publicationTime.addProperty(RDFS.label, model.createLiteral("published", "en"));
        publicationTime.addProperty(RDFS.label, model.createLiteral("xuất bản vào", "vi"));

        Property writtenBy = model.getProperty(uriBuilder.buildClassPropertyURI(baseUri, "writtenBy"));
        writtenBy.addProperty(RDFS.label, model.createLiteral("written by", "en"));
        writtenBy.addProperty(RDFS.label, model.createLiteral("của", "vi"));
        writtenBy.addProperty(RDFS.label, model.createLiteral("của tác giả", "vi"));
        writtenBy.addProperty(RDFS.label, model.createLiteral("of", "en"));
        writtenBy.addProperty(RDFS.label, model.createLiteral("of author", "en"));

        Property hasWritten = model.getProperty(uriBuilder.buildClassPropertyURI(baseUri, "hasWritten"));
        hasWritten.addProperty(RDFS.label, model.createLiteral("has written", "en"));
        hasWritten.addProperty(RDFS.label, model.createLiteral("viết", "vi"));
        hasWritten.addProperty(RDFS.label, model.createLiteral("đã viết", "vi"));
        hasWritten.addProperty(RDFS.label, model.createLiteral("tác giả của", "vi"));
        hasWritten.addProperty(RDFS.label, model.createLiteral("wrote", "en"));

        Property belongsToGenre = model.getProperty(uriBuilder.buildClassPropertyURI(baseUri, "belongsToGenre"));
        belongsToGenre.addProperty(RDFS.label, model.createLiteral("belongs to genre", "en"));
        belongsToGenre.addProperty(RDFS.label, model.createLiteral("of genre", "en"));
        belongsToGenre.addProperty(RDFS.label, model.createLiteral("thuộc thể loại", "vi"));
        belongsToGenre.addProperty(RDFS.label, model.createLiteral("thể loại", "vi"));

        Property containsBooks = model.getProperty(uriBuilder.buildClassPropertyURI(baseUri, "containsBooks"));
        containsBooks.addProperty(RDFS.label, model.createLiteral("is genre of", "en"));
        containsBooks.addProperty(RDFS.label, model.createLiteral("genre of", "en"));
        containsBooks.addProperty(RDFS.label, model.createLiteral("thể loại của", "vi"));

        Property authorStageName = model.getProperty(uriBuilder.buildClassPropertyURI(baseUri, "authorStageName"));
        authorStageName.addProperty(RDFS.label, model.createLiteral("has stage name", "en"));
        authorStageName.addProperty(RDFS.label, model.createLiteral("stage name", "en"));
        authorStageName.addProperty(RDFS.label, model.createLiteral("stage name is", "en"));
        authorStageName.addProperty(RDFS.label, model.createLiteral("nghệ danh", "vi"));
        authorStageName.addProperty(RDFS.label, model.createLiteral("có nghệ danh", "vi"));

        Property authorBirthDate = model.getProperty(uriBuilder.buildClassPropertyURI(baseUri, "authorBirthDate"));
        authorBirthDate.addProperty(RDFS.label, model.createLiteral("was borned at", "en"));
        authorBirthDate.addProperty(RDFS.label, model.createLiteral("has birthdate", "en"));
        authorBirthDate.addProperty(RDFS.label, model.createLiteral("birthdate is", "en"));
        authorBirthDate.addProperty(RDFS.label, model.createLiteral("sinh vào", "vi"));
        authorBirthDate.addProperty(RDFS.label, model.createLiteral("sinh vào ngày", "vi"));

        Property authorBirthPlace = model.getProperty(uriBuilder.buildClassPropertyURI(baseUri, "authorBirthPlace"));
        authorBirthPlace.addProperty(RDFS.label, model.createLiteral("was borned at", "en"));
        authorBirthPlace.addProperty(RDFS.label, model.createLiteral("was borned in", "en"));
        authorBirthPlace.addProperty(RDFS.label, model.createLiteral("birth place", "en"));
        authorBirthPlace.addProperty(RDFS.label, model.createLiteral("sinh tại", "vi"));
        authorBirthPlace.addProperty(RDFS.label, model.createLiteral("nơi sinh", "vi"));

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
        return 4;
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
