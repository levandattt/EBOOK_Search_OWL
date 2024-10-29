package com.ebook_searching.ontology.repository;

import com.ebook_searching.ontology.constants.ClassNames;
import com.ebook_searching.ontology.utils.URIBuilder;
import org.apache.jena.datatypes.RDFDatatype;
import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.datatypes.xsd.impl.XSDDateTimeType;
import org.apache.jena.datatypes.xsd.impl.XSDDateType;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.tdb.TDBFactory;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.apache.jena.vocabulary.XSD;
import org.ebook_searching.common.mapper.DateMapper;
import org.ebook_searching.common.utils.StringConverter;
import org.ebook_searching.common.utils.StringUtils;
import org.ebook_searching.proto.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.function.Function;

import static org.ebook_searching.common.constants.Common.ZERO;

@Repository
public class OntologyRepository {
    @Value("${ontology.tdb2.directory}")
    private String TDB_DIRECTORY;

    @Value("${domain}")
    private String domain = "http://www.ebook-searching.org/ebook";

    @Autowired
    private DateMapper dateMapper;

    @Autowired
    private URIBuilder uriBuilder;

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

    // Save a new book to the ontology in RDF/OWL format
    public void saveBook(Event.AddBookEvent book) {
        Dataset dataset = TDBFactory.createDataset(TDB_DIRECTORY);
        dataset.begin(ReadWrite.WRITE);
        try {
            Model model = dataset.getDefaultModel();
            Resource ebookClass;
            ebookClass = model.getResource(uriBuilder.buildClassURI(domain, ClassNames.BOOK));
            if (!model.containsResource(ebookClass)) {
                // Create the class resource URI for "Ebook"
                ebookClass = model.createResource(uriBuilder.buildClassURI(domain, ClassNames.BOOK), OWL.Class);

                // Add multiple labels in different languages to the "Ebook" class
                ebookClass.addProperty(RDFS.label, model.createLiteral("Ebook", "en"));
                ebookClass.addProperty(RDFS.label, model.createLiteral("Book", "en"));
                ebookClass.addProperty(RDFS.label, model.createLiteral("Sách", "vi"));
                ebookClass.addProperty(RDFS.label, model.createLiteral("Sách Điện Tử", "vi"));
            }

            Resource authorClass;
            authorClass = model.getResource(uriBuilder.buildClassURI(domain, ClassNames.AUTHOR));
            if (!model.containsResource(authorClass)) {
                // Create the class resource URI for "Ebook"
                authorClass = model.createResource(uriBuilder.buildClassURI(domain, ClassNames.AUTHOR), OWL.Class);

                // Add multiple labels in different languages to the "Ebook" class
                authorClass.addProperty(RDFS.label, model.createLiteral("Author", "en"));
                authorClass.addProperty(RDFS.label, model.createLiteral("Tác giả", "en"));
            }

            // Define namespaces used in the ontology
            Property titleProperty = model.createProperty(uriBuilder.buildClassPropertyURI(domain, "title"));
            Property avgRatingProperty = model.createProperty(uriBuilder.buildClassPropertyURI(domain, "avgRating"));
            Property ratingCountProperty = model.createProperty(uriBuilder.buildClassPropertyURI(domain, "ratingCount"));
            Property reviewCountProperty = model.createProperty(uriBuilder.buildClassPropertyURI(domain, "reviewCount"));
            Property publicationTimeProperty = model.createProperty(uriBuilder.buildClassPropertyURI(domain, "publicationTime"));
            Property totalPagesProperty = model.createProperty(uriBuilder.buildClassPropertyURI(domain, "totalPages"));
            Property publishedByProperty = model.createProperty(uriBuilder.buildClassPropertyURI(domain, "publishedBy"));

            Property writtenByProperty = model.createProperty(uriBuilder.buildClassRelationshipURI(domain, "writtenBy"));
            writtenByProperty.addProperty(RDF.type, OWL.ObjectProperty);
            // Set the domain of the property to "Ebook"
            writtenByProperty.addProperty(RDFS.domain, ebookClass);

            // Set the range of the property to "Author"
            writtenByProperty.addProperty(RDFS.range, authorClass);

            Property belongsToGenreProperty = model.createProperty(uriBuilder.buildClassRelationshipURI(domain, "belongsToGenre"));
            belongsToGenreProperty.addProperty(RDF.type, OWL.ObjectProperty);

            // Create the book resource in the ontology
            Resource bookResource = model.createResource(uriBuilder.buildIndividualURI(domain, StringConverter.toCamelCase(book.getTitle()), book.getId() + ""))
                    .addProperty(RDF.type, ebookClass)
                    .addProperty(titleProperty, book.getTitle())
                    .addProperty(avgRatingProperty, model.createTypedLiteral(ZERO))
                    .addProperty(ratingCountProperty, model.createTypedLiteral(ZERO))
                    .addProperty(reviewCountProperty, model.createTypedLiteral(ZERO))
                    .addProperty(publicationTimeProperty, dateMapper.map(book.getPublishedAt()).toString(), XSDDateType.XSDdate)
                    .addProperty(totalPagesProperty, model.createTypedLiteral(book.getTotalPages()))
                    .addProperty(publishedByProperty, book.getPublisher());

            for (Event.Author author : book.getAuthorsList()) {
                Resource authorResource = model.getResource(uriBuilder.buildIndividualURI(domain, StringConverter.toCamelCase(author.getName()), author.getId() + ""));
                bookResource.addProperty(writtenByProperty, authorResource);
            }

            for (String genre : StringUtils.toStringList(book.getGenres())) {
                Resource authorResource = model.getResource(uriBuilder.buildIndividualURI(domain, StringConverter.toCamelCase(genre), ""));
                bookResource.addProperty(belongsToGenreProperty, authorResource);
            }
            model.write(System.out, "RDF/XML");

            dataset.commit();
        } catch (Exception e) {
            dataset.abort();
            throw new RuntimeException("Failed to save book to the ontology", e);
        } finally {
            dataset.end();
        }
    }

