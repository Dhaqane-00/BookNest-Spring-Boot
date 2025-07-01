package com.BookNest.Service;

import com.BookNest.Repository.UserRepository;
import com.BookNest.utils.ApiResponse;
import com.BookNest.utils.JwtUtil;
import com.BookNest.Models.LoginRequest;
import com.BookNest.Models.LoginResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.BookNest.Models.User;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtUtil jwtUtil;

    public ApiResponse<LoginResponse> loginUser(LoginRequest loginRequest) {
        User existingUser = userRepository.findByUsername(loginRequest.getUsername());
        if (existingUser == null) {
            return ApiResponse.error("User not found", 404);
        }
        
        if (!passwordEncoder.matches(loginRequest.getPassword(), existingUser.getPassword())) {
            return ApiResponse.error("Invalid password", 401);
        }
        
        String token = jwtUtil.generateToken(existingUser.getUsername(), existingUser.getRole().name());
        
        LoginResponse loginResponse = LoginResponse.builder()
                .token(token)
                .username(existingUser.getUsername())
                .email(existingUser.getEmail())
                .role(existingUser.getRole().name())
                .userId(existingUser.getId())
                .build();
                
        return ApiResponse.success("User logged in successfully", loginResponse);
    }

    public ApiResponse<User> createUser(User user) {
        User existingUserByUsername = userRepository.findByUsername(user.getUsername());
        if (existingUserByUsername != null) {
            return ApiResponse.error("Username already exists", 409);
        }
        
        User existingUserByEmail = userRepository.findByEmail(user.getEmail());
        if (existingUserByEmail != null) {
            return ApiResponse.error("Email already exists", 409);
        }
        
        // Hash the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        // Set default role if not provided
        if (user.getRole() == null) {
            user.setRole(User.Role.USER);
        }
        
        User savedUser = userRepository.save(user);
        
        // Don't return the password in the response
        savedUser.setPassword(null);
        
        return ApiResponse.created("User created successfully", savedUser);
    }

    public ApiResponse<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        // Don't return passwords
        users.forEach(user -> user.setPassword(null));
        return ApiResponse.success("Users retrieved successfully", users);
    }
    
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}