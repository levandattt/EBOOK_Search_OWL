package com.ebook_searching.book.dto;

import lombok.Data;

import java.util.List;

@Data
public class BaseBook {
    private Long id;
    private String uri;
    private String uuid;
    private String title;
    private double avgRating;
    private String image;
    private List<AuthorDetail> authors;
}
