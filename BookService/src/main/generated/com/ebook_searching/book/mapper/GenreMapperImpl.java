package com.ebook_searching.book.mapper;

import com.ebook_searching.book.model.book.Book;
import com.ebook_searching.book.model.genre.Genre;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.ebook_searching.proto.Event;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-10T20:18:06+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.13 (Amazon.com Inc.)"
)
@Component
public class GenreMapperImpl implements GenreMapper {

    @Override
    public Genre toGenre(Event.Genre genre) {
        if ( genre == null ) {
            return null;
        }

        Genre genre1 = new Genre();

        genre1.setId( genre.getId() );
        if ( genre.hasName() ) {
            genre1.setName( map( genre.getName() ) );
        }
        if ( genre.hasImage() ) {
            genre1.setImage( map( genre.getImage() ) );
        }

        return genre1;
    }

    @Override
    public Genre toGenre(Genre genre) {
        if ( genre == null ) {
            return null;
        }

        Genre genre1 = new Genre();

        genre1.setId( genre.getId() );
        genre1.setName( genre.getName() );
        genre1.setCreatedAt( genre.getCreatedAt() );
        genre1.setUpdatedAt( genre.getUpdatedAt() );
        Set<Book> set = genre.getBooks();
        if ( set != null ) {
            genre1.setBooks( new LinkedHashSet<Book>( set ) );
        }
        genre1.setImage( genre.getImage() );

        return genre1;
    }
}
