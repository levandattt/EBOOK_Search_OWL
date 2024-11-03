package org.ebook_searching.admin.service;

import org.ebook_searching.admin.dto.AuthorDetail;
import org.ebook_searching.admin.model.Author;
import org.ebook_searching.admin.payload.request.AddAuthorRequest;
import org.ebook_searching.admin.payload.request.UpdateAuthorRequest;
import org.ebook_searching.admin.payload.response.AddAuthorResponse;
import org.ebook_searching.admin.payload.response.DeleteAuthorResponse;
import org.ebook_searching.admin.payload.response.GetAuthorResponse;
import org.ebook_searching.admin.payload.response.UpdateAuthorResponse;

import java.util.List;

public interface AuthorService {
    AddAuthorResponse addAuthor(AddAuthorRequest book);
    UpdateAuthorResponse updateAuthor(UpdateAuthorRequest book);
    DeleteAuthorResponse deleteAuthor(Long id);
    Author findById(Long id);
    List<GetAuthorResponse> getAllAuthors();

    AuthorDetail findAuthorDetailById(Long id);
}
