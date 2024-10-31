package com.ebook_searching.book.mapper;

import com.ebook_searching.book.model.Author;
import com.google.protobuf.StringValue;
import org.ebook_searching.proto.Event;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    Author toAuthor(Event.Author author);
    void updateAuthorFromRequest(@MappingTarget Author author, Event.Author request);

    default String map(StringValue value) {
        return value == null ? null : value.getValue();
    }
}
