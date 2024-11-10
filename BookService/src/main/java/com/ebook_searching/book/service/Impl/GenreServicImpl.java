package com.ebook_searching.book.service.Impl;

import com.ebook_searching.book.mapper.EventMapper;
import com.ebook_searching.book.mapper.GenreMapper;
import com.ebook_searching.book.model.author.Author;
import com.ebook_searching.book.model.genre.Genre;
import com.ebook_searching.book.repository.GenreRepository;
import com.ebook_searching.book.service.GenreService;
import org.ebook_searching.common.exception.RecordNotFoundException;
import org.ebook_searching.proto.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GenreServicImpl implements GenreService {

    @Value(value = "${spring.kafka.consumer.add-genre-topic}")
    private String addGenreTopic;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private GenreMapper genreMapper;

    @Autowired
    private KafkaTemplate<String, Event.Genre> addGenreEventPublisher;

    @Autowired
    private EventMapper eventMapper;

    @Override
    public void addGenre(Event.Genre request) {
        // Convert the AddAuthorRequest to a Author entity
        Genre genre = genreMapper.toGenre(request);

        // Convert the AddGenreRequest to a Genre entity
        genre.setId(null);
        genreRepository.save(genre);
    }

    @Override
    public List<Genre> getAllGenres() {
        return genreRepository.findAll().stream().map(genre -> genreMapper.toGenre(genre)).toList();
    }
}
