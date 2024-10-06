package com.ebook_searching.ontology.constants;

public class SpartQueryConstant {
    private SpartQueryConstant() {}

    private static final String PREFIX = "PREFIX ex: <http://www.example.org/ebook#> ";
    private static final String PREFIX_RDFS = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> ";
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
//        return PREFIX + PREFIX_RDFS +
//                "SELECT ?title ?author ?genre ?isbn ?publisher" +
//                "WHERE { " +
//                "?book a ex:Ebook . " +
//                "?book ex:isbn ?isbn . " +
//                "?book ex:writtenBy ?author . " +
//                "?book ex:publishedBy ?publisher . " +
//                "?book ex:belongsToGenre ?genre . " +
//                "?author rdfs:label ?authorName . " +
//                "FILTER (str(?authorName) = \"" + authorName + "\") " +
//                "OPTIONAL { ?book rdfs:label ?title . } " +
//                "}";
    }
}
