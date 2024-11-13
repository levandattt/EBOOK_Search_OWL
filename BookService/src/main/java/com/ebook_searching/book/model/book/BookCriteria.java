package com.ebook_searching.book.model.book;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookCriteria {
    String keyword;
    Long genreId;
}
