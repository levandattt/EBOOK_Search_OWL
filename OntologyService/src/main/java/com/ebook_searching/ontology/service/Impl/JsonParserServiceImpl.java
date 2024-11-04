package com.ebook_searching.ontology.service.Impl;

import com.ebook_searching.ontology.model.Book;
import com.ebook_searching.ontology.model.Ontology.OWLAuthor;
import com.ebook_searching.ontology.model.Ontology.OWLBook;
import com.ebook_searching.ontology.service.JsonParserService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
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
                    String[] keyValue = property.split("=");
                    switch (keyValue[0].trim()) {
                        case "authorStageName": {
                            String stageName = author.getStageName();
                            if(stageName == null){
                                author.setStageName(keyValue[1]);
                            }else{
                                if(!stageName.contains(keyValue[1])) {
                                    author.setStageName(author.getStageName() + ',' + keyValue[1]);
                                }
                            }
                        } break;
                        case "authorName": author.setName(keyValue[1]); break;
                        case "authorBirthDate": author.setBirthDate(keyValue[1]); break;
//                        case "birthDate": author.setBirthDate(LocalDate.parse(keyValue[1], DateTimeFormatter.ISO_LOCAL_DATE)); break;
                        case "authorBirthPlace": author.setBirthPlace(keyValue[1]); break;
                        case "authorWebsite": author.setWebsite(keyValue[1]); break;
                        case "authorDescription": author.setDescription(keyValue[1]); break;
                        case "authorImage": author.setImage(keyValue[1]); break;
                        case "authorDeathDate": author.setDeathDate(keyValue[1]); break;
                        case "authorUuid": author.setUuid(keyValue[1]); break;
//                        case "deathDate": author.setDeathDate(LocalDate.parse(keyValue[1], DateTimeFormatter.ISO_LOCAL_DATE)); break;
                        case "authorNationality": author.setNationality(keyValue[1]); break;
                    }
                }
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
            if (binding.has("Book")) {
                OWLBook book = new OWLBook();
                book.setUri(binding.path("Book").path("value").asText());
                String[] properties = binding.path("properties").path("value").asText().split("\\|\\|");
                for (String property : properties) {
                    String[] keyValue = property.split("=");
                    switch (keyValue[0].trim()) {
                        case "title": book.setTitle(keyValue[1]); break;
                        case "avgRating": {
                            book.setAvgRating(Double.parseDouble(keyValue[1].trim()));
                        } break;
                        case "ratingCount": {
                            book.setRatingCount(Integer.parseInt(keyValue[1]));
                        } break;
                        case "reviewCount": {
                            book.setReviewCount(Integer.parseInt(keyValue[1]));
                        } break;
                        case "publicationTime": {
                            System.out.println(keyValue[1]);
                            LocalDate date = LocalDate.parse(keyValue[1]);

                            // Convert it to LocalDateTime at midnight
                            LocalDateTime dateTime = date.atStartOfDay();

                            // Convert LocalDateTime to Unix timestamp (seconds since epoch)
                            long unixTimestamp = dateTime.toEpochSecond(ZoneOffset.UTC);
                            book.setPublicationTime(unixTimestamp);
                        } break;
                        case "totalPages": {
                            book.setTotalPages(Integer.parseInt(keyValue[1]));

                        } break;
                        case "bookUuid": {
                            book.setUuid(keyValue[1]);

                        } break;
                        case "genre": {
                            book.setGenre(keyValue[1]);
                        } break;
                        case "description": {
                            book.setDescription(keyValue[1]);
                        } break;
                        case "publisher": {
                            book.setPublisher(keyValue[1]);
                        } break;
                        case "language": {
                            book.setLanguage(keyValue[1]);
                        } break;
                    }
                }
                books.add(book);
            }
        });
        return books;
    }
}
