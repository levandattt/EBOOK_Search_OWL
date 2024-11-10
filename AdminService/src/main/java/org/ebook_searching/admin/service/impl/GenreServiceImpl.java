package org.ebook_searching.admin.service.impl;

import org.ebook_searching.admin.mapper.EventMapper;
import org.ebook_searching.admin.mapper.GenreMapper;
import org.ebook_searching.admin.model.Author;
import org.ebook_searching.admin.model.Genre;
import org.ebook_searching.admin.payload.request.AddGenreRequest;
import org.ebook_searching.admin.payload.request.UpdateGenreRequest;
import org.ebook_searching.admin.payload.response.*;
import org.ebook_searching.admin.repository.GenreRepository;
import org.ebook_searching.admin.service.GenreService;
import org.ebook_searching.common.exception.RecordNotFoundException;
import org.ebook_searching.proto.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService {

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
    public AddGenreResponse addGenre(AddGenreRequest request) {
        //check if the genre already exists
        Genre genre = genreRepository.findByName(request.getName());
        if (genre != null) {
            return genreMapper.toAddGenreResponse(genre);
        }

        // Convert the AddGenreRequest to a Genre entity
        genre = genreMapper.toGenre(request);

        genre.setId(null);
        genreRepository.save(genre);

        addGenreEventPublisher.send(addGenreTopic,
                eventMapper.toGenre(genre));

        return genreMapper.toAddGenreResponse(genre);
    }

    @Override
    public List<GetGenreResponse> getAllGenres() {
        return genreRepository.findAll().stream().map(genre -> genreMapper.toGetGenreResponse(genre)).toList();
    }

    @Override
    public UpdateGenreResponse updateGenre(UpdateGenreRequest request) {
        // validate
        Genre existingGenre = findById(request.getId());

        // update all the field
        genreMapper.updateGenreFromRequest(existingGenre, request);

        Genre updateResult =  genreRepository.save(existingGenre);

//        addAuthorEventPublisher.send(updateAuthorTopic,
//                eventMapper.toUpdateAuthor(existingAuthor, oldName));

        return genreMapper.toUpdateGenreResponse(updateResult);
    }

    @Override
    public Genre findById(Long id) {
        Optional<Genre> optionalGenre = genreRepository.findById(id);
        if (optionalGenre.isEmpty()) {
            throw new RecordNotFoundException("Genre not found with id: " + id);
        } else return optionalGenre.get();
    }

}