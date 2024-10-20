package org.ebook_searching.admin.mapper;

import org.ebook_searching.admin.model.Author;
import org.ebook_searching.admin.model.Book;
import org.ebook_searching.admin.payload.request.AddBookRequest;
import org.ebook_searching.admin.payload.request.UpdateBookRequest;
import org.ebook_searching.admin.payload.response.AddBookResponse;
import org.ebook_searching.admin.payload.response.GetBookResponse;
import org.ebook_searching.admin.payload.response.UpdateBookResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Set;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring")
public interface BookMapper {
    Book toBook(AddBookRequest request);

    AddBookResponse toAddBookResponse(Book book);

    Book toBook(UpdateBookRequest request);

    UpdateBookResponse toUpdateBookResponse(Book book);

    GetBookResponse toGetBookResponse(Book book);
}
