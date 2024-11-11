package org.ebook_searching.admin.payload.response;

import lombok.Data;

@Data
public class GetGenreResponse {
    private Long id;
    private String name;
    private String slug;
    private String image;
}
