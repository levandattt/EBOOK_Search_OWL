package org.ebook_searching.admin.service;

//import org.ebook_searching.admin.dto.GenreDetail;
import org.ebook_searching.admin.model.Genre;
import org.ebook_searching.admin.payload.request.AddGenreRequest;
//import org.ebook_searching.admin.payload.request.UpdateGenreRequest;
import org.ebook_searching.admin.payload.request.UpdateGenreRequest;
import org.ebook_searching.admin.payload.response.AddGenreResponse;
import org.ebook_searching.admin.payload.response.GetGenreResponse;
import org.ebook_searching.admin.payload.response.UpdateGenreResponse;
//import org.ebook_searching.admin.payload.response.DeleteGenreResponse;
//import org.ebook_searching.admin.payload.response.GetGenreResponse;
//import org.ebook_searching.admin.payload.response.UpdateGenreResponse;
import java.util.List;

public interface GenreService {
    AddGenreResponse addGenre(AddGenreRequest genre);
    UpdateGenreResponse updateGenre(UpdateGenreRequest genre);
//    DeleteGenreResponse deleteGenre(Long id);
    Genre findById(Long id);
    List<GetGenreResponse> getAllGenres();
//
//    GenreDetail findGenreDetailById(Long id);
}
