package com.ebook_searching.ontology.service.Impl;

import com.ebook_searching.ontology.model.Book;
import com.ebook_searching.ontology.model.Ontology.OWLAuthor;
import com.ebook_searching.ontology.model.Ontology.OWLBook;
import com.ebook_searching.ontology.service.JsonParserService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class JsonParserServiceImpl implements JsonParserService {

    private DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public List<OWLAuthor> parseAuthors(JsonNode root) {
        List<OWLAuthor> authors = new ArrayList<>();
        JsonNode bindings = root.path("results").path("bindings");

        bindings.forEach(binding -> {
            if (binding.has("Author")) {
                OWLAuthor author = new OWLAuthor();
                author.setUri(binding.path("Author").path("value").asText());

                String[] properties = binding.path("properties").path("value").asText().split("\\|\\|");
                for (String property : properties) {
                    System.out.println("Property:"+property);
                    String[] keyValue = property.split("=");
                    switch (keyValue[0].trim()) {
                        case "name": author.setName(keyValue[1]); break;
                        case "birthDate": author.setBirthDate(LocalDate.parse(keyValue[1], DateTimeFormatter.ISO_LOCAL_DATE)); break;
                        case "birthPlace": author.setBirthPlace(keyValue[1]); break;
                        case "website": author.setWebsite(keyValue[1]); break;
                        case "description": author.setDescription(keyValue[1]); break;
                        case "imageLink": author.setImageLink(keyValue[1]); break;
                        case "deathDate": author.setDeathDate(LocalDate.parse(keyValue[1], DateTimeFormatter.ISO_LOCAL_DATE)); break;
                        case "nationality": author.setNationality(keyValue[1]); break;
                    }
                }
                System.out.println("Author Data"+author);
                authors.add(author);
            }
        });

        return authors;
    }

    @Override
    public List<OWLBook> parseBooks(JsonNode root) {
        List<OWLBook> books = new ArrayList<>();
        JsonNode bindings = root.path("results").path("bindings");

        bindings.forEach(binding -> {
            if (binding.has("Ebook")) {
                OWLBook book = new OWLBook();
                book.setUri(binding.path("Ebook").path("value").asText());
                String[] properties = binding.path("properties").path("value").asText().split("\\|\\|");
                for (String property : properties) {
                    String[] keyValue = property.split("=");
                    switch (keyValue[0]) {
                        case "title": book.setTitle(keyValue[1]); break;
                        case "avgRating": book.setAvgRatings(Double.parseDouble(keyValue[1])); break;
                        case "ratingCount": book.setRatingsCount(Integer.parseInt(keyValue[1])); break;
                        case "reviewCount": book.setReviewCount(Integer.parseInt(keyValue[1])); break;
                        case "publicationTime": book.setPublishedAt(LocalDate.parse(keyValue[1], dateFormatter)); break;
                        case "totalPages": book.setTotalPages(Integer.parseInt(keyValue[1])); break;
                    }
                }
                books.add(book);
            }
        });
        return books;
    }
}
