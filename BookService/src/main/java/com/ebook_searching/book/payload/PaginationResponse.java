package com.ebook_searching.book.payload;

import java.util.List;


public class PaginationResponse<T> {
    private int numPages;
    private int offset;
    private int totalItems;
    private int limit;
    private List<T> data;
}
