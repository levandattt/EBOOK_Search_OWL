package org.ebook_searching.admin.payload.response;

import lombok.Data;
import org.ebook_searching.admin.dto.AuthorDetail;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
public class AddAuthorResponse {
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