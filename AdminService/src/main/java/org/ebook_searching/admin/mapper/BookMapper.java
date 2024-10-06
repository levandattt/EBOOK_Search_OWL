package org.ebook_searching.admin.mapper;

import org.ebook_searching.admin.model.Author;
import org.ebook_searching.admin.model.Book;
import org.ebook_searching.admin.payload.request.AddBookRequest;
import org.ebook_searching.admin.payload.response.AddBookResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Set;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring")
public interface BookMapper {
    // Mapping from AddBookRequest to Book entity
    Book toBook(AddBookRequest request);

    // Mapping from Book entity to AddBookResponse DTO
//    @Mapping(source = "authors", target = "authorIds", qualifiedByName = "mapAuthorsToIds")
    AddBookResponse toAddBookResponse(Book book);

//    // Custom mapping method to map Author entities to authorIds
//    @Named("mapAuthorsToIds")
//    static Set<Long> mapAuthorsToIds(Set<Author> authors) {
//        return authors.stream().map(Author::getId).collect(Collectors.toSet());
//    }
}
