package org.ebook_searching.admin.mapper;

import org.ebook_searching.admin.dto.AuthorDetail;
import org.ebook_searching.admin.model.Author;
import org.ebook_searching.admin.payload.request.AddAuthorRequest;
import org.ebook_searching.admin.payload.request.UpdateAuthorRequest;
import org.ebook_searching.admin.payload.response.AddAuthorResponse;
import org.ebook_searching.admin.payload.response.GetAuthorResponse;
import org.ebook_searching.admin.payload.response.UpdateAuthorResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    AuthorDetail toAuthorDetail(Author author);
    Author toAuthor(AddAuthorRequest request);
    AddAuthorResponse toAddAuthorResponse(Author author);
    UpdateAuthorResponse toUpdateAuthorResponse(Author author);
    void updateAuthorFromRequest(@MappingTarget Author author, UpdateAuthorRequest request);
    GetAuthorResponse toGetAuthorResponse(Author author);
}
