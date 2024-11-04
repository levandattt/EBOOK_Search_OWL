package org.ebook_searching.admin.service.impl;

import org.ebook_searching.admin.dto.AuthorDetail;
import org.ebook_searching.admin.mapper.AuthorMapper;
import org.ebook_searching.admin.mapper.EventMapper;
import org.ebook_searching.admin.model.Author;
import org.ebook_searching.admin.model.Book;
import org.ebook_searching.admin.payload.request.AddAuthorRequest;
import org.ebook_searching.admin.payload.request.UpdateAuthorRequest;
import org.ebook_searching.admin.payload.response.*;
import org.ebook_searching.admin.repository.AuthorRepository;
import org.ebook_searching.admin.service.AuthorService;
import org.ebook_searching.common.exception.InvalidFieldsException;
import org.ebook_searching.common.exception.RecordNotFoundException;
import org.ebook_searching.proto.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {
    @Value(value = "${spring.kafka.consumer.add-author-topic}")
    private String addAuthorTopic;

    @Autowired
    private AuthorMapper authorMapper;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private KafkaTemplate<String, Event.Author> addAuthorEventPublisher;


    @Autowired
    private EventMapper eventMapper;

    @Override
    public AddAuthorResponse addAuthor(AddAuthorRequest request) {
        // Convert the AddAuthorRequest to a Author entity
        Author author = authorMapper.toAuthor(request);

        author.setId(null);
        authorRepository.save(author);

        addAuthorEventPublisher.send(addAuthorTopic,
                eventMapper.toAuthor(author));

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

    @Override
    public AuthorDetail findAuthorDetailById(Long id) {
        Author author = findById(id);
        return authorMapper.toAuthorDetail(author);
    }
}
