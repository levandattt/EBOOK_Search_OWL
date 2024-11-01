package com.ebook_searching.book.mapper;

import com.ebook_searching.book.dto.ReviewDetail;
import com.ebook_searching.book.model.author.Author;
import com.ebook_searching.book.model.book.Book;
import com.ebook_searching.book.model.review.Review;
import org.ebook_searching.common.mapper.DateMapper;
import org.ebook_searching.common.mapper.StringValueMapper;
import org.ebook_searching.common.utils.StringUtils;
import org.ebook_searching.proto.Event;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring", uses = {StringUtils.class, DateMapper.class, StringValueMapper.class}, collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface ReviewMapper {
    ReviewDetail toReviewDetail(Review review);
}