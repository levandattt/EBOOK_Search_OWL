package org.ebook_searching.admin.dto;

import lombok.Data;

@Data
public class ReviewDetail {
    private String image;
    private String reviewer;
    private String content;
    private Long time;
}