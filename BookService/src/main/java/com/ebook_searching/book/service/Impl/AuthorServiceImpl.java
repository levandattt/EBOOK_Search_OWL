package com.ebook_searching.book.service.Impl;

import com.ebook_searching.book.mapper.AuthorMapper;
import com.ebook_searching.book.mapper.EventMapper;
import com.ebook_searching.book.model.author.Author;
import com.ebook_searching.book.repository.AuthorRepository;
import com.ebook_searching.book.service.AuthorService;
import org.ebook_searching.proto.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
    private EventMapper eventMapper;

    @Override
    public void addAuthor(Event.Author request) {
        // Convert the AddAuthorRequest to a Author entity
        Author author = authorMapper.toAuthor(request);

        author.setId(null);
        authorRepository.save(author);
    }

    @Override
    public void updateAuthor(Event.Author request) {
        // validate
        Optional<Author> optionalAuthor = authorRepository.findById(request.getId());

        if (optionalAuthor.isEmpty()) {
            return ;
        }
        Author existingAuthor = optionalAuthor.get();

        // update all the field
        authorMapper.updateAuthorFromRequest(existingAuthor, request);
        authorRepository.save(existingAuthor);
    }
}