    // Helper method to create or retrieve a resource if it already exists
    public Resource createOrGetResource(Model model, String ns, String type, String id) {
        Resource resource = model.getResource(ns + type + id);
        if (!model.containsResource(resource)) {
            resource = model.createResource(ns + type + "/" + id)
                    .addProperty(RDF.type, model.createResource(ns + type));
        }
        return resource;
    }

    public <T> T transaction(ReadWrite readWriteMode, Function<Model, T> action) {
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

    public void saveAuthor(Event.Author author) {
        Dataset dataset = TDBFactory.createDataset(TDB_DIRECTORY);
        dataset.begin(ReadWrite.WRITE);
        try {
            Model model = dataset.getDefaultModel();

            Property labelProperty = model.createProperty(uriBuilder.buildClassPropertyURI(domain, "label"));

            Resource classResources = model.getResource(uriBuilder.buildClassURI(domain, ClassNames.AUTHOR));
            if (!model.containsResource(classResources)) {
                model.createResource(classResources).addProperty(RDF.type, classResources)
                        .addProperty(labelProperty, ClassNames.AUTHOR);

            }

            // Define the namespace properties for the Author ontology
            Property authorNameProperty = model.createProperty(uriBuilder.buildClassPropertyURI(domain, "name"));
            Property authorStageNameProperty = model.createProperty(uriBuilder.buildClassPropertyURI(domain, "stageName"));
            Property authorNationalityProperty = model.createProperty(uriBuilder.buildClassPropertyURI(domain, "nationality"));
            Property authorBirthDateProperty = model.createProperty(uriBuilder.buildClassPropertyURI(domain, "birthDate"));
            Property authorBirthPlaceProperty = model.createProperty(uriBuilder.buildClassPropertyURI(domain, "birthPlace"));
            Property authorDeathDateProperty = model.createProperty(uriBuilder.buildClassPropertyURI(domain, "deathDate"));
            Property authorWebsiteProperty = model.createProperty(uriBuilder.buildClassPropertyURI(domain, "website"));
            Property authorDescriptionProperty = model.createProperty(uriBuilder.buildClassPropertyURI(domain, "description"));
            Property authorImageLinkProperty = model.createProperty(uriBuilder.buildClassPropertyURI(domain, "image"));

            // Create the author resource in the ontology
            Resource authorResource = model.createResource(
                            uriBuilder.buildIndividualURI(domain,
                                    StringConverter.toCamelCase(author.getName()), String.valueOf(author.getId())))
                    .addProperty(RDF.type, model.getResource(uriBuilder.buildClassURI(domain, ClassNames.AUTHOR)))
                    .addProperty(authorNameProperty, author.getName());

            // Add optional properties if they are present
            if (author.hasStageName()) {
                authorResource.addProperty(authorStageNameProperty, author.getStageName().getValue());
            }
            if (author.hasNationality()) {
                authorResource.addProperty(authorNationalityProperty, author.getNationality().getValue());
            }
            if (author.hasBirthDate()) {
                authorResource.addProperty(authorBirthDateProperty,
                        model.createTypedLiteral(author.getBirthDate().getValue(), XSDDatatype.XSDdate));
            }
            if (author.hasBirthPlace()) {
                authorResource.addProperty(authorBirthPlaceProperty, author.getBirthPlace().getValue());
            }
            if (author.hasDeathDate()) {
                authorResource.addProperty(authorDeathDateProperty,
                        model.createTypedLiteral(author.getDeathDate().getValue(), XSDDatatype.XSDdate));
            }
            if (author.hasWebsite()) {
                authorResource.addProperty(authorWebsiteProperty, author.getWebsite().getValue());
            }
            if (author.hasDescription()) {
                authorResource.addProperty(authorDescriptionProperty, author.getDescription().getValue());
            }
            if (author.hasImage()) {
                authorResource.addProperty(authorImageLinkProperty, author.getImage().getValue());
            }

            dataset.commit();
        } catch (Exception e) {
            dataset.abort();
            throw new RuntimeException("Failed to save book to the ontology", e);
        } finally {
            dataset.end();
        }
    }
}