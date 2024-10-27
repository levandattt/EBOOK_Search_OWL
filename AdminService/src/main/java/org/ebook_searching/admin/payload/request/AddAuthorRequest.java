package org.ebook_searching.admin.payload.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
public class AddAuthorRequest {

    @NotNull(message = "Name is required")
    @NotBlank(message = "Name cannot be blank")
    @Length(max = 100, message = "Name cannot exceed 100 characters")
    private String name;

    @Length(max = 100, message = "Stage name cannot exceed 100 characters")
    private String stageName;

    private LocalDate birthDate;
    private LocalDate deathDate;

    @Length(max = 100, message = "Birth place cannot exceed 100 characters")
    private String birthPlace;

    @Length(max = 50, message = "Nationality cannot exceed 50 characters")
    private String nationality;

    @Length(max = 255, message = "Website URL cannot exceed 255 characters")
    private String website;

    private String description;

    private String image;
}