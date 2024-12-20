package org.ebook_searching.admin.payload;

import lombok.Data;

import java.util.List;

@Data
public class PageResponse<T> {
    List<T> items;
    private int numPages;
    private int offset;
    private int limit;
    private int totalItems;
}
