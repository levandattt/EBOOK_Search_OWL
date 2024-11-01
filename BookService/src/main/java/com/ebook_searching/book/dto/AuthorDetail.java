package com.ebook_searching.book.dto;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
public class AuthorDetail {
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
