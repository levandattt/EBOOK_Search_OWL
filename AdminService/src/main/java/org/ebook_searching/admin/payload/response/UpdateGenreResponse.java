package org.ebook_searching.admin.payload.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateGenreResponse {
    private Long id;
    private String name;
    private String image;
}
