package org.ebook_searching.admin.payload;

import lombok.Data;

import java.util.List;

@Data
public class PaginationResponse<T> {
    private int numPages;
    private int offset;
    private int totalItems;
    private int limit;
    private List<T> data;
}
