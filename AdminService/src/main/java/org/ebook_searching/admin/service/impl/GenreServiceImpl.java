package org.ebook_searching.admin.service.impl;

import org.apache.tomcat.util.http.fileupload.InvalidFileNameException;
import org.ebook_searching.admin.mapper.EventMapper;
import org.ebook_searching.admin.mapper.GenreMapper;
import org.ebook_searching.admin.model.Author;
import org.ebook_searching.admin.model.Genre;
import org.ebook_searching.admin.model.OrderCriteria;
import org.ebook_searching.admin.model.Pagination;
import org.ebook_searching.admin.payload.PaginationResponse;
import org.ebook_searching.admin.payload.request.AddGenreRequest;
import org.ebook_searching.admin.payload.request.UpdateGenreRequest;
import org.ebook_searching.admin.payload.response.*;
import org.ebook_searching.admin.repository.GenreRepository;
import org.ebook_searching.admin.service.GenreService;
import org.ebook_searching.common.exception.InvalidFieldsException;
import org.ebook_searching.common.exception.RecordNotFoundException;
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
public class GenreServiceImpl implements GenreService {

    @Value(value = "${spring.kafka.consumer.add-genre-topic}")
    private String addGenreTopic;

    @Value(value = "${spring.kafka.consumer.update-genre-topic}")
    private String updateGenreTopic;

    @Value(value = "${spring.kafka.consumer.delete-genre-topic}")
    private String deleteGenreTopic;

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
    public PaginationResponse<GetGenreResponse> getAllGenres(Pagination pagination, OrderCriteria orderCriteria) {
        Pageable pageable = PageRequest.of(
                pagination.getOffset(),
                pagination.getLimit(),
                Sort.by(Sort.Direction.fromString(orderCriteria.getOrderDirection()), orderCriteria.getOrderBy())
        );

        Page<Genre> genrePage =  genreRepository.findAll(pageable);

        List<Genre> genres = genrePage.getContent();

        PaginationResponse<GetGenreResponse> result = PaginationResponse.<GetGenreResponse>builder()
                .numPages(genrePage.getTotalPages())
                .offset(pagination.getOffset())
                .limit(pagination.getLimit())
                .totalItems((int) genrePage.getTotalElements())
                .data(genres.stream().map(genreMapper::toGetGenreResponse).toList())
                .build();

        return result;
    }

    @Override
    public UpdateGenreResponse updateGenre(UpdateGenreRequest request) {
        Genre existingGenre = findById(request.getId());

        // update all the field
        genreMapper.updateGenreFromRequest(existingGenre, request);

        Genre updateResult =  genreRepository.save(existingGenre);

        String oldName = existingGenre.getName();

        addGenreEventPublisher.send(updateGenreTopic,
                eventMapper.toUpdateGenre(existingGenre, oldName));

        return genreMapper.toUpdateGenreResponse(updateResult);
    }

    @Override
    public DeleteGenreResponse deleteGenre(Long id){
        Genre existingGenre = findById(id);
        if (!existingGenre.getBooks().isEmpty()){
          throw InvalidFieldsException.fromFieldError("id", "Cannot delete this genre because there are books under this genre!");
        }

        addGenreEventPublisher.send(deleteGenreTopic,
                eventMapper.toGenre(existingGenre));

        genreRepository.deleteById(existingGenre.getId());
        DeleteGenreResponse response = new DeleteGenreResponse();
        response.setId(id);
        return response;
    }

    @Override
    public Genre findById(Long id) {
        Optional<Genre> optionalGenre = genreRepository.findById(id);
        if (optionalGenre.isEmpty()) {
            throw new RecordNotFoundException("Genre not found with id: " + id);
        } else return optionalGenre.get();
    }

}