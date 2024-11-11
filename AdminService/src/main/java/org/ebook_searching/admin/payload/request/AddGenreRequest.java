package org.ebook_searching.admin.payload.request;

import lombok.Data;

@Data
public class AddGenreRequest {
    private String name;
    private String slug;
    private String image;
}
