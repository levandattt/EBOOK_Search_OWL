package com.ebook_searching.book.mapper;

import com.ebook_searching.book.model.author.Author;
import com.ebook_searching.book.model.book.Book;
import javax.annotation.processing.Generated;
import org.ebook_searching.common.mapper.DateMapper;
import org.ebook_searching.common.mapper.StringValueMapper;
import org.ebook_searching.proto.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-10T20:18:05+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.13 (Amazon.com Inc.)"
)
@Component
public class EventMapperImpl implements EventMapper {

    @Autowired
    private DateMapper dateMapper;
    @Autowired
    private StringValueMapper stringValueMapper;

    @Override
    public Event.AddBookEvent toBookEvent(Book addBookRequest) {
        if ( addBookRequest == null ) {
            return null;
        }

        Event.AddBookEvent.Builder addBookEvent = Event.AddBookEvent.newBuilder();

        if ( addBookRequest.getAuthors() != null ) {
            for ( Author author : addBookRequest.getAuthors() ) {
                addBookEvent.addAuthors( toAuthor( author ) );
            }
        }
        if ( addBookRequest.getId() != null ) {
            addBookEvent.setId( addBookRequest.getId() );
        }
        if ( addBookRequest.getTitle() != null ) {
            addBookEvent.setTitle( addBookRequest.getTitle() );
        }
        if ( addBookRequest.getPublishedAt() != null ) {
            addBookEvent.setPublishedAt( addBookRequest.getPublishedAt() );
        }
        if ( addBookRequest.getPublisher() != null ) {
            addBookEvent.setPublisher( addBookRequest.getPublisher() );
        }
        if ( addBookRequest.getTotalPages() != null ) {
            addBookEvent.setTotalPages( addBookRequest.getTotalPages() );
        }
        if ( addBookRequest.getLanguage() != null ) {
            addBookEvent.setLanguage( addBookRequest.getLanguage() );
        }
        if ( addBookRequest.getDescription() != null ) {
            addBookEvent.setDescription( addBookRequest.getDescription() );
        }
        if ( addBookRequest.getImage() != null ) {
            addBookEvent.setImage( addBookRequest.getImage() );
        }
        if ( addBookRequest.getUuid() != null ) {
            addBookEvent.setUuid( addBookRequest.getUuid() );
        }

        return addBookEvent.build();
    }

    @Override
    public Event.Author toAuthor(Author author) {
        if ( author == null ) {
            return null;
        }

        Event.Author.Builder author1 = Event.Author.newBuilder();

        if ( author.getId() != null ) {
            author1.setId( author.getId() );
        }
        if ( author.getName() != null ) {
            author1.setName( author.getName() );
        }
        if ( author.getStageName() != null ) {
            author1.setStageName( stringValueMapper.map( author.getStageName() ) );
        }
        if ( author.getBirthDate() != null ) {
            author1.setBirthDate( stringValueMapper.map( dateMapper.toString( author.getBirthDate() ) ) );
        }
        if ( author.getDeathDate() != null ) {
            author1.setDeathDate( stringValueMapper.map( dateMapper.toString( author.getDeathDate() ) ) );
        }
        if ( author.getBirthPlace() != null ) {
            author1.setBirthPlace( stringValueMapper.map( author.getBirthPlace() ) );
        }
        if ( author.getNationality() != null ) {
            author1.setNationality( stringValueMapper.map( author.getNationality() ) );
        }
        if ( author.getWebsite() != null ) {
            author1.setWebsite( stringValueMapper.map( author.getWebsite() ) );
        }
        if ( author.getDescription() != null ) {
            author1.setDescription( stringValueMapper.map( author.getDescription() ) );
        }
        if ( author.getImage() != null ) {
            author1.setImage( stringValueMapper.map( author.getImage() ) );
        }
        if ( author.getUuid() != null ) {
            author1.setUuid( author.getUuid() );
        }

        return author1.build();
    }
}
