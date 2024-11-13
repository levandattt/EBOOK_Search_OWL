package com.ebook_searching.book.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GenreDetail {
    private Long id;
    private String name;
    private String image;
}
