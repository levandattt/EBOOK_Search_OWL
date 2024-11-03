package org.ebook_searching.authentication.payload.response;

import lombok.Data;
import org.ebook_searching.authentication.dto.Profile;

@Data
public class LoginResponse {
    private Profile profile;
    private String accessToken;
    private long expirationTimestamp;

    // Getters and Setters
}
