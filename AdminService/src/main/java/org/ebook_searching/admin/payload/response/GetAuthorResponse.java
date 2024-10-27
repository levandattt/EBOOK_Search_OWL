package org.ebook_searching.admin.payload.response;

import lombok.Data;
import org.ebook_searching.admin.dto.AuthorDetail;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
public class GetAuthorResponse {

    private Long   id;
    private String name;

    private String stageName;

    private LocalDate birthDate;
    private LocalDate deathDate;

    private String birthPlace;

    private String nationality;

    private String website;

    private String description;

    private String image;
}
