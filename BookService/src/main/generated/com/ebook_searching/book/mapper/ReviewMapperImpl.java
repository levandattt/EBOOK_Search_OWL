package com.ebook_searching.book.mapper;

import com.ebook_searching.book.dto.ReviewDetail;
import com.ebook_searching.book.model.review.Review;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-10T20:18:05+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.13 (Amazon.com Inc.)"
)
@Component
public class ReviewMapperImpl implements ReviewMapper {

    @Override
    public ReviewDetail toReviewDetail(Review review) {
        if ( review == null ) {
            return null;
        }

        ReviewDetail reviewDetail = new ReviewDetail();

        if ( review.getImage() != null ) {
            reviewDetail.setImage( review.getImage() );
        }
        if ( review.getReviewer() != null ) {
            reviewDetail.setReviewer( review.getReviewer() );
        }
        if ( review.getContent() != null ) {
            reviewDetail.setContent( review.getContent() );
        }
        if ( review.getTime() != null ) {
            reviewDetail.setTime( review.getTime() );
        }

        return reviewDetail;
    }
}
