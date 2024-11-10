package org.ebook_searching.admin.mapper;

import org.ebook_searching.admin.dto.GenreDetail;
import org.ebook_searching.admin.model.Genre;
import org.ebook_searching.admin.payload.request.AddGenreRequest;
import org.ebook_searching.admin.payload.request.UpdateGenreRequest;
import org.ebook_searching.admin.payload.response.AddGenreResponse;

import org.ebook_searching.admin.payload.response.GetGenreResponse;
import org.ebook_searching.admin.payload.response.UpdateGenreResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface GenreMapper {
    GenreDetail toGenreDetail(Genre Genre);
    Genre toGenre(AddGenreRequest request);
    Genre toGenre(UpdateGenreRequest request);
    AddGenreResponse toAddGenreResponse(Genre Genre);
    UpdateGenreResponse toUpdateGenreResponse(Genre Genre);
    void updateGenreFromRequest(@MappingTarget Genre Genre, UpdateGenreRequest request);
    GetGenreResponse toGetGenreResponse(Genre Genre);
}
