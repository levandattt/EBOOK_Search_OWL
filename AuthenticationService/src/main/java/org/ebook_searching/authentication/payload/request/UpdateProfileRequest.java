package org.ebook_searching.authentication.payload.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Data
public class UpdateProfileRequest {
    
    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotBlank(message = "Gender is required")
    @Pattern(regexp = "male|female|other|Male|Female|Other", message = "Gender must be 'male', 'female', or 'other'")
    private String gender;

    @Past(message = "Date of birth must be a past date")
    private LocalDate dateOfBirth;
}
