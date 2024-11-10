package com.ebook_searching.ontology.migrations;

import org.apache.jena.query.Dataset;
import org.apache.jena.rdf.model.*;
import org.apache.jena.tdb.TDBFactory;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.*;

@Component
public class MigrationManager {
    @Value("${ontology.tdb2.directory}")
    private String TDB_DIRECTORY;

    @Autowired
    private V1_InitOntology v1_initOntology;

    @Autowired
    private V2_AddUUID v2_addUuid;

    @Autowired
    private V3_AddLabel v3_addLabel;

    @Autowired
    private V4_AddAllLabels v4_addAllLabels;

    @Autowired
    private V5_AddLabelsForClasses v5AddLabelsForClasses;

    @Autowired
    private V6_AddLabelsForAuthors v6_AddLabelsForAuthors;

    @Autowired
    private V7_AddLabelsForAuthors v7AddLabelsForAuthors;

    @Autowired
    private V8_MigrateDescriptionData v8MigrateDescriptionData;

    @Value("${ontology.domain}")
    private String baseUri;

    @Value("${ontology.ontology-file}")
    private String owlFile;

    private ResourceLoader resourceLoader;

    public MigrationManager() {
    }

    public void runMigrations() throws IOException {
        Dataset dataset = TDBFactory.createDataset(TDB_DIRECTORY);

        Model model = dataset.getDefaultModel();

        // Get current version from ontology
        Resource ontology = model.getResource(baseUri);
        String currentVersion = ontology.getProperty(OWL.versionInfo) != null
                ? ontology.getProperty(OWL.versionInfo).getString()
                : "0";

        int versionNumber = Integer.parseInt(currentVersion);

        // Load migration classes
        List<Migration> migrations = loadMigrations();

        // Apply migrations if necessary
        for (Migration migration : migrations) {
            if (migration.getVersion() > versionNumber) {
                migration.up();
                System.out.println("[Ontology migrations] Applied migration version: " + migration.getVersion());
                versionNumber = migration.getVersion();
            }
        }

        // Update the version in the ontology
        ontology.removeAll(OWL.versionInfo);
        ontology.addProperty(OWL.versionInfo, Integer.toString(versionNumber));

//        writeModelToFile(model);
        try (FileOutputStream out = new FileOutputStream(owlFile)) {
            model.write(out, "RDF/XML");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Migration> loadMigrations() {
        List<Migration> migrations = new ArrayList<>();

        // Here, you should load migration classes manually or through reflection.
        migrations.add(v1_initOntology);
        migrations.add(v2_addUuid);
        migrations.add(v3_addLabel);
        migrations.add(v4_addAllLabels);
        migrations.add(v5AddLabelsForClasses);
        migrations.add(v6_AddLabelsForAuthors);
        migrations.add(v7AddLabelsForAuthors);
        migrations.add(v8MigrateDescriptionData);

        // Sort by version to apply in order
        migrations.sort(Comparator.comparingInt(Migration::getVersion));

        return migrations;
    }

    private void writeModelToFile(Model model) {
        try (FileOutputStream out = new FileOutputStream(owlFile)) {
            // Create a new model to store only ontology structure (classes, properties)
            Model ontologyStructure = ModelFactory.createDefaultModel();

            // Iterate over all statements in the model
            StmtIterator stmtIterator = model.listStatements();
            while (stmtIterator.hasNext()) {
                Statement stmt = stmtIterator.nextStatement();
                // Only add schema (ontology) triples: classes, properties, ontology info, and their metadata
                if (stmt.getPredicate().equals(RDFS.subClassOf) ||
                        stmt.getPredicate().equals(RDFS.domain) ||
                        stmt.getPredicate().equals(RDFS.range) ||
                        stmt.getObject().isResource() && stmt.getObject().asResource().hasProperty(RDF.type, OWL.Class) ||
                        stmt.getSubject().hasProperty(RDF.type, OWL.Class) ||
                        stmt.getPredicate().equals(RDFS.label) ||
                        stmt.getPredicate().equals(OWL.versionInfo) ||
                        stmt.getSubject().hasProperty(RDF.type, OWL.Ontology) ||
                        stmt.getPredicate().equals(RDFS.comment)) {
                    ontologyStructure.add(stmt);
                }
            }

            // Write the structure-only model to the output file
            ontologyStructure.write(out, "RDF/XML");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Interface for Migrations
    public interface Migration {
        void up();

        int getVersion();
    }
}
