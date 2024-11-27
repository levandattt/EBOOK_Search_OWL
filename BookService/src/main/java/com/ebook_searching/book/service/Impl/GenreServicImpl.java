package com.ebook_searching.book.service.Impl;

import com.ebook_searching.book.dto.GenreDetail;
import com.ebook_searching.book.mapper.EventMapper;
import com.ebook_searching.book.mapper.GenreMapper;
import com.ebook_searching.book.model.OrderCriteria;
import com.ebook_searching.book.model.Pagination;
import com.ebook_searching.book.model.genre.Genre;
import com.ebook_searching.book.payload.PaginationResponse;
import com.ebook_searching.book.repository.GenreRepository;
import com.ebook_searching.book.service.GenreService;
import org.ebook_searching.proto.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        Genre genre = genreMapper.toGenre(request);

        Optional<Genre> optionalGenre = genreRepository.findByUuid(genre.getUuid());

        if (optionalGenre.isEmpty()) {
            genre.setId(null);
            genreRepository.save(genre);;
        }
    }

    @Override
    public PaginationResponse<GenreDetail> getAllGenres(Pagination pagination, OrderCriteria orderCriteria) {
        Pageable pageable = PageRequest.of(
                pagination.getOffset() / pagination.getLimit(),
                pagination.getLimit(),
                Sort.by(Sort.Direction.fromString(orderCriteria.getOrderDirection()), orderCriteria.getOrderBy())
        );

        Page<Genre> genrePage =  genreRepository.findAll(pageable);

        List<Genre> genres = genrePage.getContent();

        PaginationResponse<GenreDetail> result = PaginationResponse.<GenreDetail>builder()
                .numPages(genrePage.getTotalPages())
                .offset(pagination.getOffset())
                .limit(pagination.getLimit())
                .totalItems((int) genrePage.getTotalElements())
                .data(genres.stream().map(genreMapper::toGenreDetail).toList())
                .build();

        return result;
    }

    @Override
    public void updateGenre(Event.Genre request) {
        Optional<Genre> optionalGenre = genreRepository.findByUuid(request.getUuid());

        if (optionalGenre.isEmpty()) {
            return;
        }

        Genre existingGenre = optionalGenre.get();
            genreMapper.updateGenreFromRequest(existingGenre, request);
        genreRepository.save(existingGenre);
    }

    @Override
    public void deleteGenre(String uuid) {
            Optional<Genre> optionalGenre = genreRepository.findByUuid(uuid);
            optionalGenre.ifPresent(genre -> genreRepository.deleteById(genre.getId()));
    }
}


