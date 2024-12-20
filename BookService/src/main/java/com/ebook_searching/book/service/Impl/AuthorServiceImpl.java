package com.ebook_searching.book.service.Impl;

import com.ebook_searching.book.dto.AuthorDetail;
import com.ebook_searching.book.mapper.AuthorMapper;
import com.ebook_searching.book.mapper.EventMapper;
import com.ebook_searching.book.model.author.Author;
import com.ebook_searching.book.model.book.Book;
import com.ebook_searching.book.repository.AuthorRepository;
import com.ebook_searching.book.service.AuthorService;
import org.ebook_searching.common.exception.RecordNotFoundException;
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

        // Check if the author already exists
        Optional<Author> optionalAuthor = authorRepository.findByUuid(author.getUuid());
        if(optionalAuthor.isEmpty()) {
            author.setId(null);
            authorRepository.save(author);
        }
    }

    @Override
    public void updateAuthor(Event.Author request) {
        // validate
        Optional<Author> optionalAuthor = authorRepository.findByUuid(request.getUuid());

        if (optionalAuthor.isEmpty()) {
            return ;
        }
        Author existingAuthor = optionalAuthor.get();

        // update all the field
        authorMapper.updateAuthorFromRequest(existingAuthor, request);
        authorRepository.save(existingAuthor);
    }

    public Author findById(Long id) {
        Optional<Author> optionalAuthor = authorRepository.findById(id);
        if (optionalAuthor.isEmpty()) {
            throw new RecordNotFoundException("Không tồn tại tác giả này");
        } else return optionalAuthor.get();
    }

    @Override
    public AuthorDetail findAuthorDetailById(Long id) {
        Author author = findById(id);
        return authorMapper.toAuthor(author);
    }

    @Override
    public void deleteAuthor(String uuid) {
        Optional<Author> optionalAuthor = authorRepository.findByUuid(uuid);

        optionalAuthor.ifPresent(author -> authorRepository.deleteById(author.getId()));
    }
}
