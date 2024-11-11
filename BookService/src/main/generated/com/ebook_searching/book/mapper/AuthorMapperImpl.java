package com.ebook_searching.book.mapper;

import com.ebook_searching.book.adapter.ontology_client.OWLAuthor;
import com.ebook_searching.book.dto.AuthorDetail;
import com.ebook_searching.book.model.author.Author;
import java.time.LocalDate;
import javax.annotation.processing.Generated;
import org.ebook_searching.proto.Event;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-10T20:18:06+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.13 (Amazon.com Inc.)"
)
@Component
public class AuthorMapperImpl implements AuthorMapper {

    @Override
    public Author toAuthor(Event.Author author) {
        if ( author == null ) {
            return null;
        }

        Author author1 = new Author();

        author1.setId( author.getId() );
        author1.setName( author.getName() );
        if ( author.hasStageName() ) {
            author1.setStageName( map( author.getStageName() ) );
        }
        author1.setUuid( author.getUuid() );
        if ( author.hasBirthDate() ) {
            author1.setBirthDate( LocalDate.parse( map( author.getBirthDate() ) ) );
        }
        if ( author.hasDeathDate() ) {
            author1.setDeathDate( LocalDate.parse( map( author.getDeathDate() ) ) );
        }
        if ( author.hasBirthPlace() ) {
            author1.setBirthPlace( map( author.getBirthPlace() ) );
        }
        if ( author.hasNationality() ) {
            author1.setNationality( map( author.getNationality() ) );
        }
        if ( author.hasWebsite() ) {
            author1.setWebsite( map( author.getWebsite() ) );
        }
        if ( author.hasDescription() ) {
            author1.setDescription( map( author.getDescription() ) );
        }
        if ( author.hasImage() ) {
            author1.setImage( map( author.getImage() ) );
        }

        return author1;
    }

    @Override
    public AuthorDetail toAuthorDetail(OWLAuthor author) {
        if ( author == null ) {
            return null;
        }

        AuthorDetail authorDetail = new AuthorDetail();

        authorDetail.setName( author.getName() );
        authorDetail.setStageName( author.getStageName() );
        if ( author.getBirthDate() != null ) {
            authorDetail.setBirthDate( LocalDate.parse( author.getBirthDate() ) );
        }
        if ( author.getDeathDate() != null ) {
            authorDetail.setDeathDate( LocalDate.parse( author.getDeathDate() ) );
        }
        authorDetail.setBirthPlace( author.getBirthPlace() );
        authorDetail.setNationality( author.getNationality() );
        authorDetail.setWebsite( author.getWebsite() );
        authorDetail.setDescription( author.getDescription() );
        authorDetail.setImage( author.getImage() );

        return authorDetail;
    }

    @Override
    public AuthorDetail toAuthor(Author author) {
        if ( author == null ) {
            return null;
        }

        AuthorDetail authorDetail = new AuthorDetail();

        authorDetail.setName( author.getName() );
        authorDetail.setStageName( author.getStageName() );
        authorDetail.setBirthDate( author.getBirthDate() );
        authorDetail.setDeathDate( author.getDeathDate() );
        authorDetail.setBirthPlace( author.getBirthPlace() );
        authorDetail.setNationality( author.getNationality() );
        authorDetail.setWebsite( author.getWebsite() );
        authorDetail.setDescription( author.getDescription() );
        authorDetail.setImage( author.getImage() );

        return authorDetail;
    }

    @Override
    public void updateAuthorFromRequest(Author author, Event.Author request) {
        if ( request == null ) {
            return;
        }

        author.setId( request.getId() );
        author.setName( request.getName() );
        if ( request.hasStageName() ) {
            author.setStageName( map( request.getStageName() ) );
        }
        else {
            author.setStageName( null );
        }
        author.setUuid( request.getUuid() );
        if ( request.hasBirthDate() ) {
            author.setBirthDate( LocalDate.parse( map( request.getBirthDate() ) ) );
        }
        else {
            author.setBirthDate( null );
        }
        if ( request.hasDeathDate() ) {
            author.setDeathDate( LocalDate.parse( map( request.getDeathDate() ) ) );
        }
        else {
            author.setDeathDate( null );
        }
        if ( request.hasBirthPlace() ) {
            author.setBirthPlace( map( request.getBirthPlace() ) );
        }
        else {
            author.setBirthPlace( null );
        }
        if ( request.hasNationality() ) {
            author.setNationality( map( request.getNationality() ) );
        }
        else {
            author.setNationality( null );
        }
        if ( request.hasWebsite() ) {
            author.setWebsite( map( request.getWebsite() ) );
        }
        else {
            author.setWebsite( null );
        }
        if ( request.hasDescription() ) {
            author.setDescription( map( request.getDescription() ) );
        }
        else {
            author.setDescription( null );
        }
        if ( request.hasImage() ) {
            author.setImage( map( request.getImage() ) );
        }
        else {
            author.setImage( null );
        }
    }
}
