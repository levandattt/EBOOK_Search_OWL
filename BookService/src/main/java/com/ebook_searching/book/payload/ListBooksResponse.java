package com.ebook_searching.book.payload;


import com.ebook_searching.book.dto.AuthorDetail;
import com.ebook_searching.book.dto.BaseBook;
import com.ebook_searching.book.dto.BookDetail;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ListBooksResponse {
    private int numPages;
    private int offset;
    private int limit;
    private int totalItems;
    private List<BaseBook> data;
    private BookDetail bookDetail;
    private AuthorDetail author;
}
