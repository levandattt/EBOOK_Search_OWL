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
import java.util.ArrayList;
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
            Property descriptionProperty = model.getProperty(uriBuilder.buildClassPropertyURI(domain, "description"));
            Property uuidProperty = model.getProperty(uriBuilder.buildClassPropertyURI(domain, "bookUuid"));
            descriptionProperty.addProperty(RDF.type, uriBuilder.buildClassURI(domain, ClassNames.BOOK));
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
                    .addProperty(uuidProperty, book.getUuid())
                    .addProperty(descriptionProperty, book.getDescription());

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

    // Save a new book to the ontology in RDF/OWL format
    public void updateBook(Event.AddBookEvent book) {
        Dataset dataset = TDBFactory.createDataset(TDB_DIRECTORY);
        dataset.begin(ReadWrite.WRITE);
        try {
            Model model = dataset.getDefaultModel();

            // Define namespaces used in the ontology
            Property titleProperty = model.getProperty(uriBuilder.buildClassPropertyURI(domain, "title"));
            Property avgRatingProperty = model.getProperty(uriBuilder.buildClassPropertyURI(domain, "avgRating"));
            Property ratingCountProperty = model.getProperty(uriBuilder.buildClassPropertyURI(domain, "ratingCount"));
            Property reviewCountProperty = model.getProperty(uriBuilder.buildClassPropertyURI(domain, "reviewCount"));
            Property publicationTimeProperty = model.getProperty(uriBuilder.buildClassPropertyURI(domain, "publicationTime"));
            Property totalPagesProperty = model.getProperty(uriBuilder.buildClassPropertyURI(domain, "totalPages"));
            Property publishedByProperty = model.getProperty(uriBuilder.buildClassPropertyURI(domain, "publishedBy"));
            Property uuidProperty = model.getProperty(uriBuilder.buildClassPropertyURI(domain, "bookUuid"));
            Property descriptionProperty = model.getProperty(uriBuilder.buildClassPropertyURI(domain, "description"));
            descriptionProperty.addProperty(RDF.type, uriBuilder.buildClassURI(domain, ClassNames.BOOK));
            Property writtenByProperty = model.getProperty(uriBuilder.buildClassRelationshipURI(domain, "writtenBy"));
            Property hasWritten = model.getProperty(uriBuilder.buildClassPropertyURI(domain, "hasWritten"));

            Property belongsToGenreProperty = model.getProperty(uriBuilder.buildClassRelationshipURI(domain, "belongsToGenre"));
            Property containsBooks = model.getProperty(uriBuilder.buildClassPropertyURI(domain, "containsBooks"));

            // Create the book resource in the ontology
            Resource bookResource = model.getResource(uriBuilder.buildIndividualURI(domain, StringConverter.toCamelCase(book.getTitle()), book.getId() + ""))
                    .removeProperties()
                    .addProperty(titleProperty, book.getTitle())
                    .addProperty(avgRatingProperty, model.createTypedLiteral(ZERO))
                    .addProperty(ratingCountProperty, model.createTypedLiteral(ZERO))
                    .addProperty(reviewCountProperty, model.createTypedLiteral(ZERO))
                    .addProperty(publicationTimeProperty, dateMapper.map(book.getPublishedAt()).toString(), XSDDateType.XSDdate)
                    .addProperty(totalPagesProperty, model.createTypedLiteral(book.getTotalPages()))
                    .addProperty(publishedByProperty, book.getPublisher())
                    .addProperty(uuidProperty, book.getUuid())
                    .addProperty(descriptionProperty, book.getDescription());

            // Check if the book's title has changed
            if (!book.getTitle().equals(book.getOldTitle())) {
                // Get the old and new book resources
                Resource oldBook = model.getResource(
                        uriBuilder.buildIndividualURI(domain,
                                StringConverter.toCamelCase(book.getOldTitle()), book.getId() + ""));

                // Step 1: Migrate relationships with authors (hasWritten and writtenBy)

                // Collect and migrate all "writtenBy" statements where oldBook is the subject
                StmtIterator writtenByIter = oldBook.listProperties(writtenByProperty);
                List<Statement> writtenByStatements = new ArrayList<>();
                while (writtenByIter.hasNext()) {
                    writtenByStatements.add(writtenByIter.nextStatement());
                }

                for (Statement stmt : writtenByStatements) {
                    Resource authorResource = stmt.getObject().asResource();
                    bookResource.addProperty(writtenByProperty, authorResource);
                    authorResource.addProperty(hasWritten, bookResource); // Update author's hasWritten to point to the new book
                }

                // Step 2: Migrate relationships with genres (belongsToGenre and containsBooks)

                // Collect and migrate all "belongsToGenre" statements where oldBook is the subject
                StmtIterator belongsToGenreIter = oldBook.listProperties(belongsToGenreProperty);
                List<Statement> belongsToGenreStatements = new ArrayList<>();
                while (belongsToGenreIter.hasNext()) {
                    belongsToGenreStatements.add(belongsToGenreIter.nextStatement());
                }

                for (Statement stmt : belongsToGenreStatements) {
                    Resource genreResource = stmt.getObject().asResource();
                    bookResource.addProperty(belongsToGenreProperty, genreResource);
                    genreResource.addProperty(containsBooks, bookResource); // Update genre's containsBooks to point to the new book
                }

                // Remove old relationships from the model where oldBook was the subject or object
                oldBook.removeProperties();
                model.removeAll(null, null, oldBook);
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

    // Save a new book to the ontology in RDF/OWL format
    public void deleteBook(Event.AddBookEvent book) {
        Dataset dataset = TDBFactory.createDataset(TDB_DIRECTORY);
        dataset.begin(ReadWrite.WRITE);
        try {
            Model model = dataset.getDefaultModel();

            // Build the URI for the book resource
            String bookURI = uriBuilder.buildIndividualURI(
                    domain,
                    StringConverter.toCamelCase(book.getTitle()),
                    String.valueOf(book.getId())
            );

            // Get the book resource from the model
            Resource bookResource = model.getResource(bookURI);

            // Remove all triples where the book resource is the subject
            model.removeAll(bookResource, null, (RDFNode) null);

            // Remove all triples where the book resource is the object
            model.removeAll(null, null, bookResource);

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

    public void updateAuthor(Event.Author author) {
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
            Resource authorIndividual = model.getResource(
                            uriBuilder.buildIndividualURI(domain,
                                    StringConverter.toCamelCase(author.getName()), String.valueOf(author.getId())))
                    .removeProperties()
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

            if (!author.getName().equals(author.getOldName())) {
                Resource old = model.getResource(
                        uriBuilder.buildIndividualURI(domain,
                                StringConverter.toCamelCase(author.getOldName()), String.valueOf(author.getId())));

                // Define the properties
                Property hasWritten = model.getProperty(uriBuilder.buildClassPropertyURI(domain, "hasWritten"));
                Property writtenByProperty = model.getProperty(uriBuilder.buildClassPropertyURI(domain, "writtenBy"));

                // Step 1: Migrate the "hasWritten" relationships from the old author to the new author
                // Collect the statements into a list to avoid ConcurrentModificationException
                StmtIterator iter = old.listProperties(hasWritten);
                List<Statement> statementsToMigrate = new ArrayList<>();
                while (iter.hasNext()) {
                    statementsToMigrate.add(iter.nextStatement());
                }
                // Now, modify the model after iteration is complete
                for (Statement stmt : statementsToMigrate) {
                    // Add the hasWritten property to the new author
                    authorIndividual.addProperty(hasWritten, stmt.getObject());
                }

                // Step 2: Update the "writtenBy" properties pointing to the old author
                // Collect the statements into a list to avoid ConcurrentModificationException
                StmtIterator reverseIter = model.listStatements(null, writtenByProperty, old);
                List<Statement> statementsToUpdate = new ArrayList<>();
                while (reverseIter.hasNext()) {
                    statementsToUpdate.add(reverseIter.nextStatement());
                }

                // Remove old "writtenBy" statements and add new ones pointing to the new author
                for (Statement stmt : statementsToUpdate) {
                    Resource subject = stmt.getSubject();
                    // Remove the old statement
                    model.remove(stmt);
                    // Add a new statement with the new author as the object
                    model.add(subject, writtenByProperty, authorIndividual);
                }

                // Remove statements where old is the subject
                old.removeProperties();

                // Remove statements where old is the object
                model.removeAll(null, null, old);
            }


            dataset.commit();
        } catch (Exception e) {
            dataset.abort();
            throw new RuntimeException("Failed to save book to the ontology", e);
        } finally {
            dataset.end();
        }
    }

    public void deleteAuthor(Event.Author author) {
        Dataset dataset = TDBFactory.createDataset(TDB_DIRECTORY);
        dataset.begin(ReadWrite.WRITE);
        try {
            Model model = dataset.getDefaultModel();

            // Build the URI for the book resource
            String authorURI = uriBuilder.buildIndividualURI(
                    domain,
                    StringConverter.toCamelCase(author.getName()),
                    String.valueOf(author.getId())
            );

            // Get the book resource from the model
            Resource authorResource = model.getResource(authorURI);

            // Remove all triples where the book resource is the subject
            model.removeAll(authorResource, null, (RDFNode) null);

            // Remove all triples where the book resource is the object
            model.removeAll(null, null, authorResource);

            dataset.commit();
        } catch (Exception e) {
            dataset.abort();
            throw new RuntimeException("Failed to save book to the ontology", e);
        } finally {
            dataset.end();
        }
    }
}