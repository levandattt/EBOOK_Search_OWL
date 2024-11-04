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

    @Value("${ontology.domain}")
    private String domain = "http://www.ebook-searching.org/ontology";

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
            Resource ebookClass = model.getResource(uriBuilder.buildClassURI(domain, ClassNames.BOOK));

            // Define namespaces used in the ontology
            Property titleProperty = model.getProperty(uriBuilder.buildClassPropertyURI(domain, "title"));
            Property avgRatingProperty = model.getProperty(uriBuilder.buildClassPropertyURI(domain, "avgRating"));
            Property ratingCountProperty = model.getProperty(uriBuilder.buildClassPropertyURI(domain, "ratingCount"));
            Property reviewCountProperty = model.getProperty(uriBuilder.buildClassPropertyURI(domain, "reviewCount"));
            Property publicationTimeProperty = model.getProperty(uriBuilder.buildClassPropertyURI(domain, "publicationTime"));
            Property totalPagesProperty = model.getProperty(uriBuilder.buildClassPropertyURI(domain, "totalPages"));
            Property publishedByProperty = model.getProperty(uriBuilder.buildClassPropertyURI(domain, "publishedBy"));
            Property uuidProperty = model.getProperty(uriBuilder.buildClassPropertyURI(domain, "bookUuid"));

            Property writtenByProperty = model.getProperty(uriBuilder.buildClassRelationshipURI(domain, "writtenBy"));
            Property hasWritten = model.getProperty(uriBuilder.buildClassPropertyURI(domain, "hasWritten"));

            Property belongsToGenreProperty = model.getProperty(uriBuilder.buildClassRelationshipURI(domain, "belongsToGenre"));
            Property containsBooks = model.getProperty(uriBuilder.buildClassPropertyURI(domain, "containsBooks"));

            // Create the book resource in the ontology
            Resource bookResource = model.createResource(uriBuilder.buildIndividualURI(domain, StringConverter.toCamelCase(book.getTitle()), book.getId() + ""))
                    .addProperty(RDF.type, ebookClass)
                    .addProperty(titleProperty, book.getTitle())
                    .addProperty(avgRatingProperty, model.createTypedLiteral(ZERO))
                    .addProperty(ratingCountProperty, model.createTypedLiteral(ZERO))
                    .addProperty(reviewCountProperty, model.createTypedLiteral(ZERO))
                    .addProperty(publicationTimeProperty, dateMapper.map(book.getPublishedAt()).toString(), XSDDateType.XSDdate)
                    .addProperty(totalPagesProperty, model.createTypedLiteral(book.getTotalPages()))
                    .addProperty(publishedByProperty, book.getPublisher())
                    .addProperty(uuidProperty, book.getUuid());

            for (Event.Author author : book.getAuthorsList()) {
                Resource authorResource = model.getResource(uriBuilder.buildIndividualURI(domain, StringConverter.toCamelCase(author.getName()), author.getId() + ""));
                bookResource.addProperty(writtenByProperty, authorResource);

                authorResource.addProperty(hasWritten, bookResource);
            }

            for (String genre : StringUtils.toStringList(book.getGenres())) {
                Resource genreResource = model.getResource(uriBuilder.buildIndividualURI(domain, StringConverter.toCamelCase(genre), ""));
                bookResource.addProperty(belongsToGenreProperty, genreResource);

                genreResource.addProperty(containsBooks, bookResource);
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

            Resource authorClass = model.getResource(uriBuilder.buildClassURI(domain, ClassNames.AUTHOR));

            // Define the namespace properties for the Author ontology
            Property authorNameProperty = model.getProperty(uriBuilder.buildClassPropertyURI(domain, "authorName"));
            Property authorStageNameProperty = model.getProperty(uriBuilder.buildClassPropertyURI(domain, "authorStageName"));
            Property authorNationalityProperty = model.getProperty(uriBuilder.buildClassPropertyURI(domain, "authorNationality"));
            Property authorBirthDateProperty = model.getProperty(uriBuilder.buildClassPropertyURI(domain, "authorBirthDate"));
            Property authorBirthPlaceProperty = model.getProperty(uriBuilder.buildClassPropertyURI(domain, "authorBirthPlace"));
            Property authorDeathDateProperty = model.getProperty(uriBuilder.buildClassPropertyURI(domain, "authorDeathDate"));
            Property authorWebsiteProperty = model.getProperty(uriBuilder.buildClassPropertyURI(domain, "authorWebsite"));
            Property authorDescriptionProperty = model.getProperty(uriBuilder.buildClassPropertyURI(domain, "authorDescription"));
            Property authorImageProperty = model.getProperty(uriBuilder.buildClassPropertyURI(domain, "authorImage"));
            Property uuidProperty = model.getProperty(uriBuilder.buildClassPropertyURI(domain, "authorUuid"));

            // Create the author resource in the ontology
            Resource authorIndividual = model.createResource(
                            uriBuilder.buildIndividualURI(domain,
                                    StringConverter.toCamelCase(author.getName()), String.valueOf(author.getId())))
                    .addProperty(RDF.type, authorClass)
                    .addProperty(authorNameProperty, author.getName())
                    .addProperty(uuidProperty, author.getUuid());

            // Add optional properties if they are present
            if (author.hasStageName()) {
                authorIndividual.addProperty(authorStageNameProperty, author.getStageName().getValue());
            }
            if (author.hasNationality()) {
                authorIndividual.addProperty(authorNationalityProperty, author.getNationality().getValue());
            }
            if (author.hasBirthDate()) {
                authorIndividual.addProperty(authorBirthDateProperty,
                        model.createTypedLiteral(author.getBirthDate().getValue(), XSDDatatype.XSDdate));
            }
            if (author.hasBirthPlace()) {
                authorIndividual.addProperty(authorBirthPlaceProperty, author.getBirthPlace().getValue());
            }
            if (author.hasDeathDate()) {
                authorIndividual.addProperty(authorDeathDateProperty,
                        model.createTypedLiteral(author.getDeathDate().getValue(), XSDDatatype.XSDdate));
            }
            if (author.hasWebsite()) {
                authorIndividual.addProperty(authorWebsiteProperty, author.getWebsite().getValue());
            }
            if (author.hasDescription()) {
                authorIndividual.addProperty(authorDescriptionProperty, author.getDescription().getValue());
            }
            if (author.hasImage()) {
                authorIndividual.addProperty(authorImageProperty, author.getImage().getValue());
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