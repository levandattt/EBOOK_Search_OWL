package com.ebook_searching.ontology.constants;

import java.util.List;

public class SpartQueryConstant {
    private SpartQueryConstant() {}

    private static final String PREFIX = "PREFIX ex: <http://www.example.org/ebook#> ";
    private static final String PREFIX_RDFS = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> ";
    public static final String RETRIEVES_ALL_CLASSES = "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
            "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
            "SELECT DISTINCT ?class\n" +
            "WHERE {\n" +
            "  { ?class a owl:Class . }\n" +
            "  \n" +
            "}";
    public static String SEARCH_BOOK_BY_AUTHOR (String authorName) {
        return "PREFIX ex: <http://www.example.org/ebook#>\n" +
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
        for (String property : dataProperties) {
            valuesPart.append("LCASE(\"");
            valuesPart.append(property);
            if (dataProperties.indexOf(property) == dataProperties.size() - 1) {
                valuesPart.append("\")");
            } else {
                valuesPart.append("\"),");
            }
        }
        valuesPart.append("))");
        return "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX ex: <http://example.org/ontology#> \n" +
                "PREFIX owl: <http://www.w3.org/2002/07/owl#> \n" +
                "SELECT (STRAFTER(STR(?class), \"#\") AS ?className) ?name WHERE {\n" +
                "  ?individual ?property ?name .\n" +
                "  ?individual rdf:type ?class . \n" +
                "  FILTER(isIRI(?class)) \n" +
                "  FILTER(!strstarts(str(?class), str(owl:))) \n" +
                valuesPart.toString() +
                "}";
    }


    public static String GET_OBJECTPROPERTIES_BETWEEN_CLASSES(String classA, String classB) {

        return "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                "SELECT DISTINCT \n" +
                "    (strafter(str(?property), \"#\") AS ?propertyName) \n" +
                "    (strafter(str(?domain), \"#\") AS ?domainName) \n" +
                "    (strafter(str(?range), \"#\") AS ?rangeName) \n" +
                "WHERE {\n" +
                "    # Tìm lớp Author\n" +
                "    ?classA rdfs:label ?labelA .\n" +
                "    FILTER (lcase(str(?labelA)) = \"" + classA.toLowerCase() + "\")\n" +
                "    \n" +
                "    # Tìm lớp Ebook\n" +
                "    ?classB rdfs:label ?labelB .\n" +
                "    FILTER (lcase(str(?labelB)) = \"" + classB.toLowerCase() + "\")\n" +
                "    \n" +
                "    {\n" +
                "        ?property rdf:type owl:ObjectProperty .\n" +
                "        ?property rdfs:domain ?classA .\n" +
                "        ?property rdfs:range ?classB .\n" +
                "        BIND(?classA AS ?domain)\n" +
                "        BIND(?classB AS ?range)\n" +
                "    }\n" +
                "    UNION\n" +
                "    {\n" +
                "        ?property rdf:type owl:ObjectProperty .\n" +
                "        ?property rdfs:domain ?classB .\n" +
                "        ?property rdfs:range ?classA .\n" +
                "        BIND(?classB AS ?domain)\n" +
                "        BIND(?classA AS ?range)\n" +
                "    }\n" +
                "}\n" +
                "ORDER BY ?propertyName\n";
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
        sparqlQueryString.append("SELECT DISTINCT (strafter(str(?class), \"#\") AS ?className) ?label WHERE { ");
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

}
