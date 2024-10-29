package org.ebook_searching.admin.mapper;

import org.ebook_searching.admin.model.Author;
import org.ebook_searching.admin.model.Book;
import org.ebook_searching.common.mapper.DateMapper;
import org.ebook_searching.common.mapper.StringValueMapper;
import org.ebook_searching.common.utils.StringUtils;
import org.ebook_searching.proto.Event;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring", uses = {StringUtils.class, DateMapper.class, StringValueMapper.class}, collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface EventMapper {

    @Mapping(target = "authorsList", source = "authors")
    Event.AddBookEvent toBookEvent(Book addBookRequest);

    Event.Author toAuthor(Author author);

}