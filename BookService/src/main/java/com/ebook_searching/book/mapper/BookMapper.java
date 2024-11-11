package com.ebook_searching.book.mapper;

import com.ebook_searching.book.adapter.ontology_client.OWLBook;
import com.ebook_searching.book.dto.BaseBook;
import com.ebook_searching.book.dto.BookDetail;
import com.ebook_searching.book.model.book.Book;
import com.google.protobuf.StringValue;
import org.ebook_searching.common.utils.StringUtils;
import org.ebook_searching.proto.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring", uses = {StringUtils.class, AuthorMapper.class})
public interface BookMapper {

    @Mapping(target="genres", ignore = true)
    Book toBook(Event.AddBookEvent request);

    @Mapping(target = "publicationTime", source = "publishedAt")
    @Mapping(target = "categories", ignore = true)
    BookDetail toBookDetail(Book request);

    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "genres", ignore = true)
    BookDetail toBookDetail(OWLBook request);

    BaseBook toBaseBook(OWLBook book);

    BaseBook toBaseBook(Book book);

    @Mapping(target = "id", ignore = true)
    @Mapping(target="genres", ignore = true)
    void updateBookFromRequest(@MappingTarget Book book, Event.AddBookEvent request);

    default String map(StringValue value) {
        return value == null ? null : value.getValue();
    }
}
