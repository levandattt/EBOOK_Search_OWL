package org.ebook_searching.authentication.dto;

import lombok.Data;

@Data
public class Profile {
    private Long userId;
    private String username;
    private String email;
    private String fullName;
    private String gender;
    private String dateOfBirth;
    private boolean setup;
}
