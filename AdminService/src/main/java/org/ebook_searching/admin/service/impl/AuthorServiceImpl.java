package org.ebook_searching.admin.service.impl;

import org.ebook_searching.admin.exception.InvalidFieldsException;
import org.ebook_searching.admin.exception.RecordNotFoundException;
import org.ebook_searching.admin.mapper.AuthorMapper;
import org.ebook_searching.admin.model.Author;
import org.ebook_searching.admin.payload.request.AddAuthorRequest;
import org.ebook_searching.admin.payload.request.UpdateAuthorRequest;
import org.ebook_searching.admin.payload.response.*;
import org.ebook_searching.admin.repository.AuthorRepository;
import org.ebook_searching.admin.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {
    @Autowired
    private AuthorMapper authorMapper;

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public AddAuthorResponse addAuthor(AddAuthorRequest request) {
        // Convert the AddAuthorRequest to a Author entity
        Author author = authorMapper.toAuthor(request);

        author.setId(null);
        authorRepository.save(author);

        // TODO: publish event
//        addAuthorEventPublisher.send(addAuthorTopic,
//                eventMapper.toAuthorEvent(author));

        // Convert the saved Author entity to AddAuthorResponse
        return authorMapper.toAddAuthorResponse(author);
    }

    @Override
    public UpdateAuthorResponse updateAuthor(UpdateAuthorRequest request) {
        // validate
        Author existingAuthor = findById(request.getId());

        // update all the field
        authorMapper.updateAuthorFromRequest(existingAuthor, request);
        authorRepository.save(existingAuthor);

        // TODO: publish event

        return authorMapper.toUpdateAuthorResponse(existingAuthor);
    }

    @Override
    public DeleteAuthorResponse deleteAuthor(Long id) {
        Author authorSelected = findById(id);
        if (!authorSelected.getBooks().isEmpty()) {
            throw InvalidFieldsException.fromFieldError("id", "Không thể tác giả này vì còn sách đang quản lý!");
        }

        authorRepository.deleteById(authorSelected.getId());
        DeleteAuthorResponse response = new DeleteAuthorResponse();
        response.setId(id);
        return response;
    }

    @Override
    public Author findById(Long id) {
        Optional<Author> optionalAuthor = authorRepository.findById(id);
        if (optionalAuthor.isEmpty()) {
            throw new RecordNotFoundException("Không tồn tại tác giả này");
        } else return optionalAuthor.get();
    }

    @Override
    public List<GetAuthorResponse> getAllAuthors() {
        return authorRepository.findAll().stream().map(author -> authorMapper.toGetAuthorResponse(author)).toList();
    }
}