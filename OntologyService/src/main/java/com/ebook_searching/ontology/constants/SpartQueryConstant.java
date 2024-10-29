package com.ebook_searching.ontology.constants;

import com.ebook_searching.ontology.model.Ontology.OWLIndividual;
import com.ebook_searching.ontology.model.Ontology.OWLObjectProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SpartQueryConstant {
    private SpartQueryConstant() {}

    private static final String PREFIX = "PREFIX ex: <http://www.ebook-searching.org/ebook#> ";
    private static final String PREFIX_RDFS = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> ";
    public static final String RETRIEVES_ALL_CLASSES = "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
            "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
            "SELECT DISTINCT ?class\n" +
            "WHERE {\n" +
            "  { ?class a owl:Class . }\n" +
            "  \n" +
            "}";
    public static String SEARCH_BOOK_BY_AUTHOR (String authorName) {
        return "PREFIX ex: <http://www.ebook-searching.org/ebook#>\n" +
                "\n" +
                "SELECT * WHERE {\n" +
                "    ?author ex:name \"J.K. Rowling\" .\n" + // Tên tác giả đã được sửa thành J.K. Rowling
                "    ?book a ex:Ebook .\n" +
                "    ?book ex:writtenBy ?author .\n" +
                "    ?book ex:title ?title .\n" +
                "    OPTIONAL { ?book ex:isbn ?isbn . }\n" +
                "    OPTIONAL { ?book ex:publicationYear ?publicationYear . }\n" +
                "    OPTIONAL { ?book ex:publishedBy ?publisher . }\n" +
                "    OPTIONAL { ?book ex:belongsToGenre ?genre . }\n" +
                "    OPTIONAL { ?genre ex:label ?label . }\n" +
                "    OPTIONAL { ?author ex:name ?authorName . }\n" + // Lấy tên tác giả
                "    OPTIONAL { ?publisher ex:label ?publisherName . }\n" + // Lấy tên nhà xuất bản
                "    OPTIONAL { ?genre ex:label ?genreName . }\n" + // Lấy tên thể loại
                "}";
    }

    public static String GET_CLASS_BY_DATAPROPERTIES(List<String> dataProperties) {
        StringBuilder valuesPart = new StringBuilder();
        valuesPart.append("FILTER(LCASE(?name) IN ( ");
        for (int i = 0; i < dataProperties.size(); i++) {
            valuesPart.append("LCASE(\"");
            valuesPart.append(dataProperties.get(i));
            valuesPart.append("\")");
            if (i < dataProperties.size() - 1) {
                valuesPart.append(", ");
            }
        }
        valuesPart.append("))");

        return "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX ex: <http://example.org/ontology#> \n" +
                "PREFIX owl: <http://www.w3.org/2002/07/owl#> \n" +
                "SELECT " +
                "(?individual AS ?individualURI) " +
                "(?class AS ?classURI) " +
                "(strafter(str(?individual), \"#\") AS ?individualName) " +
                "(strafter(str(?class), \"#\") AS ?className) " +
                "(?name AS ?label) " +
                "WHERE {\n" +
                "  ?individual ?property ?name .\n" +
                "  ?individual rdf:type ?class . \n" +
                "  FILTER(isIRI(?class)) \n" +
                "  FILTER(!strstarts(str(?class), str(owl:))) \n" +
                "  FILTER(!strstarts(str(?class), str(rdfs:))) \n" +
                valuesPart.toString() +
                "}\n" +
                "GROUP BY ?individual ?class ?name";
    }



    public static String GET_OBJECTPROPERTIES_BETWEEN_CLASSES(List<String> objectProperties) {
        StringBuilder valuesPart = new StringBuilder();
        int count = 0;
        int total = objectProperties.size() * (objectProperties.size() - 1);

        for (int i = 0; i < objectProperties.size(); i++) {
            for (int j = 0; j < objectProperties.size(); j++) {
                if (i == j) continue;

                valuesPart.append("(?domain = ebook:");
                valuesPart.append(objectProperties.get(i));
                valuesPart.append(" && ?range = ebook:");
                valuesPart.append(objectProperties.get(j));
                valuesPart.append(")");

                count++;
                if (count < total) {
                    valuesPart.append(" || \n");
                }
            }
        }

        return "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX ebook: <http://www.ebook-searching.org/ebook#>\n" +
                "SELECT " +
                "(strafter(str(?property), \"#\") AS ?propertyName) " +
                "(strafter(str(?domain), \"#\") AS ?domainName) " +
                "(strafter(str(?range), \"#\") AS ?rangeName) " +
                "WHERE {\n" +
                "  ?property rdf:type owl:ObjectProperty .\n" +
                "  ?property rdfs:domain ?domain .\n" +
                "  ?property rdfs:range ?range .\n" +
                "  FILTER (STRSTARTS(STR(?property), \"http://www.ebook-searching.org/ebook#\") &&\n" +
                "          STRSTARTS(STR(?domain), \"http://www.ebook-searching.org/ebook#\")\n" +
                "&&\n" +
                "          STRSTARTS(STR(?range), \"http://www.ebook-searching.org/ebook#\")\n" +
                " &&\n" +
                "    (\n" +
                valuesPart.toString() +
                "\n" +
                "    )\n" +
                ")\n" +
                "}";
    }

    public static String CHECK_LIST_CLASS (List<String> list) {
        StringBuilder sparqlQueryString = new StringBuilder();

        sparqlQueryString.append("PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>");
        // break line
        sparqlQueryString.append("\n");
        sparqlQueryString.append("PREFIX owl: <http://www.w3.org/2002/07/owl#>");
        sparqlQueryString.append("\n");
        sparqlQueryString.append("PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> ");
        sparqlQueryString.append("\n");
        sparqlQueryString.append("SELECT DISTINCT (strafter(str(?class), \"#\") AS ?className) (?class AS ?uri) ?label WHERE { ");
        sparqlQueryString.append("\n");
        sparqlQueryString.append("  ?class a owl:Class . ");
        sparqlQueryString.append("\n");
        sparqlQueryString.append("  ?class rdfs:label ?label . ");
        sparqlQueryString.append("\n");
        sparqlQueryString.append("  FILTER ( ");
        sparqlQueryString.append("\n");
        sparqlQueryString.append("    LCASE(str(?label)) IN (");
        for (int i = 0; i < list.size(); i++) {
            sparqlQueryString.append("\"").append(list.get(i).toLowerCase()).append("\"");
            if (i < list.size() - 1) {
                sparqlQueryString.append(", ");
            }
        }
        sparqlQueryString.append(") ");
        sparqlQueryString.append("\n");
        sparqlQueryString.append("  ) ");
        sparqlQueryString.append("\n");
        sparqlQueryString.append("} ");


        return sparqlQueryString.toString();
    }

    public static String QUERY_BY_OBJECTPROPERTY_BETWEEN_CLASSES(List<OWLObjectProperty> objectProperties) {
        StringBuilder sparqlQueryStringBuilder = new StringBuilder();
        for(int i=0; i<objectProperties.size();i++){
            OWLObjectProperty objectProperty = objectProperties.get(i);
            sparqlQueryStringBuilder.append("{?");
            sparqlQueryStringBuilder.append(objectProperty.getDomain());
            sparqlQueryStringBuilder.append(" ebook:");
            sparqlQueryStringBuilder.append(objectProperty.getName());
            sparqlQueryStringBuilder.append(" ?");
            sparqlQueryStringBuilder.append(objectProperty.getRange());
            sparqlQueryStringBuilder.append(" .}\n");
            if (i < objectProperties.size() - 1) {
                sparqlQueryStringBuilder.append("UNION");
            }
        };
        return "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"+
                "PREFIX ebook: <http://www.ebook-searching.org/ebook#>\n"+
                "SELECT DISTINCT * WHERE {\n"+
                sparqlQueryStringBuilder.toString()+
                "}";
    }

    public static String QUERY_BY_OBJECTPROPERTY_BETWEEN_INDIVIDUAL_AND_CLASS(List<OWLObjectProperty> objectProperties, List<String> classes, Map<String, String> dataProperties) {
        StringBuilder sparqlQueryStringBuilder = new StringBuilder();
        sparqlQueryStringBuilder.append("SELECT DISTINCT *  WHERE {  \n");

        for(int i=0; i<dataProperties.size();i++){
            Map.Entry<String, String> entry = dataProperties.entrySet().stream().findFirst().get();
            String key = entry.getKey();
            String value = entry.getValue();
            objectProperties.forEach(objectProperty -> {
                if(key.toLowerCase().equals(objectProperty.getDomain().toLowerCase())){
                    sparqlQueryStringBuilder.append("{ebook:");
                    sparqlQueryStringBuilder.append(value);
                    sparqlQueryStringBuilder.append(" ebook:");
                    sparqlQueryStringBuilder.append(objectProperty.getName());
                    sparqlQueryStringBuilder.append(" ?");
                    sparqlQueryStringBuilder.append(objectProperty.getRange());
                    sparqlQueryStringBuilder.append(" .}\n");
                }
            });
        };
        return "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX ebook: <http://www.ebook-searching.org/ebook#>\n" +
                    sparqlQueryStringBuilder.toString()+
                "}";
    }

    public static String QUERY_SINGLE_INDIVIDUAL(Map<String, String> dataProperties) {
        Map.Entry<String, String> entry = dataProperties.entrySet().stream().findFirst().get();
        String key = entry.getKey();
        String value = entry.getValue();
        return "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX ebook: <http://www.ebook-searching.org/ebook#>\n" +
                "SELECT ?value (strafter(str(?property), \"#\") AS ?key) WHERE {  \n" +
                " {ebook:" +
                value +
                " ?property ?value .}\n" +
                "FILTER (isLiteral(?value))\n" +
                "}";
    }

    public static String QUERY_SINGLE_CLASS(List<String> classes) {
        StringBuilder sparqlQueryStringBuilder = new StringBuilder();
        for(int i=0; i<classes.size();i++){
            sparqlQueryStringBuilder.append("\"");
            sparqlQueryStringBuilder.append(classes.get(i));
            System.out.println("hihihi class" + classes.get(i));
            sparqlQueryStringBuilder.append("\"");
            if (i < classes.size() - 1) {
                sparqlQueryStringBuilder.append(", ");
            }
        };
        return "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n" +
                "PREFIX ex: <http://www.ebook-searching.org/ebook#>\n" +
                "SELECT DISTINCT * WHERE { \n" +
                "?individual rdf:type ?class .\n" +
                "}\n";
    }

    public static String QUERY_SINGLE_CLASS(String className) {
        return "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX ebook: <http://www.ebook-searching.org/ebook#>\n" +
                "SELECT * WHERE {  \n" +
                "{?individual rdf:type ebook:" +
                className +
                " .}\n" +
                "}";
    }

    public static String QUERY_BY_OBJECTPROPERTY_N_DATAPROPERTY_N_CLASS(List<String> classes,List<OWLObjectProperty> objectProperties, List<OWLIndividual> individuals) {
        StringBuilder sparqlQueryStringBuilder = new StringBuilder();
        List<String> selectClass = new ArrayList<>();
        //BUILD QUERY
        individuals.forEach((item) -> {
            objectProperties.forEach((objectProperty) -> {
                if (objectProperty.getDomain().equals(item.getClassName())) {
                    if(!selectClass.contains(objectProperty.getRange())){
                        selectClass.add(objectProperty.getRange());
                    }
                    sparqlQueryStringBuilder.append("{ex:");
                    sparqlQueryStringBuilder.append(item.getIndividualName());
                    sparqlQueryStringBuilder.append(" ex:");
                    sparqlQueryStringBuilder.append(objectProperty.getName());
                    sparqlQueryStringBuilder.append(" ?");
                    sparqlQueryStringBuilder.append(objectProperty.getRange());
                    sparqlQueryStringBuilder.append(" .\n");
                    sparqlQueryStringBuilder.append("?");
                    sparqlQueryStringBuilder.append(objectProperty.getRange());
                    sparqlQueryStringBuilder.append(" ?property ?value .\n");
                    sparqlQueryStringBuilder.append("FILTER (isLiteral(?value))  \n");
                    sparqlQueryStringBuilder.append("} UNION\n");

                }

                if (objectProperty.getRange().equals(item.getClassName())) {
                    if(!selectClass.contains(objectProperty.getDomain())){
                        selectClass.add(objectProperty.getDomain());
                    }

                    sparqlQueryStringBuilder.append("{?");
                    sparqlQueryStringBuilder.append(objectProperty.getDomain());
                    sparqlQueryStringBuilder.append(" ex:");
                    sparqlQueryStringBuilder.append(objectProperty.getName());
                    sparqlQueryStringBuilder.append(" ex:");
                    sparqlQueryStringBuilder.append(item.getIndividualName());
                    sparqlQueryStringBuilder.append(" .\n");
                    sparqlQueryStringBuilder.append("?");
                    sparqlQueryStringBuilder.append(objectProperty.getDomain());
                    sparqlQueryStringBuilder.append(" ?property ?value .\n");
                    sparqlQueryStringBuilder.append("FILTER (isLiteral(?value))  \n");
                    sparqlQueryStringBuilder.append("} UNION\n");
                }
            });
        });

        int lastUnionIndex = sparqlQueryStringBuilder.lastIndexOf(" UNION");
        if (lastUnionIndex != -1) {
            sparqlQueryStringBuilder.delete(lastUnionIndex, lastUnionIndex + 6);
        }

        return "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX ex: <http://www.ebook-searching.org/ebook#>\n" +
                "SELECT " +
                "?" + String.join(" ?", selectClass)+
                " (GROUP_CONCAT(CONCAT(STRAFTER(STR(?property), \"#\"), \"=\", STR(?value)); SEPARATOR=\", \") AS ?properties)" +
                "\n" +
                "WHERE {\n" +
                sparqlQueryStringBuilder.toString() +
                "} \n" +
                "GROUP BY " +
                " ?" + String.join(" ?", selectClass);
    }
}
