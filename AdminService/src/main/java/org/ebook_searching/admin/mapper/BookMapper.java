package org.ebook_searching.admin.mapper;

import org.ebook_searching.admin.dto.BookDetail;
import org.ebook_searching.admin.model.Book;
import org.ebook_searching.admin.payload.request.AddBookRequest;
import org.ebook_searching.admin.payload.request.UpdateBookRequest;
import org.ebook_searching.admin.payload.response.AddBookResponse;
import org.ebook_searching.admin.payload.response.GetBookResponse;
import org.ebook_searching.admin.payload.response.UpdateBookResponse;
import org.ebook_searching.common.utils.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring", uses = {StringUtils.class, AuthorMapper.class})
public interface BookMapper {
//    @Mapping(target = "genres", source = "genres", qualifiedByName = "toSingleString")
    @Mapping(target = "categories", source = "categories", qualifiedByName = "toSingleString")
    Book toBook(AddBookRequest request);

    @Mapping(target = "publicationTime", source = "publishedAt")
    @Mapping(target = "categories", ignore = true)
//    @Mapping(target = "genres", source = "genres", qualifiedByName = "toStringList")
    BookDetail toBookDetail(Book request);

//    @Mapping(target = "genres", source = "genres", qualifiedByName = "toStringList")
    @Mapping(target = "categories", source = "categories", qualifiedByName = "toStringList")
    AddBookResponse toAddBookResponse(Book book);

//    @Mapping(target = "genres", source = "genres", qualifiedByName = "toSingleString")
    @Mapping(target = "categories", source = "categories", qualifiedByName = "toSingleString")
    void updateBookFromRequest(@MappingTarget Book book, UpdateBookRequest request);

//    @Mapping(target = "genres", source = "genres", qualifiedByName = "toStringList")
    @Mapping(target = "categories", source = "categories", qualifiedByName = "toStringList")
    UpdateBookResponse toUpdateBookResponse(Book book);

//    @Mapping(target = "genres", source = "genres", qualifiedByName = "toStringList")
    @Mapping(target = "categories", source = "categories", qualifiedByName = "toStringList")
    GetBookResponse toGetBookResponse(Book book);
}
