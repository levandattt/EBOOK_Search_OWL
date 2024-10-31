package com.ebook_searching.book.mapper;

import com.ebook_searching.book.model.Book;
import com.google.protobuf.StringValue;
import org.ebook_searching.common.utils.StringUtils;
import org.ebook_searching.proto.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring", uses = {StringUtils.class, AuthorMapper.class})
public interface BookMapper {
    Book toBook(Event.AddBookEvent request);

    void updateBookFromRequest(@MappingTarget Book book, Event.AddBookEvent request);

    default String map(StringValue value) {
        return value == null ? null : value.getValue();
    }
}
