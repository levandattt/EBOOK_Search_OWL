package com.ebook_searching.book.payload;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PaginationResponse<T> {
    private int numPages;
    private int offset;
    private int totalItems;
    private int limit;
    private List<T> data;
}
