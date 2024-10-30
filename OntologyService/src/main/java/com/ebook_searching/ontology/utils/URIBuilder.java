package com.ebook_searching.ontology.utils;

import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

@Component
public class URIBuilder {
    /**
     * Builds a URI for a class type.
     *
     * @param className The name of the class (e.g., "Author", "Book").
     * @return A URI pointing to the class resource.
     * @throws URISyntaxException If the URI syntax is incorrect.
     */
    public String buildClassURI(String namespace, String className) {
        return namespace + "#" + className;
    }

    /**
     * Builds a URI for an individual.
     *
     * @param name The name of the individual (e.g., "HoChiMinh", "ThePrisonDiaryofHoChiMinh").
     * @param id   A unique identifier for the individual (e.g., UUID or database ID).
     * @return A URI pointing to the individual resource.
     */
    public String buildIndividualURI(String namespace, String name, String id) {
        String formattedName = name.replaceAll("\\s+", "-");  // Replace spaces with hyphens
        if (id.isEmpty()) {
            return String.format("%s#%s", namespace, formattedName);
        }
        return String.format("%s#%s-%s", namespace, formattedName, id);
    }

    /**
     * Builds a URI with a generated UUID if no ID is provided.
     *
     * @param name The name of the individual.
     * @return A URI with a generated UUID.
     */
    public String buildIndividualURIWithUUID(String namespace, String name) {
        String uuid = UUID.randomUUID().toString();
        return buildIndividualURI(namespace, name, uuid);
    }

    // Generate a URI for a class-specific property
    public String buildClassPropertyURI(String namespace, String propertyName) {
        return String.format("%s#%s", namespace, propertyName);
    }

    // Generate a URI for a class-specific relationship
    public String buildClassRelationshipURI(String namespace, String relationshipName) {
        return String.format("%s#%s", namespace, relationshipName);
    }
}
