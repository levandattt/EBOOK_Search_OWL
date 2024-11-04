package com.ebook_searching.ontology.constants;

import com.ebook_searching.ontology.model.Ontology.OWLIndividual;
import com.ebook_searching.ontology.model.Ontology.OWLObjectProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SpartQueryConstant {
    private static final String PREFIX = "PREFIX ex: <http://www.ebook-searching.org/ontology#> ";
    private static final String PREFIX_RDFS = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> ";
    public static final String RETRIEVES_ALL_CLASSES = "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
            "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
            "SELECT DISTINCT ?class\n" +
            "WHERE {\n" +
            "  { ?class a owl:Class . }\n" +
            "  \n" +
            "}";
    public static String SEARCH_BOOK_BY_AUTHOR (String authorName) {
        return "PREFIX ex: <http://www.ebook-searching.org/ontology#>\n" +
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
        for (int i = 0; i < dataProperties.size(); i++) {
            valuesPart.append("\"");
            valuesPart.append(dataProperties.get(i).toLowerCase());
            valuesPart.append("\"");
            if (i < dataProperties.size() - 1) {
                valuesPart.append(", ");
            }
        }

        return "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                "PREFIX ebook: <http://www.ebook-searching.org/ontology#>\n" +
                "SELECT \n" +
                "\t(?individual AS ?individualURI)\n" +
                "\t(strafter(str(?individual), \"#\") AS ?individualName)\n" +
                "\t?property \n" +
                "\t?label \n" +
                "\t(MIN(?class) AS ?classURI)\n" +
                "\t(STRAFTER(STR(MIN(?class)), \"#\") AS ?className)\n" +
                "WHERE {\n" +
                "    ?individual ?property ?label .\n" +
                "    ?individual rdf:type ?class .\n" +
                "    \n" +
                "    FILTER (STRSTARTS(STR(?individual), \"http://www.ebook-searching.org/ontology#\"))\n" +
                "    FILTER (STRSTARTS(STR(?property), \"http://www.ebook-searching.org/ontology#\"))\n" +
                "    FILTER (STRSTARTS(STR(?class), \"http://www.ebook-searching.org/ontology#\"))\n" +
                "\n" +
                "    FILTER NOT EXISTS { ?property rdf:type owl:ObjectProperty . }\n" +
                "    FILTER (\n" +
                "        LCASE(STR(?label)) in (" +
                valuesPart.toString() +
                ")\n" +
                "    )\n" +
                "}\n" +
                "GROUP BY ?individual ?property ?label";
    }


    public static String GET_OBJECTPROPERTIES_BY_LABEL(List<String> objectProperties){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < objectProperties.size(); i++) {
            stringBuilder.append("\"");
            stringBuilder.append(objectProperties.get(i).toLowerCase());
            stringBuilder.append("\"");
            if (i < objectProperties.size() - 1) {
                stringBuilder.append(", ");
            }
        }


        return "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX ebook: <http://www.ebook-searching.org/ontology#>\n" +
                "SELECT \n" +
                "    (strafter(str(?property), \"#\") AS ?propertyName) \n" +
                "    (strafter(str(?domain), \"#\") AS ?domainName) \n" +
                "    (strafter(str(?range), \"#\") AS ?rangeName)\n" +
                "    ?label \n" +
                "WHERE {\n" +
                "    ?property rdf:type owl:ObjectProperty .\n" +
                "    ?property rdfs:domain ?domain .\n" +
                "    ?property rdfs:range ?range .\n" +
                "    ?property rdfs:label ?label .\n" +
                "    \n" +
                "    FILTER (\n" +
                "        STRSTARTS(STR(?property), \"http://www.ebook-searching.org/ontology#\") &&\n" +
                "        STRSTARTS(STR(?domain), \"http://www.ebook-searching.org/ontology#\") &&\n" +
                "        STRSTARTS(STR(?range), \"http://www.ebook-searching.org/ontology#\") &&\n" +
                "        LCASE(STR(?label)) in (" +
                stringBuilder.toString() +
                ") &&"+
                "        (\n" +
                "            (?domain = ebook:Author && ?range = ebook:Book) ||\n" +
                "            (?domain = ebook:Book && ?range = ebook:Author)\n" +
                "        )\n" +
                "    )\n" +
                "}\n";
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
                "PREFIX ebook: <http://www.ebook-searching.org/ontology#>\n" +
                "SELECT " +
                "(strafter(str(?property), \"#\") AS ?propertyName) " +

                "(strafter(str(?domain), \"#\") AS ?domainName) " +
                "(strafter(str(?range), \"#\") AS ?rangeName) " +
                "WHERE {\n" +
                "  ?property rdf:type owl:ObjectProperty .\n" +
                "  ?property rdfs:domain ?domain .\n" +
                "  ?property rdfs:range ?range .\n" +
                "  FILTER (STRSTARTS(STR(?property), \"http://www.ebook-searching.org/ontology#\") &&\n" +
                "          STRSTARTS(STR(?domain), \"http://www.ebook-searching.org/ontology#\")\n" +
                "&&\n" +
                "          STRSTARTS(STR(?range), \"http://www.ebook-searching.org/ontology#\")\n" +
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

    public static String QUERY_INDIVIDUAL_OF_DOMAIN_BY_OBJECTPROPERTY(OWLObjectProperty objectProperty) {
        return "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX ebook: <http://www.ebook-searching.org/ontology#>\n" +
                "SELECT DISTINCT\n" +
                "?" + objectProperty.getDomain() + "\n" +
                " (GROUP_CONCAT(CONCAT(STRAFTER(STR(?property), \"#\"), \"=\", STR(?value)); SEPARATOR=\"|| \") AS ?properties)\n" +
                "WHERE {\n" +
                "?" + objectProperty.getDomain() +" rdf:type ebook:" + objectProperty.getDomain() + " .\n" +
                "    ?" + objectProperty.getDomain() + " ebook:" + objectProperty.getName() + " ebook:" + objectProperty.getRange() + " .\n" +
                "    ?" + objectProperty.getDomain() + " ?property ?value .\n" +
                "FILTER (isLiteral(?value))\n" +
                "    FILTER (STRSTARTS(STR(?" + objectProperty.getDomain() + "), \"http://www.ebook-searching.org/ontology#\"))\n" +
                "    FILTER (STRSTARTS(STR(?property), \"http://www.ebook-searching.org/ontology#\"))\n" +
                "}group by ?" + objectProperty.getDomain();
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
                "PREFIX ebook: <http://www.ebook-searching.org/ontology#>\n"+
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
                "PREFIX ebook: <http://www.ebook-searching.org/ontology#>\n" +
                sparqlQueryStringBuilder.toString()+
                "}";
    }

    public static String QUERY_SINGLE_INDIVIDUAL(OWLIndividual individual) {
        return "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX ebook: <http://www.ebook-searching.org/ontology#>\n" +
                "SELECT \n" +
                "    (ebook:" + individual.getIndividualName() + " AS ?" + individual.getClassName() + ") \n" +
                "    (GROUP_CONCAT(CONCAT(STRAFTER(STR(?property), \"#\"), \"=\", STR(?value)); SEPARATOR=\"|| \") AS ?properties)\n" +



        "WHERE {  \n" +
                "    ebook:" + individual.getIndividualName() + " ?property ?value .\n" +
                "    FILTER (isLiteral(?value))\n" +
                "}\n" +
                "GROUP BY ?" + individual.getClassName();
    }

    public static String QUERY_SINGLE_CLASS(List<String> classes) {
        StringBuilder sparqlQueryStringBuilder = new StringBuilder();
        StringBuilder classBuilder = new StringBuilder();
        for(int i=0; i<classes.size();i++){
            classBuilder.append("?");
            classBuilder.append(classes.get(i));
            classBuilder.append(" ");

            sparqlQueryStringBuilder.append("{");
            sparqlQueryStringBuilder.append("?");
            sparqlQueryStringBuilder.append(classes.get(i));
            sparqlQueryStringBuilder.append(" rdf:type ex:");
            sparqlQueryStringBuilder.append(classes.get(i));
            sparqlQueryStringBuilder.append(" .\n");
            sparqlQueryStringBuilder.append("?");
            sparqlQueryStringBuilder.append(classes.get(i));
            sparqlQueryStringBuilder.append(" ?property ?value .\n");
            sparqlQueryStringBuilder.append("FILTER (isLiteral(?value))\n");
            sparqlQueryStringBuilder.append("}\n");
            if (i < classes.size() - 1) {
                sparqlQueryStringBuilder.append("UNION");
            }
        };
        return "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX ex: <http://www.ebook-searching.org/ontology#>\n" +
                "SELECT \n" +
                classBuilder.toString() + "\n" +
                "(GROUP_CONCAT(CONCAT(STRAFTER(STR(?property), \"#\"), \"=\", STR(?value)); SEPARATOR=\"|| \") AS ?properties)\n" +
                "WHERE {\n" +
                sparqlQueryStringBuilder.toString() +
                "} Group by " + classBuilder.toString();
    }


    public static String QUERY_SINGLE_CLASS(String className) {
        return "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX ex: <http://www.ebook-searching.org/ontology#>\n" +
                "SELECT \n" +
                "?" + className + "\n" +
                "(GROUP_CONCAT(CONCAT(STRAFTER(STR(?property), \"#\"), \"=\", STR(?value)); SEPARATOR=\"|| \") AS ?properties)\n" +
                "WHERE {\n" +
                "?" + className + " rdf:type ex:" + className +" .\n" +
                "?" + className + " ?property ?value .\n" +
                "FILTER (isLiteral(?value))\n" +
                "} Group by  ?" + className;
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
                "PREFIX ex: <http://www.ebook-searching.org/ontology#>\n" +
                "SELECT " +
                "?" + String.join(" ?", selectClass)+
                " (GROUP_CONCAT(CONCAT(STRAFTER(STR(?property), \"#\"), \"=\", STR(?value)); SEPARATOR=\"|| \") AS ?properties)" +

                "\n" +
                "WHERE {\n" +
                sparqlQueryStringBuilder.toString() +
                "} \n" +
                "GROUP BY " +
                " ?" + String.join(" ?", selectClass);
    }

    public static String GET_KEYWORDS_OF_CLASS_N_LABEL() {
        return "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX ebook: <http://www.ebook-searching.org/ontology#>\n" +
                "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "SELECT \n" +
                "  (strafter(str(?class), \"#\") AS ?className) \n" +
                "  (GROUP_CONCAT(?label; separator=\"||\") AS ?labels)\n" +
                "WHERE {\n" +
                "  ?class a owl:Class .\n" +
                "  ?class rdfs:label ?label .\n" +
                "  \n" +
                "  FILTER(STRSTARTS(STR(?class), \"http://www.ebook-searching.org/ontology#\"))\n" +
                "}\n" +
                "GROUP BY ?class\n";
    }

    public static String GET_KEYWORDS_OF_OBJECT_PROPERTY(){
        return "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX ex: <http://www.ebook-searching.org/ontology#>\n" +
                "SELECT \n" +
                "(strafter(str(?property), \"#\") AS ?objectPropertyName) \n"+
                "(GROUP_CONCAT(DISTINCT ?label; separator=\"||\") AS ?labels)\n" +

        "WHERE {\n" +
                "    ?domain ?property ?range . \n" +
                "    OPTIONAL { ?property rdfs:label ?label . } \n" +
                "    FILTER (STRSTARTS(STR(?domain), \"http://www.ebook-searching.org/ontology#\")) \n" +
                "    FILTER (STRSTARTS(STR(?range), \"http://www.ebook-searching.org/ontology#\")) \n" +
                "    FILTER (STRSTARTS(STR(?property), \"http://www.ebook-searching.org/ontology#\")) \n" +
                "}\n" +
                "GROUP BY ?property";
    }

    public static String GET_KEYWORDS_OF_INDIVIDUAL_N_DATA() {
        return "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                "PREFIX ebook: <http://www.ebook-searching.org/ontology#>\n" +
                "SELECT \n" +
                "(strafter(str(?class), \"#\") AS ?className)\n" +
                "(strafter(str(?individual), \"#\") AS ?individualName)\n" +
                "(GROUP_CONCAT(CONCAT(STRAFTER(STR(?property), \"#\"), \"=\", STR(?value)); SEPARATOR=\"|| \") AS ?properties)\n" +
                "WHERE {  \n" +
                "    ?class a owl:Class .\n" +
                "    FILTER(STRSTARTS(STR(?class), \"http://www.ebook-searching.org/ontology#\")) .\n" +
                "    \n" +
                "    ?individual a ?class .\n" +
                "    \n" +
                "    ?individual  ?property ?value .\n" +
                "}\n" +
                "GROUP BY ?class ?individual";
    }

}