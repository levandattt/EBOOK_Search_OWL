package com.ebook_searching.book.mapper;

import com.ebook_searching.book.adapter.ontology_client.OWLBook;
import com.ebook_searching.book.dto.AuthorDetail;
import com.ebook_searching.book.dto.BaseBook;
import com.ebook_searching.book.dto.BookDetail;
import com.ebook_searching.book.dto.ReviewDetail;
import com.ebook_searching.book.model.author.Author;
import com.ebook_searching.book.model.book.Book;
import com.ebook_searching.book.model.review.Review;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.ebook_searching.common.utils.StringUtils;
import org.ebook_searching.proto.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-10T20:18:05+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.13 (Amazon.com Inc.)"
)
@Component
public class BookMapperImpl implements BookMapper {

    @Autowired
    private AuthorMapper authorMapper;

    @Override
    public Book toBook(Event.AddBookEvent request) {
        if ( request == null ) {
            return null;
        }

        Book book = new Book();

        book.setId( request.getId() );
        book.setTitle( request.getTitle() );
        book.setPublishedAt( request.getPublishedAt() );
        book.setPublisher( request.getPublisher() );
        book.setTotalPages( request.getTotalPages() );
        book.setLanguage( request.getLanguage() );
        book.setDescription( request.getDescription() );
        book.setUuid( request.getUuid() );
        book.setImage( request.getImage() );

        return book;
    }

    @Override
    public BookDetail toBookDetail(Book request) {
        if ( request == null ) {
            return null;
        }

        BookDetail bookDetail = new BookDetail();

        bookDetail.setPublicationTime( request.getPublishedAt() );
        bookDetail.setId( request.getId() );
        bookDetail.setTitle( request.getTitle() );
        if ( request.getTotalPages() != null ) {
            bookDetail.setTotalPages( request.getTotalPages() );
        }
        bookDetail.setPublisher( request.getPublisher() );
        bookDetail.setAuthors( authorSetToAuthorDetailList( request.getAuthors() ) );
        bookDetail.setLanguage( request.getLanguage() );
        bookDetail.setDescription( request.getDescription() );
        bookDetail.setImage( request.getImage() );
        bookDetail.setReviews( reviewSetToReviewDetailSet( request.getReviews() ) );

        return bookDetail;
    }

    @Override
    public BookDetail toBookDetail(OWLBook request) {
        if ( request == null ) {
            return null;
        }

        BookDetail bookDetail = new BookDetail();

        bookDetail.setGenres( StringUtils.toStringList( request.getGenre() ) );
        bookDetail.setId( (long) request.getId() );
        bookDetail.setUri( request.getUri() );
        bookDetail.setTitle( request.getTitle() );
        bookDetail.setAvgRating( request.getAvgRating() );
        bookDetail.setRatingCount( request.getRatingCount() );
        bookDetail.setPublicationTime( request.getPublicationTime() );
        bookDetail.setTotalPages( request.getTotalPages() );
        bookDetail.setPublisher( request.getPublisher() );
        bookDetail.setLanguage( request.getLanguage() );
        bookDetail.setDescription( request.getDescription() );

        return bookDetail;
    }

    @Override
    public BaseBook toBaseBook(OWLBook book) {
        if ( book == null ) {
            return null;
        }

        BaseBook baseBook = new BaseBook();

        baseBook.setId( (long) book.getId() );
        baseBook.setUri( book.getUri() );
        baseBook.setUuid( book.getUuid() );
        baseBook.setTitle( book.getTitle() );
        baseBook.setAvgRating( book.getAvgRating() );

        return baseBook;
    }

    @Override
    public BaseBook toBaseBook(Book book) {
        if ( book == null ) {
            return null;
        }

        BaseBook baseBook = new BaseBook();

        baseBook.setId( book.getId() );
        baseBook.setUuid( book.getUuid() );
        baseBook.setTitle( book.getTitle() );
        baseBook.setImage( book.getImage() );
        baseBook.setAuthors( authorSetToAuthorDetailList( book.getAuthors() ) );

        return baseBook;
    }

    @Override
    public void updateBookFromRequest(Book book, Event.AddBookEvent request) {
        if ( request == null ) {
            return;
        }

        book.setTitle( request.getTitle() );
        book.setPublishedAt( request.getPublishedAt() );
        book.setPublisher( request.getPublisher() );
        book.setTotalPages( request.getTotalPages() );
        book.setLanguage( request.getLanguage() );
        book.setDescription( request.getDescription() );
        book.setUuid( request.getUuid() );
        book.setImage( request.getImage() );
    }

    protected List<AuthorDetail> authorSetToAuthorDetailList(Set<Author> set) {
        if ( set == null ) {
            return null;
        }

        List<AuthorDetail> list = new ArrayList<AuthorDetail>( set.size() );
        for ( Author author : set ) {
            list.add( authorMapper.toAuthor( author ) );
        }

        return list;
    }

    protected ReviewDetail reviewToReviewDetail(Review review) {
        if ( review == null ) {
            return null;
        }

        ReviewDetail reviewDetail = new ReviewDetail();

        reviewDetail.setImage( review.getImage() );
        reviewDetail.setReviewer( review.getReviewer() );
        reviewDetail.setContent( review.getContent() );
        reviewDetail.setTime( review.getTime() );

        return reviewDetail;
    }

    protected Set<ReviewDetail> reviewSetToReviewDetailSet(Set<Review> set) {
        if ( set == null ) {
            return null;
        }

        Set<ReviewDetail> set1 = new LinkedHashSet<ReviewDetail>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Review review : set ) {
            set1.add( reviewToReviewDetail( review ) );
        }

        return set1;
    }
}
