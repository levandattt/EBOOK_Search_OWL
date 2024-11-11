package com.ebook_searching.book.mapper;

import com.ebook_searching.book.adapter.ontology_client.OWLAuthor;
import com.ebook_searching.book.dto.AuthorDetail;
import com.ebook_searching.book.dto.GenreDetail;
import com.ebook_searching.book.model.author.Author;
import com.ebook_searching.book.model.genre.Genre;
import com.google.protobuf.StringValue;
import org.ebook_searching.common.mapper.DateMapper;
import org.ebook_searching.proto.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = DateMapper.class)
public interface GenreMapper {
    Genre toGenre(Event.Genre genre);
    Genre toGenre(Genre genre);
    GenreDetail toGenreDetail(Genre genre);
    @Mapping(target = "id", ignore = true)
    void updateGenreFromRequest(@MappingTarget Genre genre, Event.Genre request);
    default String map(StringValue value) {
        return value == null ? null : value.getValue();
    }
}