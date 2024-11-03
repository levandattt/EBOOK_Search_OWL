package org.ebook_searching.authentication.service;

import org.ebook_searching.authentication.dto.Profile;
import org.ebook_searching.authentication.payload.request.LoginRequest;
import org.ebook_searching.authentication.payload.request.RegisterRequest;
import org.ebook_searching.authentication.payload.request.UpdateProfileRequest;
import org.ebook_searching.authentication.payload.response.LoginResponse;
import org.ebook_searching.authentication.payload.response.RegisterResponse;

public interface UserService {
    RegisterResponse register(RegisterRequest request);
    LoginResponse login(LoginRequest request);
    Profile getProfile(Long userId);
    Profile updateProfile(Long userId, UpdateProfileRequest request);
}
