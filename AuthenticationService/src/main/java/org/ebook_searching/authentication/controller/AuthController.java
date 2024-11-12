package org.ebook_searching.authentication.controller;

import org.ebook_searching.authentication.dto.Profile;
import org.ebook_searching.authentication.payload.request.LoginRequest;
import org.ebook_searching.authentication.payload.request.RegisterRequest;
import org.ebook_searching.authentication.payload.request.UpdateProfileRequest;
import org.ebook_searching.authentication.payload.response.LoginResponse;
import org.ebook_searching.authentication.payload.response.RegisterResponse;
import org.ebook_searching.authentication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api")
public class AuthController {
    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public RegisterResponse register(@Valid @RequestBody RegisterRequest request) {
        return userService.register(request);
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return userService.login(request);
    }

    @GetMapping("/profile/{userId}")
    public Profile getProfile(@PathVariable Long userId, Principal principal) {
        // Check if the user has access to this profile (forbidden if accessing other users' profiles)
//        if (!userId.equals(Long.parseLong(principal.getName()))) {
//            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
//        }
        return userService.getProfile(userId);
    }

    @PutMapping("/profile/{userId}")
    public Profile updateProfile(@PathVariable Long userId,
                                 @Valid @RequestBody UpdateProfileRequest request,
                                 Principal principal) {
//        if (!userId.equals(Long.parseLong(principal.getName()))) {
//            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
//        }
        return userService.updateProfile(userId, request);
    }
}
