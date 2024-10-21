package org.ebook_searching.admin.mapper;

import org.ebook_searching.admin.model.Book;
import org.ebook_searching.common.mapper.DateMapper;
import org.ebook_searching.proto.Event;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = DateMapper.class)
public interface EventMapper {
    Event.AddBookEvent toBookEvent(Book addBookRequest);
}
