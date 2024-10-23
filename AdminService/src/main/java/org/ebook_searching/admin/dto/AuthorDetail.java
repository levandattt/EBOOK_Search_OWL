package org.ebook_searching.admin.dto;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class AuthorDetail {
    private Long id;

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
