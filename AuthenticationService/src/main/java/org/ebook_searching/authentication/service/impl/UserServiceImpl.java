package org.ebook_searching.authentication.service.impl;

import io.jsonwebtoken.SignatureAlgorithm;
import org.ebook_searching.authentication.mapper.UserMapper;
import org.ebook_searching.authentication.model.User;
import org.ebook_searching.authentication.model.Role;
import org.ebook_searching.authentication.dto.Profile;
import org.ebook_searching.authentication.model.UserProfile;
import org.ebook_searching.authentication.payload.request.LoginRequest;
import org.ebook_searching.authentication.payload.request.RegisterRequest;
import org.ebook_searching.authentication.payload.request.UpdateProfileRequest;
import org.ebook_searching.authentication.payload.response.LoginResponse;
import org.ebook_searching.authentication.payload.response.RegisterResponse;
import org.ebook_searching.authentication.repository.UserRepository;
import org.ebook_searching.authentication.service.UserService;
import org.ebook_searching.common.exception.InvalidFieldsException;
import org.ebook_searching.common.exception.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.stream.Collectors;

import io.jsonwebtoken.Jwts;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder; // for encoding passwords

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpiration;


    public RegisterResponse register(RegisterRequest request) {
        // Check if username or email already exists
        if (request.getUsername()!=null) {
            if (userRepository.existsByUsername(request.getUsername())) {
                throw InvalidFieldsException.fromFieldError("username", "Username already taken");
            }
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw InvalidFieldsException.fromFieldError("email", "Email already registered");
        }

        // Create new user and profile
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));

        // Save the user
        userRepository.save(user);

        String accessToken = generateAccessToken(user);  // implement this method
        long expirationTimestamp = System.currentTimeMillis() / 1000L + jwtExpiration; // 1-hour expiry example

        // Create response
        RegisterResponse response = new RegisterResponse();
        response.setProfile(userMapper.toProfile(user));
        response.setAccessToken(accessToken);
        response.setExpirationTimestamp(expirationTimestamp);

        return response;
    }

    // Generate JWT Access Token
    private String generateAccessToken(User user) {
        // Set expiration time
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpiration * 1000);

        // Generate token with user details and roles as claims
        return Jwts.builder()
                .setSubject(String.valueOf(user.getId()))  // Set user ID as subject
                .claim("username", user.getUsername())
                .claim("roles", user.getRoles().stream()
                        .map(Role::getRoleName)
                        .collect(Collectors.toList()))
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        // Check if user exists by username or email
        User user = userRepository.findByUsernameOrEmail(request.getUsername(), request.getUsername())
                .orElseThrow(() -> InvalidFieldsException.fromFieldError("username", "User doesn't exist"));

        // Verify password
        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw InvalidFieldsException.fromFieldError("password", "Wrong password");
        }

        // Generate access token
        String accessToken = generateAccessToken(user);
        long expirationTimestamp = System.currentTimeMillis() / 1000L + jwtExpiration;

        // Build and return response
        LoginResponse response = new LoginResponse();
        response.setProfile(userMapper.toProfile(user)); // Profile constructor takes User object
        response.setAccessToken(accessToken);
        response.setExpirationTimestamp(expirationTimestamp);

        return response;
    }

    @Override
    public Profile getProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RecordNotFoundException("User not found"));

        return userMapper.toProfile(user);
    }

    @Override
    public Profile updateProfile(Long userId, UpdateProfileRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RecordNotFoundException("User not found"));

        UserProfile profile;
        // Update profile information
        if (user.getProfile() != null) {
            profile = user.getProfile();
        } else {
            profile = new UserProfile();
        }
        profile.setFullName(request.getFullName());
        profile.setGender(request.getGender());
        profile.setDateOfBirth(request.getDateOfBirth());
        profile.setSetup(true); // Mark setup as true

        user.setProfile(profile);

        userRepository.save(user); // Save the updated user profile

        return userMapper.toProfile(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
