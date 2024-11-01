package com.ebook_searching.book.mapper;

import com.ebook_searching.book.dto.AuthorDetail;
import com.ebook_searching.book.model.author.Author;
import com.google.protobuf.StringValue;
import org.ebook_searching.common.mapper.DateMapper;
import org.ebook_searching.proto.Event;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = DateMapper.class)
public interface AuthorMapper {
    Author toAuthor(Event.Author author);
    AuthorDetail toAuthor(Author author);
    void updateAuthorFromRequest(@MappingTarget Author author, Event.Author request);

    default String map(StringValue value) {
        return value == null ? null : value.getValue();
    }
}
