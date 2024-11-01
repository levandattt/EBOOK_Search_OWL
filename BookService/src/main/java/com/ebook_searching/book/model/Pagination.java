package com.ebook_searching.book.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Pagination {
    Integer limit;
    Integer offset;
}
